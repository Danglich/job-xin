package com.danglich.jobxinseeker.security;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
						.requestMatchers("/static/**").permitAll()
						.requestMatchers("/style/**").permitAll()
						.requestMatchers("/viec-lam/**").permitAll()
						.anyRequest().authenticated()
			
				)
			.formLogin(form -> form.loginPage("/auth/login")
									//.loginProcessingUrl("/login-process")
									//.defaultSuccessUrl("/", true)
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
	
	
	@Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {
            if (exception instanceof DisabledException) {
                
                response.sendRedirect("/disabled-account");
            } else {
                
                response.sendRedirect("/login-error");
            }
        }
    }
	
	
	

}
