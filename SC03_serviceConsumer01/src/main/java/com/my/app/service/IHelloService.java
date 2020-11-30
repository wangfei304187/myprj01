package com.my.app.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.my.app.pojo.Greeting;

@FeignClient("single-provider")
public interface IHelloService {

    @RequestMapping(value = "/hello")
    String sayHello();

    @GetMapping("/greeting")
    public Greeting sayGreeting(@RequestParam(value = "name", defaultValue = "World") String name);
}