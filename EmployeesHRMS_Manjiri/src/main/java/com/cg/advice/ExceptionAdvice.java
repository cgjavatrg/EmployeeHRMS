package com.cg.advice;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.core.AuthenticationException;

import com.cg.exception.InvalidDataException;
import com.cg.exception.RecordNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestControllerAdvice
public class ExceptionAdvice {

	Logger log=LoggerFactory.getLogger(ExceptionAdvice.class);
	
	  @ExceptionHandler(RecordNotFoundException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String recordNotFoundHandler(RecordNotFoundException ex) {
		log.error(ex.getMessage());
	    return ex.getMessage();
	  }
	  
	  @ExceptionHandler(InvalidDataException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  String invalidDataHandler(InvalidDataException ex) {
		log.error(ex.getMessage());
	    return ex.getMessage();
	  }
	  
	   @ExceptionHandler(AuthenticationException.class )
	    public ResponseEntity<String> handleAuthenticationException(Exception ex) {

		   	String errmsg="Invalid Credential!! Authentication Failed";
		   	log.error(errmsg);
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errmsg);
	    }
	  
	  
	  
	  @ExceptionHandler(Exception.class)
	  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	  String generalHandler1(Exception ex) {
		 Throwable t=ex.getCause();
		 int i=0;
		 String msg="";
		 while(t!=null)
		 {
			 msg=msg+t.getLocalizedMessage();
			 i++;
			 System.out.println(i+"=>"+t.getLocalizedMessage());
			 t=t.getCause();
		 }
		 log.error(ex.toString()+" "+msg);
	    return msg;
	  }
}
