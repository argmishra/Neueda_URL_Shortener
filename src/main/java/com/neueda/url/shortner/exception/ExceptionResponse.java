package com.neueda.url.shortner.exception;

import java.util.Date;

public class ExceptionResponse {

	private Date date;
	private String message;
	private String detail;

	public ExceptionResponse(Date date, String message, String detail) {
		super();
		this.date = date;
		this.message = message;
		this.detail = detail;
	}

}
