package com.my.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/hello")
    public String hello(){
        List<String> services = discoveryClient.getServices();
        for(String s : services){
            System.out.println("ServiceA-Provider(02), ProviderController::hello() ===> " + s);
        }
        return "hello spring cloud! (from ServiceA-Provider(02))";
    }

}