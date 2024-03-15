package com.danglich.jobxinseeker.service;

import java.util.List;

import com.danglich.jobxinseeker.dto.RegisterDTO;
import com.danglich.jobxinseeker.dto.SeekerInfoDTO;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.model.Jobs;

public interface JobSeekerService {
	
	JobSeekers register(RegisterDTO registerDTO);
	
	SeekerInfoDTO getSeekerInfo(String username);
	
	JobSeekers getByUsername(String username);
	
	JobSeekers getCurrentUser();
	
	void saveJob(int jobId);
	
	void unSaveJob(int jobId);

}
