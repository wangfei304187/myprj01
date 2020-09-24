//package com.my.app;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//public class IndexController {
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @GetMapping("/")
//    public String index(HttpServletRequest request){
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null){
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("token")){
//                    String token = cookie.getValue();
//                    System.out.println("准备进数据库");
//                    User user = userMapper.findByToken(token); //去数据库寻找该token值的用户信息
//                    System.out.println(user.toString());
//                    if(user != null){ //若找到了这个用户信息
//                        //写进session，让页面去展示
//                        request.getSession().setAttribute("user",user);
//                    }
//                    break;
//                }
//
//            }
//        }
//
//        return "index";
//    }
//}