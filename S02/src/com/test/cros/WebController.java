package com.test.cros;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*")
@Controller
public class WebController {

	@RequestMapping(value = "/start")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/login1")
	public String login1() {
		return "login1";
	}
	
	@RequestMapping(value = "/login2")
	public String login2() {
		return "login2";
	}
	
	
	@RequestMapping(value = "/user/succ")
	public String succ() {
		return "succ";
	}
	
	@RequestMapping(value = "/user/fail")
	public String fail() {
		return "fail";
	}
}
