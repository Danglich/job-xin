package com.danglich.jobxinseeker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ung-tuyen")
public class ApplicationController {

	@GetMapping("/lich-su-ung-tuyen") 
	public String showApplied() {
		
		return "application/applied";
	}
}
