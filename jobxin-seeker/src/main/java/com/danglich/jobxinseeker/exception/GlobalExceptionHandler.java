package com.danglich.jobxinseeker.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AuthException.class)
	public String handleAuthException(AuthException ex , Model theModel) {
		theModel.addAttribute("error", ex.getMessage());
		
		return "redirect:@{/auth(error)}";
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String handleNoHandlerFound(NoHandlerFoundException ex) {
		
		return "page/notfound";
	}
	
	@ExceptionHandler(Exception.class)
	public void handleException(Exception ex ) {
		ex.printStackTrace();
	}

}
