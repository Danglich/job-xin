package com.danglich.jobxinseeker.service;

import com.danglich.jobxinseeker.dto.SeekerInfoDTO;
import com.danglich.jobxinseeker.model.JobSeekers;

public interface JobSeekerService {
	
	
	SeekerInfoDTO getSeekerInfo(String username);
	
	JobSeekers getByUsername(String username);
	
	JobSeekers getCurrentUser();
	
	void saveJob(int jobId);
	
	void unSaveJob(int jobId);

}
