package com.danglich.jobxinseeker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danglich.jobxinseeker.model.CV;

@Repository
public interface CVRepository  extends JpaRepository<CV, Integer>{

}
