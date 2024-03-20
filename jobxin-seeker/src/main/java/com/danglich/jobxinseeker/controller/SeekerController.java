package com.danglich.jobxinseeker.controller;

import java.security.Principal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danglich.jobxinseeker.dto.ChangePasswordDTO;
import com.danglich.jobxinseeker.dto.SeekerInfoDTO;
import com.danglich.jobxinseeker.exception.IncorrectPasswordException;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.service.JobSeekerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SeekerController {
	
	private final JobSeekerService seekerService;
	
	@GetMapping("/thong-tin-ca-nhan")
	public String showSeekerInfoPage(Principal principal , Model theModel) {
		
		SeekerInfoDTO seekerInfo = seekerService.getSeekerInfo(principal.getName());
		theModel.addAttribute("information", seekerInfo);
		
		return "profile/information";
	}
	
	@PostMapping("/change-info")
	public String changeInfo(@Valid @ModelAttribute(name = "information") SeekerInfoDTO request ,
			BindingResult bindingResult ,
			RedirectAttributes redirectAttributes
			) {
		
		if(bindingResult.hasErrors() || (request.getFullName()==null && request.getPhoneNumber()==null)) {
			return "profile/information";
		}
		seekerService.updateInfo(request);
		redirectAttributes.addAttribute("success", true);
		return "redirect:/thong-tin-ca-nhan";
		
	}
	@GetMapping("/doi-mat-khau")
	public String showChangePWPage(Principal principal , Model theModel) {
		
		ChangePasswordDTO passwordDTO = new ChangePasswordDTO(principal.getName(), null, null, null);
		theModel.addAttribute("passwordDTO", passwordDTO);
		return "profile/change_password";
	}
	
	@PostMapping("/change-password")
	public String changePassword(@Valid @ModelAttribute(name = "passwordDTO") ChangePasswordDTO passwordDTO, 
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes,
			Model theModel) {
		if(bindingResult.hasErrors()) {
			return "profile/change_password";
		}
		
		try {
			seekerService.changePassword(passwordDTO);
		} catch (IncorrectPasswordException e) {
			// Incorrect password
			theModel.addAttribute("errorMessage", e.getMessage());
			return "profile/change_password";
		}
		redirectAttributes.addAttribute("success", true);
		return "redirect:/doi-mat-khau";
	}
	
	@PostMapping("/save-job")
	public String saveJob(@RequestParam(name = "jobId") int jobId) {
		
		seekerService.saveJob(jobId);
		
		return "redirect:viec-lam-da-luu";
	}
	
	@PostMapping("/unsave-job")
	public String unSaveJob(@RequestParam(name = "jobId") int jobId) {
		
		seekerService.unSaveJob(jobId);
		
		return "redirect:viec-lam-da-luu";
	}

}
