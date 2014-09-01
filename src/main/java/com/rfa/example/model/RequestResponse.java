package com.rfa.example.model;

public class RequestResponse {
	private boolean success;
	private String message;

	public RequestResponse(boolean success, String message) {
		this.setSuccess(success);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
