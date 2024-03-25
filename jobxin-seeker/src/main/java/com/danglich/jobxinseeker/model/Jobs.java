package com.danglich.jobxinseeker.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(callSuper=false)
@Table(name = "job")
public class Jobs extends DateAudit{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	
	
	@Column(name = "salary_start")
	private int salaryStart;
	
	@Column(name = "salary_end")
	private int salaryEnd;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "title")
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "expired_at")
	private LocalDateTime expiredAt;
	
	@Column(name = "experience")
	@Enumerated(EnumType.STRING)
	private Experience experience;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@OneToMany(mappedBy = "job")
	private List<Application> applications;

}
