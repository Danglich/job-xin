package com.danglich.jobxinseeker.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.danglich.jobxinseeker.dto.RegisterDTO;
import com.danglich.jobxinseeker.exception.AuthException;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.repository.JobSeekerRepository;
import com.danglich.jobxinseeker.service.JobSeekerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobSeekerServiceImpl implements JobSeekerService{
	
	private final JobSeekerRepository repository;

	@Override
	public JobSeekers register(RegisterDTO registerDTO) {
		
		Optional<JobSeekers> seeker = repository.findByEmail(registerDTO.getEmail());
		
		if(seeker.isPresent()) {
			throw new AuthException("Email đã có tài khoản, vui lòng đăng nhập");
		}
//		JobSeekers seeker = JobSeekers.builder()
//								.email(registerDTO.getEmail())
//								.password(registerDTO.getPassword())
//								.build();
		
		return repository.save(JobSeekers.builder()
								.email(registerDTO.getEmail())
								.password(registerDTO.getPassword())
								.build());
	}

}
