package com.liee.core.exception;

public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8335085163090129670L;

	public BaseException(String message){
		super(message);
	}
	
	public BaseException(Throwable cause) {
        super(cause);
    }
	
	public BaseException(String message, Throwable cause) {
	     super(message, cause);
	}

}
