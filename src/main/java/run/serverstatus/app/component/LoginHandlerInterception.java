package run.serverstatus.app.component;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterception implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object account = request.getSession().getAttribute("account");
        if (account == null) {
            //未登录
            request.setAttribute("msg","没有权限，请先登录~");
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        } else //已登录
            return true;
    }
}
