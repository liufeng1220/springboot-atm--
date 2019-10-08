package co.fengfeng.service;

import co.fengfeng.domain.AjaxRes;

public interface PasswordService {
    AjaxRes checkPassword(String cardId,String password);

    AjaxRes updatePassword(String cardId, String password);
}
