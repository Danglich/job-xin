package com.danglich.jobxinseeker.service;

import com.danglich.jobxinseeker.dto.ChangePasswordDTO;
import com.danglich.jobxinseeker.dto.SeekerInfoDTO;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.security.oauth2.CustomOAuth2User;

public interface JobSeekerService {
	
	
	SeekerInfoDTO getSeekerInfo(String username);
	
	JobSeekers getByUsername(String username);
	
	JobSeekers getCurrentUser();
	
	void saveJob(int jobId);
	
	void unSaveJob(int jobId);
	
	void changePassword(ChangePasswordDTO request);
	
	SeekerInfoDTO updateInfo(SeekerInfoDTO seekerInfoDTO);
	
	void processLoginWithOAuth(CustomOAuth2User oAuth2User) ;


}
