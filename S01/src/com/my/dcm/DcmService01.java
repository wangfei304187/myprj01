package com.my.dcm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DcmService01 {

    public static void main(String[] args) {
        SpringApplication.run(DcmService01.class, args);

        String applicationPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(applicationPath);
    }

    @GetMapping("/dcm")
    public String hello(@RequestParam(value = "name", defaultValue = "DICOM") String name) {
        return String.format("Hello %s!", name);
    }
}
