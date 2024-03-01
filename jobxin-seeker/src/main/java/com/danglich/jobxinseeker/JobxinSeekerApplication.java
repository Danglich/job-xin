package com.danglich.jobxinseeker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.danglich.jobxinseeker.dto.RegisterDTO;
import com.danglich.jobxinseeker.model.JobSeekers;

@SpringBootApplication
@EnableJpaAuditing
public class JobxinSeekerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobxinSeekerApplication.class, args);
	}

}
