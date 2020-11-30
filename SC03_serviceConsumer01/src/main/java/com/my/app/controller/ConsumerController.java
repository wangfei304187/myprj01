package com.my.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.my.app.pojo.Greeting;
import com.my.app.service.IHelloService;

// http://localhost:8002/commonRequest
// http://localhost:8002/commonRequest2
// http://localhost:8002/feignRequest
// http://localhost:8002/feignRequest2
// http://localhost:8002/feignRequest2?name=ABCD

@RestController
public class ConsumerController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private IHelloService helloService;

	private static final String applicationName = "single-provider";

	@RequestMapping(value = "commonRequest")
	public Object commonRequest() {
		String url = "http://" + applicationName + "/hello";
		return restTemplate.getForObject(url, String.class);
	}
	
	@RequestMapping(value = "commonRequest2")
	public Object commonRequest2() {
		String url = "http://" + applicationName + "/greeting?name=ABC";
		return restTemplate.getForObject(url, Greeting.class);
	}

	@RequestMapping(value = "feignRequest")
	public Object feignRequest() {
		String s = helloService.sayHello();
		return s;
	}
	
	@RequestMapping(value = "feignRequest2")
	public Object feignRequest2(@RequestParam(value = "name", defaultValue = "World") String name) {
		Greeting obj = helloService.sayGreeting(name);
		return obj;
	}

}