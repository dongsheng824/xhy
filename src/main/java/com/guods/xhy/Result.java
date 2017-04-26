package com.guods.xhy;

public class Result {

	private Boolean success;
	private String message;
	private String cookies;

	public Result() {
		super();
	}
	public Result(Boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCookies() {
		return cookies;
	}
	public void setCookies(String cookies) {
		this.cookies = cookies;
	}
	
	
}
