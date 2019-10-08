package co.fengfeng.service;

import co.fengfeng.domain.AjaxRes;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public interface MoneyService {
    /*根据客户id查询金额*/
    AjaxRes getUserMoney(String cardId);
    //根据客户id和取款金额来取钱
    AjaxRes updateMoneyById(String userId, BigDecimal money, HttpSession session);
    /*根据客户id和取款金额来存钱*/
    AjaxRes saveMoneyById(String cardId, BigDecimal money, HttpSession session);

    AjaxRes getUserCard(String cardId,HttpSession session);
    //转账
    AjaxRes transMoneyByCard(String userCard, String transCard, BigDecimal money,HttpSession session);
}
