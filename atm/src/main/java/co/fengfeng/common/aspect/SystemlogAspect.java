package co.fengfeng.common.aspect;
import co.fengfeng.domain.Systemlog;
import co.fengfeng.mapper.SystemlogMapper;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class SystemlogAspect {
    @Autowired
    private SystemlogMapper systemlogMapper;
    /**
     * 切面日志记录
     */
    @After(value = "execution(public * co.fengfeng.service.*.*(..))")
    public void saveLog(JoinPoint joinPoint) {
        //日志对象
        Systemlog systemlog = new Systemlog();
        systemlog.setOptime(new Date());

        //获取目标方法执行的全路径
        String name = joinPoint.getTarget().getClass().getName();
        //获取方法的名称
        String signature = joinPoint.getSignature().getName();
        String func = name + ":" + signature;
        systemlog.setFunction(func);
        //获取方法参数
        Object[] args = joinPoint.getArgs();
        Gson gson = new Gson();
        StringBuffer stringBuffer=new StringBuffer();
        boolean flg = true;
        for (Object arg : args) {
            if(flg){
                systemlog.setCardId(gson.toJson(arg));
                flg=false;
            }
            stringBuffer.append( gson.toJson(arg));
        }
        systemlog.setParams(stringBuffer.toString());
        //将日志保存到数据库
//        systemlogMapper.insert(systemlog);
    }
}