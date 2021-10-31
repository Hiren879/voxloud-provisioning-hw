package com.voxloud.provisioning.model;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {

	private String errMessage;
	private String url;
	private HttpStatus statusCode;
	private String timeStamp;

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@Override
	public String toString() {
		return "ExceptionResponse [errMessage=" + errMessage + ", url=" + url + ", statusCode=" + statusCode
				+ ", timeStamp=" + timeStamp + "]";
	}

}
