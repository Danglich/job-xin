package com.danglich.jobxinseeker.service.impl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.danglich.jobxinseeker.dto.RegisterDTO;
import com.danglich.jobxinseeker.dto.SeekerInfoDTO;
import com.danglich.jobxinseeker.exception.AuthException;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.repository.JobSeekerRepository;
import com.danglich.jobxinseeker.service.JobSeekerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobSeekerServiceImpl implements JobSeekerService{
	
	private final JobSeekerRepository repository;
	private final PasswordEncoder passwordEncoder;

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

}
