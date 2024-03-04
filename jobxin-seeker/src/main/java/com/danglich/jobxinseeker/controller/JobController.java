package com.danglich.jobxinseeker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/viec-lam")
public class JobController {
	
	@GetMapping()
	public String showJobPage() {
		
		return "job-list/index";
	}

	@GetMapping("/{job_id}")
	public String showJobDetail(@PathVariable(name = "job_id") int jobId) {
		
		return "job-detail/index";
	}
}
