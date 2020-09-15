package com.test.cros;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

// url : "http://127.0.0.1:8080/api2/study", // disable cros
// url : "http://127.0.0.1:8080/api/study", // enable cros

@RestController
public class IgnoreCrosController {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
    
    
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value = "/study9090")
	public String getStudyList() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> re = restTemplate.exchange("http://localhost:8080/api2/study", HttpMethod.GET, entity, String.class);
		System.out.println(re);
		return re.getBody();
	}
}
