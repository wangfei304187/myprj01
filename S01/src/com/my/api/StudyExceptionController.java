package com.my.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.my.exception.StudyNotFoundException;

@ControllerAdvice
public class StudyExceptionController {

	@ExceptionHandler(value = StudyNotFoundException.class)
	public ResponseEntity<Object> exception(StudyNotFoundException e)
	{
		return new ResponseEntity<>("Study not found", HttpStatus.NOT_FOUND);
	}
}
