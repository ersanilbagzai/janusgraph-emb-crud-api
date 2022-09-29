package com.techframer.janusgraph.api.crud.utils.wrapper;

import lombok.Data;

import java.util.Date;

@Data
public class ExceptionResponseWrapper {
	
	private Date timestamp;
	private String status;
	private String errorMessage;
	private String details;
	
	public ExceptionResponseWrapper(Date timestamp, String status, String errorMessage, String details) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.details = details;
		this.errorMessage = errorMessage;
	}

}
