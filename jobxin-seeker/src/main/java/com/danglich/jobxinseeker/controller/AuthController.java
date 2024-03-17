package com.danglich.jobxinseeker.controller;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.spring6.expression.Fields;

import com.danglich.jobxinseeker.dto.LoginDTO;
import com.danglich.jobxinseeker.dto.RegisterDTO;
import com.danglich.jobxinseeker.exception.AuthException;
import com.danglich.jobxinseeker.exception.ExitedRegistationException;
import com.danglich.jobxinseeker.service.AuthService;
import com.danglich.jobxinseeker.service.JobSeekerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final AuthService authService;
	
	@GetMapping("/login")
	public String showLoginPage(Model theModel) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			
		    return "redirect:/";
		}
		theModel.addAttribute("loginDTO", new LoginDTO());
		
		return "auth/login-form";
	}
	
	@PostMapping("/login-process")
	public String login(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO , BindingResult theBindingResult,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response
			) {
		if(theBindingResult.hasErrors()) {
			
			return "auth/login-form";
		} 
		
		String redirectUrl = authService.login(loginDTO, request, response);
		if(redirectUrl == null)  redirectUrl = "/";
			
		return "redirect:" + redirectUrl;
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public String handleBadCredentialsException(BadCredentialsException e , RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("errorMessage", "Email hoặc mật khẩu không chính xác");
		return "redirect:/auth/login";
	}
	
	@ExceptionHandler(DisabledException.class)
	public String handleDisabledExceptionException(DisabledException e , RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("errorMessage", "Tài khoản của quý khách chưa được kích hoạt");
		return "redirect:/auth/login";
	}
	
	
	@GetMapping("/register")
	public String showRegisterPage(Model theModel) {
		RegisterDTO registerDTO = new RegisterDTO();
		theModel.addAttribute("registerDTO", registerDTO);
		
		return "auth/register-form";
	}
	
	@GetMapping("/confirm")
	public String confirm(@RequestParam(name = "token",required = true) String token , HttpServletRequest request, HttpServletResponse response) {
		authService.confirm(token, request, response);
		
		return "redirect:/";
	}
	
	@GetMapping("/register-success")
	public String showRegistationSucess() {
		
		return "auth/register-success";
	}
	
	@PostMapping("/re-send-token")
	public String reSendToken(@ModelAttribute(name = "email") String email) {
		
		authService.reSendConfirmationToken(email);
		return "redirect:/auth/register-success";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO , 
			BindingResult theBindingResult,
			RedirectAttributes redirectAttributes
			) {
		
		
		if(theBindingResult.hasErrors()) {
			return "auth/register-form";
		}
		
		try {
			authService.register(registerDTO);
			
		} catch (AuthException e) {
			log.error(e.getMessage());
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			return "redirect:/auth/register";
		} catch (ExitedRegistationException e) {
			log.error(e.getMessage());
			redirectAttributes.addFlashAttribute("isRegistered", true);
			redirectAttributes.addFlashAttribute("registeredEmail", registerDTO.getEmail());
			return "redirect:/auth/register";
		}
		
		return "redirect:/auth/register-success";
	}
	
	
	// Forget password
	@GetMapping("/forget-password")
	public String showForgetPasswordPage(Model theModel) {
		theModel.addAttribute("email", new String());
		
		return "auth/forget-password";
	}
	
	// Forget password
	@PostMapping("/forget-password")
	public void handleForgetPassword(@ModelAttribute("email") String email) {
		
		System.out.println(email);
			
	}

}
