package com.my.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"com.my"})
public class WebAppMain {
	
    public static void main(String[] args) {
        SpringApplication.run(WebAppMain.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate()
    {
    	return new RestTemplate();
    }
    
}
