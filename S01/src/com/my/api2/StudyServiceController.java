package com.my.api2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.my.api.Study;
import com.my.service.IStudyService;

@RestController
@RequestMapping("/api2/study")
public class StudyServiceController {

	@Autowired
	IStudyService studyService;
	
	// http://localhost:8080/api2/study
	@GetMapping("")
	public ResponseEntity<Object> getStudy()
	{
		return new ResponseEntity<>(studyService.getStudyList(), HttpStatus.OK);
	}
	
	// http://localhost:8080/api2/study/2
	@GetMapping(value = "/{bmStudyId}")
	public ResponseEntity<Object> getStudyById(@PathVariable String bmStudyId)
	{
		return new ResponseEntity<>(studyService.getStudyById(bmStudyId), HttpStatus.OK);
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
		studyService.createStudy(study);
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
		studyService.updateStudy(bmStudyId, study);
		return new ResponseEntity<>("Study is updated successfully", HttpStatus.OK);
	}
	
	// http://localhost:8080/api/study/2
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteStudy(@PathVariable("id") String bmStudyId)
	{
		studyService.deleteStudy(bmStudyId);
		return new ResponseEntity<>("Study is deleted successfully", HttpStatus.OK);
	}
	
}
