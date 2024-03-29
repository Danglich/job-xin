package com.danglich.jobxinseeker.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danglich.jobxinseeker.dto.SalaryRange;
import com.danglich.jobxinseeker.model.Company;
import com.danglich.jobxinseeker.model.Experience;
import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.service.AddressService;
import com.danglich.jobxinseeker.service.CompanyService;
import com.danglich.jobxinseeker.service.JobService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
	
	private final JobService jobService;
	private final CompanyService companyService;
	private final AddressService addressService;
	
	@GetMapping("/")
	public String showHome(Authentication authentication, Model theModel) {
        // for search form
		theModel.addAttribute("experiences", Experience.values());
		theModel.addAttribute("addresses", addressService.getAll());
		theModel.addAttribute("salaryRanges", SalaryRange.values());
		
		List<Jobs> newestJobs = jobService.getNewestJobs(0).getContent();
		theModel.addAttribute("newestJobs", newestJobs);
		
		List<Jobs> topJobs = jobService.getTopJobs(0).getContent();
		theModel.addAttribute("topJobs", topJobs);
		
		List<Jobs> suggestJobs = jobService.getTop4SuggestJobs();
		theModel.addAttribute("suggestJobs", suggestJobs);
		
		List<Company> topCompanies = companyService.getTopCompanies(0).getContent();
		theModel.addAttribute("topCompanies", topCompanies);

        return "home/index";
	}
	
}
