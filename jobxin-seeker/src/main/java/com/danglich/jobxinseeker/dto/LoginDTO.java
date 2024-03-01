package com.danglich.jobxinseeker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginDTO {
	
	@Email
	@NotNull(message = "Email is required")
	private String email;
	
	@NotNull(message = "Password is required")
	private String password;

}
