package com.my.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*")
@Controller
public class WebController {

	@RequestMapping(value = "/showStart")
	public String start() {
		return "start";
	}
}
