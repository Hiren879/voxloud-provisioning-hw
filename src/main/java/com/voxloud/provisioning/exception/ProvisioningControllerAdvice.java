package com.voxloud.provisioning.exception;

import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.voxloud.provisioning.model.ExceptionResponse;

/**
 * This class is responsible to handle all types of exceptions for Provisioning
 * application.
 * 
 * @author Hiren Savalia
 *
 */

@ControllerAdvice
public class ProvisioningControllerAdvice extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = getLogger(ProvisioningControllerAdvice.class);

	@ExceptionHandler({ BadInputException.class, DeviceTypeNotSupportedException.class })
	public ResponseEntity<ExceptionResponse> handleBadInputException(BadInputException ex,
			final HttpServletRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setErrMessage(ex.getMessage());
		exceptionResponse.setTimeStamp(LocalDateTime.now().toString());
		exceptionResponse.setUrl(request.getRequestURI());
		exceptionResponse.setStatusCode(HttpStatus.BAD_REQUEST);
		logException(exceptionResponse);

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
			final HttpServletRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setErrMessage(ex.getMessage());
		exceptionResponse.setTimeStamp(LocalDateTime.now().toString());
		exceptionResponse.setUrl(request.getRequestURI());
		exceptionResponse.setStatusCode(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ GenericException.class, Exception.class })
	public ResponseEntity<ExceptionResponse> handleException(Exception ex, final HttpServletRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setErrMessage(ex.getMessage());
		exceptionResponse.setTimeStamp(LocalDateTime.now().toString());
		exceptionResponse.setUrl(request.getRequestURI());
		exceptionResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);

		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void logException(ExceptionResponse exceptionResponse) {
		LOGGER.error(exceptionResponse.toString());
	}
}
