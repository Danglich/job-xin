package com.danglich.jobxinseeker.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.danglich.jobxinseeker.model.CustomUserDetail;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.repository.JobSeekerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService{
	
	private final JobSeekerRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		JobSeekers seeker = userRepository.findByEmail(username).orElseThrow(() -> new  UsernameNotFoundException("Not found user with this username"));
		
		return new CustomUserDetail(seeker);
	}

}
