package com.levi9.socialnetwork.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceDuplicateException extends Throwable{
	
	private static final long serialVersionUID = 1L;
	
	public ResourceDuplicateException(String message) {
		super(message);
	}
}
