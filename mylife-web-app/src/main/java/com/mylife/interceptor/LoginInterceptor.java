package com.mylife.interceptor;

import com.mylife.annotation.PassLogin;
import com.mylife.constant.Const;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
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
        if(handler instanceof HandlerMethod){
            //PassLogin注解放过拦截
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            PassLogin passLogin = handlerMethod.getMethodAnnotation(PassLogin.class);
            if(passLogin != null){
                return true;
            }else{
                Object sessionUser = request.getSession().getAttribute(Const.SESSION_USER);
                if(sessionUser == null){
//            request.getSession().invalidate();
                    //使用转发跳转登录页面
                    request.getRequestDispatcher("/login").forward(request,response);
                    return false;
                }
            }
        }

        return true;
    }
}
