package com.mylife.interceptor;

import com.mylife.constant.Const;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @descirption : 登录拦截器
 * @author : wyh
 * @date : 2020/9/4 15:35
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object sessionUser = request.getSession().getAttribute(Const.SESSION_USER);
        if(sessionUser == null){
            System.out.println(request.getRequestURI());
            System.out.println(request.getRequestURL());
//            request.getSession().invalidate();
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            request.getRequestDispatcher("/login").forward(request,response);
//            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
