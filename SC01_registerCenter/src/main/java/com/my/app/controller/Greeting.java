package com.my.app.controller;
public class Greeting {

//	private final long id;
//	private final String content;
//
//	public Greeting(long id, String content) {
//		this.id = id;
//		this.content = content;
//	}
//
//	public long getId() {
//		return id;
//	}
//
//	public String getContent() {
//		return content;
//	}
	
	private long id;
	private String content;
	
	public Greeting()
	{
	}

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}