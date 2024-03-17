package com.danglich.jobxinseeker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.danglich.jobxinseeker.model.JobSeekers;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeekers, Integer>{
	
	@Modifying
	@Query("UPDATE JobSeekers s " +
            "SET s.enabled = TRUE " +
            "WHERE s.id = ?1")
    void enable(int seekerId );
	
	Optional<JobSeekers> findByEmail(String email);

}
