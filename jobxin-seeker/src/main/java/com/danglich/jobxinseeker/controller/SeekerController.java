package com.danglich.jobxinseeker.controller;

import java.security.Principal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.danglich.jobxinseeker.dto.ChangePasswordDTO;
import com.danglich.jobxinseeker.dto.SeekerInfoDTO;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.service.JobSeekerService;

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
	
	@GetMapping("/doi-mat-khau")
	public String showChangePWPage(Principal principal , Model theModel) {
		
		ChangePasswordDTO passwordDTO = new ChangePasswordDTO(principal.getName(), null, null, null);
		
		theModel.addAttribute("passwordDTO", passwordDTO);
		
		return "profile/change_password";
	}

}
