package com.ge.efs.validation.exception;


public class InitializationException extends Exception {

	private static final long serialVersionUID = 8364286732775397833L;
	
	public InitializationException(){
		super();
	}
	
	public InitializationException(String message, Exception cause){
		super(message, cause);
	}
}
