package co.fengfeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableTransactionManagement    //开启事务支持
@MapperScan("co.fengfeng.mapper")
public class AtmApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtmApplication.class);
    }
}
