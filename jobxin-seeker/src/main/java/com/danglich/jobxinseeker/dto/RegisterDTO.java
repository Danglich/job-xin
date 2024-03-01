package com.danglich.jobxinseeker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterDTO {

	@Email
	@NotBlank(message = "Email is required")
	public String email;
	
	@NotBlank(message = "Password is required")
	public String password;
	
	@NotBlank(message = "Confirm password is required")
	public String confirmPassword;

	
	
	
}
