package com.danglich.jobxinseeker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danglich.jobxinseeker.model.Category;
import com.danglich.jobxinseeker.model.Company;
import com.danglich.jobxinseeker.model.Jobs;

public interface JobRepository extends JpaRepository<Jobs, Integer>{

	Page<Jobs> findByExpiredAtAfter(LocalDateTime currentDate, Pageable pageable);
	
	List<Jobs> findTop5ByCategoryId(int categoryId);
	
	List<Jobs> findTop4ByCategoryInOrderByCreatedAtDesc(List<Category> categories);
	
	List<Jobs> findTop4ByOrderByCreatedAtDesc();
	
	@Query("SELECT j FROM Jobs j LEFT JOIN j.applications a WHERE j.expiredAt > :currentDate GROUP BY j ORDER BY COALESCE(COUNT(a), 0) DESC")
	Page<Jobs> findTopJobsByApplicationCount(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);
	
	Page<Jobs> findByCategoryInAndExpiredAtAfter(List<Category> categories ,LocalDateTime currentDate, Pageable pageable);
	
	Page<Jobs> findByTitleContainingAndCompany(String keyword , Company company , Pageable pageable);
	
//	@Query("SELECT j, COALESCE(COUNT(a), 0) AS applicationCount FROM Jobs j LEFT JOIN j.applications a GROUP BY j ORDER BY applicationCount DESC")
//	Page<Object[]> findTopJobsByApplicationCount(Pageable pageable);
}
