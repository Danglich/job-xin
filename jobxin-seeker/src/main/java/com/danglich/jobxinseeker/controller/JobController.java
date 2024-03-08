package com.danglich.jobxinseeker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JobController {
	
	@GetMapping("/viec-lam")
	public String showJobPage() {
		
		return "job-list/index";
	}
	
	@GetMapping("/viec-lam-tot-nhat")
	public String showTopJob() {
		
		return "job/hot-job";
	}
	
	@GetMapping("/viec-lam-moi-nhat")
	public String showNewJob() {
		
		return "job/new-job";
	}
	
	@GetMapping("/viec-lam-da-luu")
	public String showSavedJob() {
		
		return "job/saved";
	}

	@GetMapping("/viec-lam/{job_id}")
	public String showJobDetail(@PathVariable(name = "job_id") int jobId) {
		
		return "job-detail/index";
	}
}
