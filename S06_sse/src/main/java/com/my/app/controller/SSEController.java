package com.my.app.controller;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Server Send Event
 */
@Controller
public class SSEController {
    @RequestMapping(value="/push", produces="text/event-stream;charset=utf-8")
    @ResponseBody
    public String push() {
        Random r = new Random();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        String msg = " integer->" + r.nextInt(100) + "->" + dateStr;
        //固定格式
        return "data:" + msg +"\n\n";
    }
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getSSEView () {
        return "test";
    }
}