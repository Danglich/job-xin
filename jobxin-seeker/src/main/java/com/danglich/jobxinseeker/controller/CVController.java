package com.danglich.jobxinseeker.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.ResourceAccessException;

import com.danglich.jobxinseeker.model.CV;
import com.danglich.jobxinseeker.repository.CVRepository;
import com.danglich.jobxinseeker.service.CVService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CVController {
	
	private final CVService service;
	
	@GetMapping("/xem-cv/{cvId}")
    public ResponseEntity<Resource> viewCV(@PathVariable("cvId") int id) {
        CV cv = service.getById(id);
       
        ByteArrayResource resource = new ByteArrayResource(cv.getContent());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + cv.getFilename())
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(resource.contentLength())
                .body(resource);
    }

}
