package com.danglich.jobxinseeker.service;

import org.springframework.data.domain.Page;

import com.danglich.jobxinseeker.model.Jobs;

public interface JobService {
	
	Page<Jobs> getNewestJob(int page);

}
