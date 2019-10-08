package co.fengfeng.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    QUERY_MONEY_ERROE(500, "查询金额失败，服务器内部错误"),
    LOGIN_ERROE(500, "登陆失败,服务器错误"),
    READ_CARD_ERROE(500, "读卡错误"),
    UPDATE_PASSWORD_ERROR(500, "修改密码失败"),
    WITHDRAWAL_ERROE(500, "取款失败，服务器内部错误"),
    TRANSFER_ACCOUNT_FOUND(500, "查询转账账户异常"),
    TRANSFER_ACCOUNT_ERROE(500, "转账异常，服务器错误"),
    SAVE_MONEY_ERROR(500, "存款失败，服务器内部错误");
    private Integer code;
    private String msg;
}

