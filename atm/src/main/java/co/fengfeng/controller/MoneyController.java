package co.fengfeng.controller;

import co.fengfeng.domain.AjaxRes;
import co.fengfeng.service.MoneyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@RestController
@Api(value = "金额交易", tags = {"金额交易接口"})
public class MoneyController {

    @Autowired
    private MoneyService moneyService;

    /**
     * 验证转账的卡号是否存在
     */
    @ApiOperation(value = "通过用户的id和取款金额来进行取款操作", notes = "当取款金额大于用户账号可用金额时不可取款", httpMethod = "POST")
    @PostMapping("/queryCardInfo")
    public ResponseEntity<AjaxRes> getUserInfo(String cardId,HttpSession session){
        AjaxRes ajaxRes = moneyService.getUserCard(cardId,session);
        return ResponseEntity.ok(ajaxRes);
    }
    /**
     * 根据双方卡号和交易金额来进行转账
     */
    @ApiOperation(value = "通过双方卡号来完成转账交易", notes = "当用户余额小于转出金额时不可转账", httpMethod = "POST")
    @PostMapping("/transferMoney")
    public ResponseEntity<AjaxRes> transferMoney(String userCard,String transCard,BigDecimal money,HttpSession session){
        AjaxRes ajaxRes = moneyService.transMoneyByCard(userCard, transCard, money,session);
        return ResponseEntity.ok(ajaxRes);
    }

    /**
     * 通过银行卡号查询用户的余额
     *
     * @param cardId
     * @return
     */
    @ApiOperation(value = "通过卡号查询账户金额", notes = "银行卡号查询账户余额", httpMethod = "POST")
    @PostMapping("/getMoney")
    public ResponseEntity<AjaxRes> getUserMoney( String cardId) {
        return ResponseEntity.ok(moneyService.getUserMoney(cardId));
    }
    /**
     * 根据银行卡号和输入金额来来进行取款操作
     */
    @ApiOperation(value = "通过银行卡号和取款金额来进行取款操作", notes = "当取款金额大于用户账号可用金额时不可取款", httpMethod = "POST")
    @PostMapping("/getWithdrawal")
    public ResponseEntity<AjaxRes> getWithdrawal( String cardId,BigDecimal money, HttpSession session){
        AjaxRes ajaxRes = moneyService.updateMoneyById(cardId, money,session);
        return ResponseEntity.ok(ajaxRes);
    }
    /**
     * 根据用户id和输入金额来来进行存款操作
     */
    @ApiOperation(value = "用户存钱的方法", notes = "存钱的方法", httpMethod = "POST")
    @PostMapping("/saveMoney")
    public ResponseEntity<AjaxRes> saveMoney(String cardId,BigDecimal money, HttpSession session){
        AjaxRes ajaxRes = moneyService.saveMoneyById(cardId, money,session);
        return ResponseEntity.ok(ajaxRes);
    }
}
