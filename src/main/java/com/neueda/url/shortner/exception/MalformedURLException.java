package com.neueda.url.shortner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MalformedURLException extends RuntimeException {

	public MalformedURLException(String message) {
		super(message);
	}

}
