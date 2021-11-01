package com.voxloud.provisioning.model;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionResponse {

	private String errMessage;
	private String url;
	private HttpStatus statusCode;
	private String timeStamp;
}
