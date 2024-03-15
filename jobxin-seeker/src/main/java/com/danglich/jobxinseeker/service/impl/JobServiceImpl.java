package com.danglich.jobxinseeker.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.repository.JobRepository;
import com.danglich.jobxinseeker.service.JobSeekerService;
import com.danglich.jobxinseeker.service.JobService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService{
	
	private JobRepository repository;
	
	private JobSeekerService seekerService;
	
	@Autowired
    public JobServiceImpl(@Lazy JobSeekerService seekerService, JobRepository repository) {
        this.repository = repository;
        this.seekerService = seekerService;
    }

	@Override
	public Page<Jobs> getNewestJob(int page) {
		
		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		Pageable pageable = PageRequest.of(page, 8, sort); 
		
		Page<Jobs> jobPage = repository.findByExpiredAtAfter(LocalDateTime.now(), pageable);
		
		return jobPage;
	}

	@Override
	public Jobs getById(int id) {
		
		return repository.findById(id).orElseThrow(() -> new ResourceAccessException("Not found job"));
	}

	@Override
	public List<Jobs> getSuggestJobsByCategory(int categoryId) {
		
		return repository.findTop5ByCategoryId(categoryId);
	}

	@Override
	public List<Jobs> getTop5SuggestJobs() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication.isAuthenticated()) {
			JobSeekers jobSeeker = seekerService.getByUsername(authentication.getName());
			
			return repository.findTop5ByCategoryInOrderByCreatedAtDesc(jobSeeker.getCategories());
		}
		
		return repository.findTop5ByOrderByCreatedAtDesc();
	}


	@Override
	public List<Jobs> getJobsSaved() {
		
		JobSeekers seeker = seekerService.getCurrentUser();
		
		return seeker.getSavedJobs();
	}

}
