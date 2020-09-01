package com.mylife.controller.login;

import com.mylife.constant.Const;
import com.mylife.constant.UrlConst;
import com.mylife.entity.user.TUser;
import com.mylife.service.user.ITUserService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    public static Log log = LogFactory.getLog(LoginController.class);
    @Autowired
    private ITUserService userService;
    /**
     * 登录控制
     * @param session
     * @param response
     * @return
     */
    @RequestMapping({"","/"})
    public String login(HttpSession session,HttpServletResponse response){
        if(session.getAttribute(Const.SESSION_USER) != null){
            try {
                this.buildRedirect(session,response);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }
        return "pc/login";
    }

    /**
     *  根据用户名登录
     * @param session
     * @param userName
     * @param password
     * @param verificationCode
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public Object doLogin(HttpSession session, @RequestParam String userName, @RequestParam String password,@RequestParam String verificationCode){
        return userService.loginByUserName(session,userName,password,verificationCode);
    }

    /**
     * 登出
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping("/logout")
    public void logout(HttpSession session,HttpServletResponse response) throws Exception{
        // 清空session
        Enumeration<String> enumeration =  session.getAttributeNames();
        while (enumeration.hasMoreElements()){
            session.removeAttribute(enumeration.nextElement());
        }
        response.sendRedirect(UrlConst.LOGIN_URL);
    }

    /**
     *  构建重定向
     * @param session
     * @param response
     * @throws Exception
     */
    private void buildRedirect(HttpSession session,HttpServletResponse response) throws Exception{
        TUser user = (TUser) session.getAttribute(Const.SESSION_USER);
        response.sendRedirect(UrlConst.HOME_URL);
    }
}
