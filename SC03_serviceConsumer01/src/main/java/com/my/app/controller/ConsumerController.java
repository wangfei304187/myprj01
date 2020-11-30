package com.my.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.my.app.pojo.Greeting;
import com.my.app.service.IHelloService;

// http://localhost:9001/commonRequest
// http://localhost:9001/commonRequest2
// http://localhost:9001/feignRequest
// http://localhost:9001/feignRequest2
// http://localhost:9001/feignRequest2?name=ABCD

@RestController
public class ConsumerController {

//	@Autowired
//	LoadBalancerClient loadBalancerClient;
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private IHelloService helloService;

	private static final String applicationName = "serviceA-provider";

	@RequestMapping(value = "commonRequest")
	public Object commonRequest() {
		printMsg();
		
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
	
	private void printMsg()
	{
//		ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-client");
//		String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/hello";
//		System.out.println(url);
	}

}