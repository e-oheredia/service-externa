package com.exact.service.externa.utils;

public class RestResponse {
	
	private Integer responsecode;
	private String message;
	
	public RestResponse(Integer responsecode) {
		super();
		this.responsecode = responsecode;
	}

	public RestResponse(Integer responsecode, String message) {
		super();
		this.responsecode = responsecode;
		this.message= message;
	}	
	
	public Integer getResponsecode() {
		return responsecode;
	}
	public void setResponsecode(Integer responsecode) {
		this.responsecode = responsecode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
