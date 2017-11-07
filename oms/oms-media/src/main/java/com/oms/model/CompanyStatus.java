package com.oms.model;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "COMPANY_STATUS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyStatus extends BaseEntity implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7458926180641213684L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "COMPANY_STATUS_ID")
	private Long companyStatusId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="STATUS")
	private Boolean status;

	
	public Long getCompanyStatusId() {
		return companyStatusId;
	}

	public void setCompanyStatusId(Long companyStatusId) {
		this.companyStatusId = companyStatusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public CompanyStatus() {
		super();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	
	

	

}
