package com.danglich.jobxinseeker.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	
	
	@ManyToMany(cascade = {CascadeType.PERSIST , CascadeType.MERGE , CascadeType.REFRESH , CascadeType.DETACH})
	@JoinTable(
	  name = "seeker_category", 
	  joinColumns = @JoinColumn(name = "seeker_id"), 
	  inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories;
	
	

}
