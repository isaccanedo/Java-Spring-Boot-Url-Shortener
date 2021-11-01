package dev.isaccanedo.urlshortener.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import dev.isaccanedo.urlshortener.model.dto.ApiErrorDTO;

@ControllerAdvice
public class RestControllerExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);
	
	@ExceptionHandler(value = { ResponseStatusException.class })
	public ResponseEntity<ApiErrorDTO> handleResponseStatusException(ResponseStatusException ex) {
		logger.error(ex.getMessage(), ex);
		
		ApiErrorDTO body = ApiErrorDTO.builder()
				.error(ex.getStatus().getReasonPhrase())
				.status(ex.getStatus().value())
				.message(ex.getReason())
				.timestamp(new Date())
				.build();
		
		logger.error("Response Status: {} {}", ex.getStatus().value(), ex.getStatus().getReasonPhrase());
		logger.error("Response Body: {}", body);
		
		return ResponseEntity.status(ex.getStatus()).contentType(MediaType.APPLICATION_JSON).body(body);
	}
}

