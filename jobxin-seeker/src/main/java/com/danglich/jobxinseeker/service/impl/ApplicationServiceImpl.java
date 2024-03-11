package com.danglich.jobxinseeker.service.impl;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danglich.jobxinseeker.dto.ApplicationDTO;
import com.danglich.jobxinseeker.model.Application;
import com.danglich.jobxinseeker.model.ApplicationStatus;
import com.danglich.jobxinseeker.model.CV;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.repository.ApplicationRepository;
import com.danglich.jobxinseeker.service.ApplicationService;
import com.danglich.jobxinseeker.service.CVService;
import com.danglich.jobxinseeker.service.JobSeekerService;
import com.danglich.jobxinseeker.service.JobService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService{
	
	private final ApplicationRepository repository;
	private final JobService jobService;
	private final CVService cvService;
	private final JobSeekerService seekerService;

	@Override
	public Application create(ApplicationDTO form ) throws IOException {
		
		Jobs job = jobService.getById(form.getJobId());
		
		CV cv = cvService.upload(form.getFile());
		
		Application application = Application.builder()
									.cv(cv)
									.message(form.getMessage())
									.job(job)
									.status(ApplicationStatus.APPLIED)
									.seeker(cv.getSeeker())
									.build();
		
		
		return repository.save(application);
	}

	@Override
	public Page<Application> getAppliedForCurrentUser(int pageNumber, ApplicationStatus status) {
		JobSeekers seeker = seekerService.getCurrentUser();
		Pageable page = PageRequest.of(pageNumber, 6);
		
		if(status == null) {
			return repository.findBySeekerId(seeker.getId(), page); 
		}
		
		return repository.findBySeekerIdAndStatus(seeker.getId(),status , page);
	}

}
