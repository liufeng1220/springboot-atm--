package co.fengfeng.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Table(name = "userinfo")
public class UserInfo {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer customerId; //用户编号
    private String customerName;    //开户人姓名
    private String pid; //身份证号码
    private String telephone; //电话
    private String address; //地址
}
