package co.fengfeng.service.impl;

import co.fengfeng.common.enums.ExceptionEnum;
import co.fengfeng.common.exception.AtmException;
import co.fengfeng.domain.AjaxRes;
import co.fengfeng.domain.CardInfo;
import co.fengfeng.domain.UserInfo;
import co.fengfeng.mapper.CardInfoMapper;
import co.fengfeng.mapper.UserInfoMapper;
import co.fengfeng.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private CardInfoMapper cardInfoMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 验证银行卡信息是否正确
     *
     * @param cardId
     * @param password
     * @return
     */
    @Override
    public AjaxRes getCardInfo(String cardId, String password) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        cardInfo.setPassword(password);
        try {
            CardInfo card = cardInfoMapper.selectOne(cardInfo);
            if (StringUtils.isEmpty(card)) {
                ajaxRes.setRes("error");
                ajaxRes.setMeg("密码错误");
            } else {
                UserInfo userInfo = userInfoMapper.selectByPrimaryKey(card.getCustomerId());
                ajaxRes.setRes("success");
                ajaxRes.setMeg("登陆成功");
                ajaxRes.setObject(userInfo);
            }
        } catch (Exception e) {
            log.error("登陆失败");
            throw new AtmException(ExceptionEnum.LOGIN_ERROE);
        }
        return ajaxRes;
    }

    /**
     * 根据卡号读取账户信息，判断账号是否存在
     * @param cardId
     * @return
     */
    @Override
    public AjaxRes getUserInfo(String cardId, HttpSession session) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            CardInfo cardInfo = cardInfoMapper.selectByPrimaryKey(cardId);
            if (StringUtils.isEmpty(cardInfo)) {
                ajaxRes.setRes("error");
                ajaxRes.setMeg("卡号不存在");
            } else {
                ajaxRes.setRes("success");
                ajaxRes.setMeg(cardId);
                //将卡号信息保存到本次会话
                session.setAttribute("cardId",cardId);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AtmException(ExceptionEnum.READ_CARD_ERROE);
        }
        return ajaxRes;
    }

    @Test
    public void test(){
        long round = Math.round(2.5);
        System.out.println(round);
    }
}
