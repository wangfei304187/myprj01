package com.my.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AppController {

    @RequestMapping(value = "/showMain")
    public ModelAndView showMainPage()
    {
        ModelAndView mv = new ModelAndView("main");

        return mv;
    }

    @RequestMapping(value = "/data")
    public Map<String, Object> start()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", "User1");
        map.put("role", "Admin");

        return map;
    }
}
