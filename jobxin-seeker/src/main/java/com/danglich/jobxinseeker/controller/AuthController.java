package com.danglich.jobxinseeker.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danglich.jobxinseeker.dto.LoginDTO;
import com.danglich.jobxinseeker.dto.RegisterDTO;
import com.danglich.jobxinseeker.model.Student;
import com.danglich.jobxinseeker.service.JobSeekerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final JobSeekerService jobSeekerService;
	private final AuthenticationManager authenticationManager;
	
	@GetMapping("/login")
	public String showLoginPage(Model theModel) {
		theModel.addAttribute("loginDTO", new LoginDTO());
		
		return "auth/login-form";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO , BindingResult theBindingResult,
			RedirectAttributes redirectAttributes
			) {
		if(theBindingResult.hasErrors()) {
			return "auth/login-form";
		}
		
		try {
			
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/auth/login";
		}
		
		
		
		return "redirect:/";
	}
	
	
	@GetMapping("/register")
	public String showRegisterPage(Model theModel) {
		RegisterDTO registerDTO = new RegisterDTO();
		theModel.addAttribute("registerDTO", registerDTO);
		
		return "auth/register-form";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO , BindingResult theBindingResult,
			RedirectAttributes redirectAttributes
			) {
		
		
		if(theBindingResult.hasErrors()) {
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