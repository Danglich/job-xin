package com.danglich.jobxinseeker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
	@NotBlank(message = "Email is required")
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;

}
