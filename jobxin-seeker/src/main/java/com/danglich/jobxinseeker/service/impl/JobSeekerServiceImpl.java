package com.danglich.jobxinseeker.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.danglich.jobxinseeker.dto.RegisterDTO;
import com.danglich.jobxinseeker.dto.SeekerInfoDTO;
import com.danglich.jobxinseeker.exception.AuthException;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.repository.JobSeekerRepository;
import com.danglich.jobxinseeker.service.JobSeekerService;
import com.danglich.jobxinseeker.service.JobService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobSeekerServiceImpl implements JobSeekerService{
	
	private final JobSeekerRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JobService jobService;

	@Override
	public JobSeekers register(RegisterDTO registerDTO) {
		
		Optional<JobSeekers> seeker = repository.findByEmail(registerDTO.getEmail());
		
		if(seeker.isPresent()) {
			throw new AuthException("Email đã có tài khoản, vui lòng đăng nhập");
		}

		
		return repository.save(JobSeekers.builder()
								.email(registerDTO.getEmail())
								.password(passwordEncoder.encode(registerDTO.getPassword()))
								.enabled(true)
								.build());
	}

	@Override
	public SeekerInfoDTO getSeekerInfo(String username) {
		
		JobSeekers seeker = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Not found user"));
		
		SeekerInfoDTO seekerInfo = new SeekerInfoDTO(seeker.getFullName(), seeker.getEmail() , seeker.getPhoneNumber());
		
		return seekerInfo;
	}

	@Override
	public JobSeekers getByUsername(String username) {
		
		return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Not found user"));
	}

	@Override
	public JobSeekers getCurrentUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		JobSeekers seeker = repository.findByEmail(authentication.getName()).orElseThrow(() -> new ResourceAccessException("Not found the user"));
		
		return seeker;
	}

	@Override
	public void saveJob(int jobId) {
		
		Jobs job = jobService.getById(jobId);
		JobSeekers seeker = this.getCurrentUser();
		
		seeker.saveJob(job);
		
		repository.save(seeker);
		
	}


	@Override
	public void unSaveJob(int jobId) {
		Jobs job = jobService.getById(jobId);
		JobSeekers seeker = this.getCurrentUser();
		
		seeker.unSaveJob(job);
		
		repository.save(seeker);
		
	}

}
