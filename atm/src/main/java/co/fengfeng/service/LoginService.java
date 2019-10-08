package co.fengfeng.service;

import co.fengfeng.domain.AjaxRes;

import javax.servlet.http.HttpSession;

public interface LoginService {
    AjaxRes getCardInfo(String cardId, String password);

    AjaxRes getUserInfo(String cardId, HttpSession session);
}
