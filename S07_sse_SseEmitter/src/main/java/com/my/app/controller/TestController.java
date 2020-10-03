package com.my.app.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@CrossOrigin
@RestController
@RequestMapping("/sse")
public class TestController {
	
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView showTestView () {
//        return "test";
    	ModelAndView mv = new ModelAndView("test");
        return mv;
    }
	
    /**
     * 用于创建连接
     */
    @GetMapping("/connect/{userId}")
    public SseEmitter connect(@PathVariable String userId) {
        return SseEmitterServer.connect(userId);
    }

    /**
     * 推送给所有人
     *
     * @param message
     * @return
     */
    @GetMapping("/push/{message}")
    public ResponseEntity<String> push(@PathVariable(name = "message") String message) {
        SseEmitterServer.batchSendMessage(message);
        return ResponseEntity.ok("WebSocket 推送消息给所有人");
    }

    /**
     * 发送给单个人
     *
     * @param message
     * @param userid
     * @return
     */
    @GetMapping("/push_one/{messsage}/{userid}")
    public ResponseEntity<String> pushOne(@PathVariable(name = "message") String message, @PathVariable(name = "userid") String userid) {
        SseEmitterServer.sendMessage(userid, message);
        return ResponseEntity.ok("WebSocket 推送消息给" + userid);
    }

    /**
     * 关闭连接
     */
    @GetMapping("/close/{userid}")
    public ResponseEntity<String> close(@PathVariable("userid") String userid) {
        SseEmitterServer.removeUser(userid);
        return ResponseEntity.ok("连接关闭");
    }

}