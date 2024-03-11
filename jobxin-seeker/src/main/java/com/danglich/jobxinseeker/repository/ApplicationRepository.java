package com.danglich.jobxinseeker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danglich.jobxinseeker.model.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>{

}
