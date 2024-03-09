package com.danglich.jobxinseeker.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.repository.JobRepository;
import com.danglich.jobxinseeker.service.JobService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService{
	
	private final JobRepository repository;

	@Override
	public Page<Jobs> getNewestJob(int page) {
		
		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		Pageable pageable = PageRequest.of(page, 8, sort); 
		
		Page<Jobs> jobPage = repository.findByExpiredAtAfter(LocalDateTime.now(), pageable);
		
		return jobPage;
	}

}
