package com.danglich.jobxinseeker.service;

import java.io.IOException;

import com.danglich.jobxinseeker.dto.ApplicationDTO;
import com.danglich.jobxinseeker.model.Application;

public interface ApplicationService {
	
	Application create(ApplicationDTO form) throws IOException;

}
