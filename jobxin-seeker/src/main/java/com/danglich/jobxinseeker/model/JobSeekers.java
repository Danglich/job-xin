package com.danglich.jobxinseeker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seeker")
public class JobSeekers extends DateAudit {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "avatar")
	private String avatar; 
	
	@Column(name = "enabled")
	private boolean enabled;
	
	@Column(name = "provider")
	@Enumerated(EnumType.STRING)
	private Provider provider;
	
	@ManyToMany(fetch = FetchType.EAGER ,cascade = {CascadeType.PERSIST , CascadeType.MERGE , CascadeType.REFRESH , CascadeType.DETACH})
	@JoinTable(
	  name = "seeker_category", 
	  joinColumns = @JoinColumn(name = "seeker_id"), 
	  inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories;
	
	@ManyToMany(cascade = {CascadeType.PERSIST , CascadeType.MERGE , CascadeType.REFRESH , CascadeType.DETACH})
	@JoinTable(
	  name = "job_saved", 
	  joinColumns = @JoinColumn(name = "seeker_id"), 
	  inverseJoinColumns = @JoinColumn(name = "job_id"))
	private List<Jobs> savedJobs;
	
	@OneToMany(mappedBy = "seeker")
	private List<Application> applications;
	
	
	public void saveJob(Jobs job) {
		if(savedJobs == null) {
			savedJobs = new ArrayList<>();
		}
		if(!savedJobs.contains(job)) {
			savedJobs.add(job);
			
		} 
		
	}
	
	public void unSaveJob(Jobs job) {
		if(savedJobs == null) {
			savedJobs = new ArrayList<>();
		}
		if(savedJobs.contains(job)) {
			savedJobs.remove(job);
			
		} 
		
	}
	
	

}
