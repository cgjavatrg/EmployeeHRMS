package com.cg.exception;

public class InvalidDataException extends Exception {
	public InvalidDataException() {
		super("Invalid Data");
	}
	public InvalidDataException(String msg) {
		super(msg);
	}
	@Override
	public String toString() {
		return "InvalidDataException [message = "+super.getMessage()+"]";
	}
	
	
}
