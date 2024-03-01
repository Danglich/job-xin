package com.danglich.jobxinseeker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danglich.jobxinseeker.dto.RegisterDTO;
import com.danglich.jobxinseeker.service.JobSeekerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final JobSeekerService jobSeekerService;
	
	@GetMapping("/login")
	public String showLoginPage() {
		
		return "auth/login-form";
	}
	
	@GetMapping("/register")
	public String showRegisterPage(Model theModel) {
		
		theModel.addAttribute("registerDTO", new RegisterDTO());
		return "auth/register-form";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute("registerDTO") @Valid RegisterDTO registerDTO ,
			BindingResult bindingResult ,
			RedirectAttributes redirectAttributes
			) {
		
		if(bindingResult.hasErrors()) {
			return "auth/register-form";
		}
		
		try {
			jobSeekerService.register(registerDTO);
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/auth/register";
		}
		
		return "redirect:/";
	}

}
