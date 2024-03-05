package com.danglich.jobxinseeker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cong-ty")
public class CompanyController {
	
	@GetMapping
	public String showListCompany() {
		
		return "company/list";
	}
	
	@GetMapping("/{company_id}")
	public String showCompanyDetail(@PathVariable(name = "company_id") int companyId) {
		
		return "company/detail";
	}

}
