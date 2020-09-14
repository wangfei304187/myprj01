package com.my.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.my.exception.StudyNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:9090")
@RequestMapping("/api/study")
public class StudyController {

	private static Map<String, Study> studyRepo = new HashMap<>();
	
	static {
		Study s1 = new Study();
		s1.setBmStudyId("1");
		s1.setPatientName("Jack");
		s1.setAge(31);
		s1.setStudyDescription("this is study description 1");
		
		Study s2 = new Study();
		s2.setBmStudyId("2");
		s2.setPatientName("James");
		s2.setAge(32);
		s2.setStudyDescription("this is study description 2");
		
		studyRepo.put(s1.getBmStudyId(), s1);
		studyRepo.put(s2.getBmStudyId(), s2);
	}
	
	// http://localhost:8080/api/study
	@GetMapping("")
	public ResponseEntity<Object> getStudy()
	{
		return new ResponseEntity<>(studyRepo.values(), HttpStatus.OK);
	}
	
	// http://localhost:8080/api/study/2
	@GetMapping(value = "/{bmStudyId}")
	public ResponseEntity<Object> getStudyById(@PathVariable String bmStudyId)
	{
		return new ResponseEntity<>(studyRepo.get(bmStudyId), HttpStatus.OK);
	}
	
	
//	http://localhost:8080/api/study/create
//	{
//	    "bmStudyId": "3",
//	    "patientName": "Lili",
//	    "age": 23,
//	    "studyDescription": "this is study description 3"
//	}
	@PostMapping(value = "/create")
	public ResponseEntity<Object> createStudy(@RequestBody Study study)
	{
		studyRepo.put(study.getBmStudyId(), study);
		return new ResponseEntity<>("Study is created successfully", HttpStatus.CREATED);
	}
	
	
//	http://localhost:8080/api/study/2
//	{
//        "bmStudyId": "2",
//        "patientName": "James",
//        "age": 35,
//        "studyDescription": "this is study description 2 -- updated"
//    }
	@PutMapping(value = "/{bmStudyId}")
	public ResponseEntity<Object> updateStudy(@PathVariable String bmStudyId, @RequestBody Study study)
	{
		if (!studyRepo.containsKey(bmStudyId))
		{
//			http://localhost:8080/api/study/11
//			{
//		        "bmStudyId": "11",
//		        "patientName": "James",
//		        "age": 35,
//		        "studyDescription": "this is study description 2 -- updated"
//		    }
			
			throw new StudyNotFoundException();
		}
		studyRepo.remove(bmStudyId);
		study.setBmStudyId(bmStudyId);
		studyRepo.put(bmStudyId, study);
		return new ResponseEntity<>("Study is updated successfully", HttpStatus.OK);
	}
	
	// http://localhost:8080/api/study/2
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteStudy(@PathVariable("id") String bmStudyId)
	{
		studyRepo.remove(bmStudyId);
		return new ResponseEntity<>("Study is deleted successfully", HttpStatus.OK);
	}
	
	
	
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
