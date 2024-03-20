package com.danglich.jobxinseeker.service.impl;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import com.danglich.jobxinseeker.dto.ChangePasswordDTO;
import com.danglich.jobxinseeker.dto.SeekerInfoDTO;
import com.danglich.jobxinseeker.exception.IncorrectPasswordException;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.model.Provider;
import com.danglich.jobxinseeker.repository.JobSeekerRepository;
import com.danglich.jobxinseeker.security.oauth2.CustomOAuth2User;
import com.danglich.jobxinseeker.service.JobSeekerService;
import com.danglich.jobxinseeker.service.JobService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobSeekerServiceImpl implements JobSeekerService{
	
	private final JobSeekerRepository repository;
	private final JobService jobService;
	private final PasswordEncoder passwordEncoder;
	

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

	@Override
	@Transactional
	public void changePassword(ChangePasswordDTO request) {
		JobSeekers seeker = repository.findByEmail(request.getEmail())
						.orElseThrow(() -> new ResourceAccessException("Not found seeker with this email"));
		
		if (!passwordEncoder.matches(request.getOldPassword(), seeker.getPassword()) ) {
			throw new IncorrectPasswordException("Mật khẩu không đúng");
		}
		String newPassword = passwordEncoder.encode(request.getNewPassword());
		repository.resetPassword(seeker.getId(), newPassword);
		
	}

	@Override
	public SeekerInfoDTO updateInfo(SeekerInfoDTO request) {
		JobSeekers seeker = repository.findByEmail(request.getEmail())
				.orElseThrow(() -> new ResourceAccessException("Not found seeker with this email"));
		
		seeker.setFullName(request.getFullName());
		seeker.setPhoneNumber(request.getPhoneNumber());
		repository.save(seeker);
		
		return request;
		
	}

	@Override
	public void processLoginWithOAuth(CustomOAuth2User oAuth2User) {
		String email = oAuth2User.getEmail();
		Optional<JobSeekers> seekerOptional = repository.findByEmail(email);
		if(seekerOptional.isEmpty()) {
			JobSeekers seeker = JobSeekers.builder()
									.avatar(oAuth2User.getAvatar())
									.fullName(oAuth2User.getName())
									.enabled(true)
									.provider(Provider.GOOGLE)
									.email(email)
									.build();
			repository.save(seeker);
		}
		
		
	}

}
