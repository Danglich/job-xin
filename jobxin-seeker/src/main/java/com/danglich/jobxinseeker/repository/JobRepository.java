package com.danglich.jobxinseeker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.danglich.jobxinseeker.model.Category;
import com.danglich.jobxinseeker.model.Jobs;

public interface JobRepository extends JpaRepository<Jobs, Integer>{

	Page<Jobs> findByExpiredAtAfter(LocalDateTime currentDate, Pageable pageable);
	
	List<Jobs> findTop5ByCategoryId(int categoryId);
	
	List<Jobs> findTop5ByCategoryInOrderByCreatedAtDesc(List<Category> categories);
	
	List<Jobs> findTop5ByOrderByCreatedAtDesc();
}
