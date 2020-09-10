package com.my.service;

import java.util.Collection;

import com.my.api.Study;

public interface IStudyService {

	public abstract void createStudy(Study study);
	
	public abstract void updateStudy(String bmStudyId, Study study);
	
	public abstract void deleteStudy(String bmStudyId);
	
	public abstract Collection<Study> getStudyList();
	
	public abstract Study getStudyById(String bmStudyId);
}
