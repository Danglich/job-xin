package com.danglich.jobxinseeker.validation;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.danglich.jobxinseeker.dto.LoginDTO;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LoginFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return LoginDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
    }

	

	
}
