package com.danglich.jobxinseeker.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Student {
	
	@Email
	public String firstName;
	
	
	@NotNull(message = "Is required")
	@NotBlank(message = "Is required")
	public String lastName;
	
	@NotNull(message = "Is required")
	public String courseCode;
	
	
	
	

}
