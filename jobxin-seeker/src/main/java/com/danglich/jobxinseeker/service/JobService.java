package com.danglich.jobxinseeker.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.danglich.jobxinseeker.model.Category;
import com.danglich.jobxinseeker.model.Jobs;

public interface JobService {
	
	Page<Jobs> getNewestJob(int page);
	
	Jobs getById(int id);
	
	List<Jobs> getSuggestJobsByCategory(int categoryId);
	
	List<Jobs> getTop5SuggestJobs();

}
