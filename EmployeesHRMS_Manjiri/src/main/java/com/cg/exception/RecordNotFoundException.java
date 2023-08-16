package com.cg.exception;

public class RecordNotFoundException extends Exception {
	public RecordNotFoundException() {
		super("Record Does not exists !!!");
	}
	public RecordNotFoundException(String msg) {
		super(msg);
	}
	@Override
	public String toString() {
		return "RecordNotFoundException [message "+super.getMessage()+"]";
	}
	
}
