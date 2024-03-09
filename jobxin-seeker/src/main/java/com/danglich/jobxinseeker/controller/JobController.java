package com.danglich.jobxinseeker.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.service.JobService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JobController {
	
	private final JobService service;
	
	@GetMapping("/viec-lam")
	public String showJobPage() {
		
		return "job-list/index";
	}
	
	@GetMapping("/viec-lam-tot-nhat")
	public String showTopJob() {
		
		return "job/hot-job";
	}
	
	@GetMapping("/viec-lam-moi-nhat")
	public String showNewJob(
			@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {
		
		Page<Jobs> jobPage = service.getNewestJob(page);
		model.addAttribute("jobs", jobPage.getContent());
        model.addAttribute("currentPage", jobPage.getNumber());
        model.addAttribute("totalPages", jobPage.getTotalPages());
        model.addAttribute("totalElements", jobPage.getTotalElements());
		LocalDateTime d =LocalDateTime.now();
		
        
		return "job/new-job";
	}
	
	@GetMapping("/viec-lam-da-luu")
	public String showSavedJob() {
		
		return "job/saved";
	}

	@GetMapping("/viec-lam/{jobId}")
	public String showJobDetail(@PathVariable(name = "jobId") int jobId) {
		
		return "job-detail/index";
	}
}
