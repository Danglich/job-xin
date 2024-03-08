package com.danglich.jobxinseeker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordDTO {

	private String email;
	
	private String oldPassword;
	
	private String newPassword;
	
	private String confirmPassword;
}
