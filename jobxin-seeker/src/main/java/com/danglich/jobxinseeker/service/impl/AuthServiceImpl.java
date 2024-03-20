package com.danglich.jobxinseeker.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import com.danglich.jobxinseeker.dto.LoginDTO;
import com.danglich.jobxinseeker.dto.RegisterDTO;
import com.danglich.jobxinseeker.exception.AuthException;
import com.danglich.jobxinseeker.exception.ConfirmationTokenExpiredException;
import com.danglich.jobxinseeker.exception.ExitedRegistationException;
import com.danglich.jobxinseeker.model.ConfirmationToken;
import com.danglich.jobxinseeker.model.CustomUserDetail;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.model.Provider;
import com.danglich.jobxinseeker.repository.ConfirmationTokenRepository;
import com.danglich.jobxinseeker.repository.JobSeekerRepository;
import com.danglich.jobxinseeker.service.AuthService;
import com.danglich.jobxinseeker.service.ConfirmationTokenService;
import com.danglich.jobxinseeker.service.MailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
	
	private final JobSeekerRepository seekerRepository;
	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;
	private final ConfirmationTokenService confirmationTokenService;
	private final ConfirmationTokenRepository confirmationTokenRepository;
	private final AuthenticationManager authenticationManager;
	
	@Override
	@Transactional
	public void register(RegisterDTO registerDTO) {
		
		Optional<JobSeekers> seekerOptional = seekerRepository.findByEmail(registerDTO.getEmail());
		
		if(seekerOptional.isPresent()) {
			if(seekerOptional.get().isEnabled())
				throw new AuthException("Email đã có tài khoản, vui lòng đăng nhập");
			else
				throw new ExitedRegistationException("This email already registered!");
		}
		
		String token = confirmationTokenService.save(
				seekerRepository.save(JobSeekers.builder()
							.email(registerDTO.getEmail())
							.password(passwordEncoder.encode(registerDTO.getPassword()))
							.enabled(false)
							.provider(Provider.DATABASE)
							.build()));
		
		
		String link = "http://localhost:8080/auth/confirm?token=" + token;
		String email = this.buildEmailConfirm("", link);
		mailService.send(registerDTO.getEmail(), "Xác thực tài khoản", email);
		
		
	} 

	@Override
	public void reSendConfirmationToken(String email) {
		JobSeekers seeker = seekerRepository.findByEmail(email)
								.orElseThrow(() -> new ResourceAccessException("Not found accout with email : " + email));
		
		String token = confirmationTokenService.save(seeker);
		String link = "http://localhost:8080/auth/confirm?token=" + token;
		String emailHtml = this.buildEmailConfirm("", link);
		mailService.send(email, "Xác thực tài khoản", emailHtml);
		
	}
	
	private String buildEmailConfirm(String name, String link) {
		// Nội dung email dưới dạng HTML với CSS
        String htmlContent = "<html><head><style>"
                + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; }"
                + "h1 { color: #333333; }"
                + "p { color: #666666; }"
                + "a { color: #007bff; text-decoration: none; }"
                + "</style></head><body>"
                + "<h1>Xin chào, " + name + "!</h1>"
                + "<p>Cảm ơn bạn đã đăng ký tài khoản của chúng tôi. Rất vui vì điều đó !</p>"
                + "<p>Vui lòng click vào đường dẫn sau để kích hoạt tài khoản của bạn: <a href=\"" + link + "\">Kích hoạt</a></p>"
                + "<p>Kích hoạt sẽ hết hiệu lực trong <strong>24h</strong></p>"
                + "</body></html>";

        return htmlContent;
    }
	
	private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

	@Override
	@Transactional
	public void confirm(String token , HttpServletRequest request, HttpServletResponse response) {
		ConfirmationToken confirmationToken = 
				confirmationTokenRepository.findByToken(token)
				  			.orElseThrow(() -> new ResourceAccessException("Not found the token"));
		if(confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())) {
			throw new ConfirmationTokenExpiredException("Token has expired");
		}
		
		confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
		JobSeekers seeker = confirmationToken.getSeeker();
		seekerRepository.enable(confirmationToken.getSeeker().getId());
		
		// Auto authenticate
		SecurityContextRepository securityContextRepository =
		        new HttpSessionSecurityContextRepository(); 

		CustomUserDetail userDetail = new CustomUserDetail(seeker);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        		seeker.getEmail(), null, userDetail.getAuthorities()); 
        
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication); 
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response); 
        
        
	}

	@Override
	public String login(LoginDTO loginDTO, HttpServletRequest request , HttpServletResponse response) {
		
		SecurityContextRepository securityContextRepository =
		        new HttpSessionSecurityContextRepository();
		
			UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
				loginDTO.getEmail(), loginDTO.getPassword()); 
		    Authentication authentication = authenticationManager.authenticate(token); 
		    SecurityContext context = securityContextHolderStrategy.createEmptyContext();
		    context.setAuthentication(authentication); 
		    securityContextHolderStrategy.setContext(context);
		    securityContextRepository.saveContext(context, request, response); 
		    
		    HttpSession session = request.getSession();
	        SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
	        if(savedRequest != null) 
	        	return savedRequest.getRedirectUrl();
	        else return null;
		
	}

	@Override
	@Transactional
	public void resetPassword(String email) {
		JobSeekers seeker =  seekerRepository.findByEmail(email)
							.orElseThrow(() -> new ResourceAccessException("Tài khoản email của bạn chưa đăng ký"));
		String randomPassword = UUID.randomUUID().toString().substring(0, 8);
		String passwordEncode = passwordEncoder.encode(randomPassword);
		
		seekerRepository.resetPassword(seeker.getId(), passwordEncode);
		
		String htmlContent = "<html><head><style>"
                + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; }"
                + "h1 { color: #333333; }"
                + "p { color: #666666; }"
                + "a { color: #007bff; text-decoration: none; }"
                + "</style></head><body>"
                + "<h1>Xin chào, </h1>" 
                + "<p>Mật khẩu mới của bạn là : " + randomPassword + "</p>"
                + "<p>Vui lòng tuyệt đối không cho ai biết thông tin mật khẩu này!</p>"
                + "</body></html>";
		
		mailService.send(seeker.getEmail(), "Gửi lại mật khẩu", htmlContent);
		
	}

}
