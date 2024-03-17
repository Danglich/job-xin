package com.danglich.jobxinseeker.service;

import com.danglich.jobxinseeker.model.JobSeekers;

public interface ConfirmationTokenService {
	
	String save(JobSeekers seeker);
	
	void updateConfirmedAt(String token);
	
	boolean isExitedToken(Integer seekerId);

}
