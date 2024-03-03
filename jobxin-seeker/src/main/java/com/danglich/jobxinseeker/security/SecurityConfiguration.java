package com.danglich.jobxinseeker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.danglich.jobxinseeker.model.CustomUserDetail;
import com.danglich.jobxinseeker.service.impl.CustomUserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	
	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				configurer ->
					configurer
						.requestMatchers("/").permitAll()
						.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/style/**").permitAll()
						.anyRequest().authenticated()
			
				)
			.formLogin(form -> form.loginPage("/auth/login")
									.loginProcessingUrl("/login-process")
									.defaultSuccessUrl("/", true)
									.usernameParameter("email")
									.permitAll()
					)
			.logout(logout -> 
				logout.logoutUrl("/logout")
					.logoutSuccessUrl("/auth/login?logout=true")
					  .permitAll())
			.authenticationProvider(authenticationProvider)
			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
		;

		return http.build();
	}
	
	
	
	
	

}
