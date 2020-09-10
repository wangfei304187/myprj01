package com.my.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.my.api.Study;

@RestController
public class StudyService {

	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value = "/template/study")
	public String getStudyList() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> re = restTemplate.exchange("http://localhost:8080/api/study", HttpMethod.GET, entity, String.class);
		System.out.println(re);
		return re.getBody();
	}
	
	@RequestMapping(value = "/template/study/{bmStudyId}")
	public String getStudyById(@PathVariable String bmStudyId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> re = restTemplate.exchange("http://localhost:8080/api/study/" + bmStudyId, HttpMethod.GET, entity, String.class);
		return re.getBody();
	}
	
	
	@RequestMapping(value = "/template/study/create", method = RequestMethod.POST)
	public String createStudy(@RequestBody Study study) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Study> entity = new HttpEntity<Study>(study, headers);
		
		return restTemplate.exchange("http://localhost:8080/api/study/create", HttpMethod.POST, entity, String.class).getBody();
	}
	
	@RequestMapping(value = "/template/study/{id}", method = RequestMethod.PUT)
	public String updateStudy(@PathVariable("id") String bmStudyId, @RequestBody Study study) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Study> entity = new HttpEntity<Study>(study, headers);
		
		return restTemplate.exchange("http://localhost:8080/api/study/" + bmStudyId, HttpMethod.PUT, entity, String.class).getBody();
	}
	
	@RequestMapping(value = "/template/study/{id}", method = RequestMethod.DELETE)
	public String deleteStudy(@PathVariable("id") String bmStudyId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Study> entity = new HttpEntity<Study>(headers);
		
		return restTemplate.exchange("http://localhost:8080/api/study/" + bmStudyId, HttpMethod.DELETE, entity, String.class).getBody();
	}
}
