package com.my.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.my.api.Study;

@Service
public class StudyServiceImpl implements IStudyService {

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
		
	@Override
	public void createStudy(Study study) {
		studyRepo.put(study.getBmStudyId(), study);
	}

	@Override
	public void updateStudy(String bmStudyId, Study study) {
		studyRepo.remove(bmStudyId);
		study.setBmStudyId(bmStudyId);
		studyRepo.put(bmStudyId, study);
	}

	@Override
	public void deleteStudy(String bmStudyId) {
		studyRepo.remove(bmStudyId);
	}

	@Override
	public Collection<Study> getStudyList() {
		return studyRepo.values();
	}

	@Override
	public Study getStudyById(String bmStudyId) {
		return studyRepo.get(bmStudyId);
	}

}
