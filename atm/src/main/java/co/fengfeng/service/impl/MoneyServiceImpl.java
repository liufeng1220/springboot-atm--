package co.fengfeng.service.impl;

import co.fengfeng.common.enums.ExceptionEnum;
import co.fengfeng.common.exception.AtmException;
import co.fengfeng.domain.AjaxRes;
import co.fengfeng.domain.CardInfo;
import co.fengfeng.domain.TransInfo;
import co.fengfeng.domain.UserInfo;
import co.fengfeng.mapper.CardInfoMapper;
import co.fengfeng.mapper.TransInfoMapper;
import co.fengfeng.mapper.UserInfoMapper;
import co.fengfeng.service.MoneyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
@Transactional
public class MoneyServiceImpl implements MoneyService {
    @Autowired
    private CardInfoMapper cardInfoMapper;
    @Autowired
    private TransInfoMapper transInfoMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 根据银行卡号查询账号余额
     *
     * @param cardId
     * @return
     */
    @Override
    public AjaxRes getUserMoney(String cardId) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        try {
            CardInfo cardInfo1 = cardInfoMapper.selectOne(cardInfo);
            if (StringUtils.isEmpty(cardInfo1)) {
                ajaxRes.setRes("error");
                ajaxRes.setMeg("未查询到结果");
            } else {
                ajaxRes.setRes("success");
                ajaxRes.setMeg("查询成功");
                ajaxRes.setObject(cardInfo1);
            }
        } catch (Exception e) {
            log.error("查询金额失败" + e.getMessage());
            throw new AtmException(ExceptionEnum.QUERY_MONEY_ERROE);
        }
        return ajaxRes;
    }

    /**
     * 取款操作
     *
     * @param cardId 用户卡号
     * @param money  取款金额
     * @return
     */
    @Override
    public AjaxRes updateMoneyById(String cardId, BigDecimal money, HttpSession session) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        try {
            //根据用户id查询用户信息
            CardInfo cardInfo1 = cardInfoMapper.selectOne(cardInfo);
            //账户总余额
            BigDecimal money1 = cardInfo1.getMoney();
            //账户可用余额
            Integer prestore = cardInfo1.getPrestore();
            //账户可用余款
            BigDecimal subtract = money1.subtract(new BigDecimal(prestore));
            //比较，-1小于、0等于、1大于
            int i = money.compareTo(subtract);
            if (i < 0) {
                BigDecimal subtract1 = money1.subtract(money);
                cardInfo1.setMoney(subtract1);
                cardInfoMapper.updateByPrimaryKey(cardInfo1);
                /**************保存交易记录*****************/
                TransInfo transInfo = new TransInfo();
                transInfo.setCardId(cardInfo1.getCardId());
                transInfo.setTransType(1);
                transInfo.setTransMoney(money);
                transInfo.setTransDate(new Date());
                transInfoMapper.insert(transInfo);
                /*****************************************/
                //保存到session中
                session.setAttribute("transInfo", transInfo);
                ajaxRes.setRes("success");
                ajaxRes.setMeg(money + "");
            } else if (i > 0) {
                ajaxRes.setRes("error");
                ajaxRes.setMeg("账户余额不足");
            }
        } catch (Exception e) {
            log.error("取款失败" + e.getMessage());
            throw new AtmException(ExceptionEnum.WITHDRAWAL_ERROE);
        }
        return ajaxRes;
    }

    /**
     * 根据银行卡号，和存款金额来进行存款操作
     * @param cardId
     * @param money
     * @param session
     * @return
     */
    @Override
    public AjaxRes saveMoneyById(String cardId, BigDecimal money, HttpSession session) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        try {
            //根据用户id查询用户信息
            CardInfo cardInfo1 = cardInfoMapper.selectOne(cardInfo);
            cardInfo1.setMoney(cardInfo1.getMoney().add(money));
            cardInfoMapper.updateByPrimaryKey(cardInfo1);
            /**************保存交易记录*****************/
            TransInfo trans = new TransInfo();
            trans.setCardId(cardInfo1.getCardId());
            trans.setTransType(0);
            trans.setTransMoney(money);
            trans.setTransDate(new Date());
            transInfoMapper.insert(trans);
            /*****************************************/
            //保存到session中
            session.setAttribute("transInfo", trans);
            ajaxRes.setRes("success");
            ajaxRes.setMeg(money + "");
        } catch (Exception e) {
            log.error("存款失败" + e.getMessage());
            throw new AtmException(ExceptionEnum.SAVE_MONEY_ERROR);
        }
        return ajaxRes;
    }

    /**
     * 根据卡号查询转账的用户是否存在
     *
     * @param cardId
     * @return
     */
    @Override
    public AjaxRes getUserCard(String cardId, HttpSession session) {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        AjaxRes ajaxRes = new AjaxRes();
        try {
            //根据卡号查询用户信息
            CardInfo cardInfo1 = cardInfoMapper.selectOne(cardInfo);
            if (StringUtils.isEmpty(cardInfo1)) {
                ajaxRes.setRes("error");
                ajaxRes.setMeg("输入卡号不存在，请检查后重试");
            } else {
                UserInfo userInfo = userInfoMapper.selectByPrimaryKey(cardInfo1.getCustomerId());
                ajaxRes.setRes("success");
                ajaxRes.setMeg(cardId);
                ajaxRes.setObject(userInfo);
            }
        } catch (Exception e) {
            log.error("转账异常" + e.getMessage());
            throw new AtmException(ExceptionEnum.TRANSFER_ACCOUNT_FOUND);
        }
        return ajaxRes;
    }

    /**
     * 根据双方卡号完成转账
     *
     * @param userCard
     * @param transCard
     * @param money
     * @return
     */
    @Override
    public AjaxRes transMoneyByCard(String userCard, String transCard, BigDecimal money,HttpSession session) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            //判断当前账户的可用余额是否足够完成本次交易
            CardInfo cardInfo = cardInfoMapper.selectByPrimaryKey(userCard);
            BigDecimal money1 = cardInfo.getMoney();
            BigDecimal bigDecimal = new BigDecimal(cardInfo.getPrestore());
            BigDecimal subtract = money1.subtract(bigDecimal);
            //比较，-1小于、0等于、1大于
            int i = subtract.compareTo(money);
            if (i >= 0) {
                //用户余额减去金额
                cardInfo.setMoney(cardInfo.getMoney().subtract(money));
                cardInfoMapper.updateByPrimaryKey(cardInfo);
                //对方金额增加
                CardInfo cardInfo2 = cardInfoMapper.selectByPrimaryKey(transCard);
                cardInfo2.setMoney(cardInfo2.getMoney().add(money));
                cardInfoMapper.updateByPrimaryKey(cardInfo2);
                /**************保存交易记录*****************/
                TransInfo trans = new TransInfo();
                trans.setCardId(userCard);
                trans.setTransType(2);
                trans.setTransMoney(money);
                trans.setTransDate(new Date());
                trans.setRemark(transCard);
                transInfoMapper.insert(trans);
                /*****************************************/
                ajaxRes.setRes("success");
                ajaxRes.setMeg("交易成功");
                //保存到session中
                session.setAttribute("transInfo", trans);
            } else {
                ajaxRes.setRes("error");
                ajaxRes.setMeg("账户余额不足,无法完成交易！");
            }
        }catch (Exception e){
            log.error("转账异常"+e.getMessage());
            throw new AtmException(ExceptionEnum.TRANSFER_ACCOUNT_ERROE);
        }
        return ajaxRes;
    }
}
