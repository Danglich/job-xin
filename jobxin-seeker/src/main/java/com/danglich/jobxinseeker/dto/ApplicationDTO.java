package com.danglich.jobxinseeker.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
	
	private String message;
	
	private MultipartFile file;
	
	private int jobId;
	
	
}
