package co.fengfeng.config;

import co.fengfeng.common.util.RequestUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取控制器里面存的session对象，判断用户有没有登陆
        Object loginUser = request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            request.setAttribute("meg", "没有权限请先登录");
            request.getRequestDispatcher("/login").forward(request, response);
            return false;
        }
        //将request保存到本地线程
        RequestUtil.setRequest(request);
        //已经登陆，放行
        return true;
    }
}
