package com.houseilla.houseillabackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -7008944373227650337L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
