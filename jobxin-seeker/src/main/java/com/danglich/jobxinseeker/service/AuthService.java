package com.danglich.jobxinseeker.service;

import com.danglich.jobxinseeker.dto.LoginDTO;
import com.danglich.jobxinseeker.dto.RegisterDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
	
	void register(RegisterDTO registerDTO);
	
	void reSendConfirmationToken(String email);
	
	void confirm(String token, HttpServletRequest request, HttpServletResponse response);
	
	String login(LoginDTO loginDTO, HttpServletRequest request , HttpServletResponse response);

	void resetPassword(String email);
}
