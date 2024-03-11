package com.danglich.jobxinseeker.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import com.danglich.jobxinseeker.model.CV;

public interface CVService {
	
	CV upload(MultipartFile file) throws IOException;

}
