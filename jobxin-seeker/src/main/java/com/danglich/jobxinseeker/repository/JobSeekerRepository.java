package com.danglich.jobxinseeker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danglich.jobxinseeker.model.JobSeekers;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeekers, Integer>{
	
	Optional<JobSeekers> findByEmail(String email);

}
