package com.my.interceptor;

import com.my.api.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

//    在请求处理之前调用,只有返回true才会执行请求
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        HttpSession session = httpServletRequest.getSession(true);
//        Object admin = session.getAttribute("admin");
//        if(admin!=null){
//            return true;
//        }else{
//            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login/adminLogin");
//            return false;
//        }

        System.out.println("BEGIN AdminLoginInterceptor::preHandle");
        HttpServletRequest req = httpServletRequest;
        HttpServletResponse resp = httpServletResponse;

        System.out.println(req.getMethod());
        if (req.getMethod().equals("OPTIONS"))
        {
            System.out.println("END AdminLoginInterceptor::preHandle -- 1");
            return true;
        }

        String token = req.getHeader("Authorization");
        System.out.println("AdminLoginInterceptor::preHandle, token=" + token);
        Map<String, Object> result = JwtUtils.validateToken(token);
        System.out.println("AdminLoginInterceptor::preHandle, result: " + result);
        if (!result.get("ERR_MSG").equals("ERR_MSG_OK"))
        {
            //System.out.println("sendRedirect: http://192.168.1.212:9090/login");
            //resp.sendRedirect("http://192.168.1.212:9090/login/");
            System.out.println("END AdminLoginInterceptor::preHandle -- 2");
            return false;
        }

        System.out.println("END AdminLoginInterceptor::preHandle -- 3");
        return true;
    }

//    试图渲染之后执行
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

//    在请求处理之后,视图渲染之前执行
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}