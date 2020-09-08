package com.my.dcm;

public class Study {

	private String bmStudyId;
	private String patientName;
	private int age;
	private String studyDescription;

	public Study()
	{
	}
	
	public String getBmStudyId() {
		return bmStudyId;
	}
	public void setBmStudyId(String bmStudyId) {
		this.bmStudyId = bmStudyId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getStudyDescription() {
		return studyDescription;
	}
	public void setStudyDescription(String studyDescription) {
		this.studyDescription = studyDescription;
	}
	
	
}
