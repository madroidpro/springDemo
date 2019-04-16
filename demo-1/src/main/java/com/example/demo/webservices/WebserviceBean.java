package com.example.demo.webservices;

public class WebserviceBean {
	
	private String message;

	public WebserviceBean(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "WebserviceBean [message=" + message + "]";
	}

	
}
