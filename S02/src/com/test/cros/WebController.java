package com.test.cros;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping(value = "/user/succ")
	public String succ() {
		return "succ";
	}
	
	@RequestMapping(value = "/user/fail")
	public String fail() {
		return "fail";
	}
}
