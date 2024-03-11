package com.danglich.jobxinseeker.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.danglich.jobxinseeker.dto.ApplicationDTO;
import com.danglich.jobxinseeker.model.Application;
import com.danglich.jobxinseeker.model.CV;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.repository.CVRepository;
import com.danglich.jobxinseeker.service.CVService;
import com.danglich.jobxinseeker.service.JobSeekerService;
import com.danglich.jobxinseeker.service.JobService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CVServiceImpl implements CVService{
	
	private final CVRepository repository;
	private final JobSeekerService seekerService;

	@Override
	public CV upload(MultipartFile file) throws IOException {
		
		JobSeekers seeker = seekerService.getCurrentUser();
		
		if (file.getContentType().equals("application/pdf")) {
			CV cv = CV.builder()
					.content(file.getBytes())
					.filename(file.getOriginalFilename())
					.isDefault(false)
					.seeker(seeker)
					.build();
			
			return repository.save(cv);
        } else {
        	throw new IllegalArgumentException("File must PDF format");
        }
		
	}

	@Override
	public CV getById(int id) {
		
		return repository.findById(id).orElseThrow(() -> new ResourceAccessException("Not found the CV"));
	}

}
