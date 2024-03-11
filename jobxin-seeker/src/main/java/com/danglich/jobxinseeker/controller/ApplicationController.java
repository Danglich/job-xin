package com.danglich.jobxinseeker.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danglich.jobxinseeker.dto.ApplicationDTO;
import com.danglich.jobxinseeker.service.ApplicationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ApplicationController {
	
	private final ApplicationService service;

	@GetMapping("/lich-su-ung-tuyen") 
	public String showApplied() {
		
		return "application/applied";
	}
	
	@PostMapping("/ung-tuyen")
	public String showApplyPage(@ModelAttribute("application") ApplicationDTO applicationDTO) throws IOException {
		service.create(applicationDTO);
		
		return "redirect:/lich-su-ung-tuyen";
		
	}
	
	
}
