package com.my.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*")
@Controller
public class WebController {

	@RequestMapping(value = "/start")
	public String start() {
		return "start";
	}
}
