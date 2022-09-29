package com.clinic.object;

public class CommonResponse {

	private static final long serialVersionUID = 1299676083557112049L;

	private String statuscode;
	private String message;
	
	public CommonResponse(String statusCode, String message){
		this.statuscode = statusCode;
		this.message = message;
	}

	public CommonResponse(){
		super();
	}

	public String getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
