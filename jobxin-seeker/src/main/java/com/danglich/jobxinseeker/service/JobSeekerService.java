package com.danglich.jobxinseeker.service;

import com.danglich.jobxinseeker.dto.RegisterDTO;
import com.danglich.jobxinseeker.model.JobSeekers;

public interface JobSeekerService {
	
	JobSeekers register(RegisterDTO registerDTO);

}
