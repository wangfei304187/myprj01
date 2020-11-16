package com.my.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    RestTemplate restTemplate;



    @Test
    void contextLoads() {
    }

    @Test
    public void test01() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> re = restTemplate.exchange("http://localhost:8080/greeting", HttpMethod.GET, entity, String.class);
        System.out.println(re.getBody());
    }

    @Test
    public void test02() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> re = restTemplate.exchange("http://localhost:8080/greeting?name=ABC", HttpMethod.GET, entity, String.class);
        System.out.println(re.getBody());
    }
}
