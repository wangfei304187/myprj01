package com.my.dcm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/study")
public class StudyController {

	@GetMapping("/index")
	public String index() {
		return "Hello StudyController";
	}
	
	@Value("${spring.application.name}")
	private String name;
	
	@GetMapping("/name")
	public String name() {
		return name;
	}
}
