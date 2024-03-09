package com.danglich.jobxinseeker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danglich.jobxinseeker.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer>{

}
