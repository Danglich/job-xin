package com.danglich.jobxinseeker.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.danglich.jobxinseeker.dto.ApplicationDTO;
import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.service.JobService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JobController {
	
	private final JobService service;
	
	@GetMapping("/viec-lam")
	public String showJobPage() {
		
		return "job/index";
	}
	
	@GetMapping("/viec-lam-tot-nhat")
	public String showTopJob() {
		
		return "job/hot-job";
	}
	
	@GetMapping("/viec-lam-moi-nhat")
	public String showNewJob(
			@RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {
		
		Page<Jobs> jobPage = service.getNewestJob(page);
		model.addAttribute("jobs", jobPage.getContent());
        model.addAttribute("currentPage", jobPage.getNumber());
        model.addAttribute("totalPages", jobPage.getTotalPages());
        model.addAttribute("totalElements", jobPage.getTotalElements());
		
        
		return "job/new-job";
	}
	
	@GetMapping("/viec-lam-da-luu")
	public String showSavedJob(Model theModel) {
		List<Jobs> savedJobs =  service.getJobsSaved();
		
		theModel.addAttribute("savedJobs", savedJobs);
		
		return "job/saved";
	}

	@GetMapping("/viec-lam/{jobId}")
	public String showJobDetail(@PathVariable(name = "jobId") int jobId , Model theModel) {
		
		Jobs job = service.getById(jobId);
		theModel.addAttribute("job", job);
		
		List<Jobs> suggestJobs = service.getSuggestJobsByCategory(job.getCompany().getId());
		theModel.addAttribute("suggestJobs", suggestJobs);
		
		List<Jobs> suggestJobsByUser = service.getTop5SuggestJobs();
		theModel.addAttribute("suggestJobsByUser", suggestJobsByUser);
		
		return "job/job-detail";
	}
	
	
	@GetMapping("/ung-tuyen/{jobId}")
	public String showApplicationForm(@PathVariable(name = "jobId") int jobId , Model theModel) {
		
		Jobs job = service.getById(jobId);
		theModel.addAttribute("job", job);
		
		List<Jobs> suggestJobsByUser = service.getTop5SuggestJobs();
		theModel.addAttribute("suggestJobsByUser", suggestJobsByUser);
		
		ApplicationDTO application = new ApplicationDTO();
		application.setJobId(job.getId());
		theModel.addAttribute("application", application);
		
		return "application/application-form";
	}
	
	
}
