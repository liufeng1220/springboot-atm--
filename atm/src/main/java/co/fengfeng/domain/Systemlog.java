package co.fengfeng.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name = "systemlog")
public class Systemlog {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer logId;  //日志编号
    private String cardId;  //卡号
    private String function; //操作方法
    private String params; //传入参数
    private Date optime;    //交易时间
}
