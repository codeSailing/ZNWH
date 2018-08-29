package com.quickdone.znwh.interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by psf on 2017/11/10.
 * 全局拦截器  登录之前判断权限
 */
public class AuthInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession(false);
        String requestUrl = httpServletRequest.getRequestURL().toString();
        String context = httpServletRequest.getContextPath();
        logger.info("session============" + session);
        logger.info("context============" + context);
        if (session == null) {
            if(requestUrl.indexOf("login.do")==-1){
                if(requestUrl.indexOf("/ivr/getCallContent")>-1){
                    return true;
                }
                if(requestUrl.indexOf("/outBound.do")>-1){
                    return true;
                }
                httpServletResponse.sendRedirect(context+"/login.do");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
