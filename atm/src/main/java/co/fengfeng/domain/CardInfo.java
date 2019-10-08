package co.fengfeng.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name = "cardinfo")
public class CardInfo {
    @Id
    private String cardId;  //卡号
    private String curType; //币种
    private Boolean savingType; //0活期，1定期
    private BigDecimal money;   //钱
    private Integer prestore;   //预存余额
    private String password;    //密码
    private Date openDate;  //开户时间
    private Boolean isReportLoss;   //是否挂失，0不是，1是
    private Integer customerId; //绑定账号
}
