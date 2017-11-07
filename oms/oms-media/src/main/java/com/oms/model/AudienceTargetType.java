package com.oms.model;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author vikas.parashar
 *
 */
@Entity
@Table(name = "AUDIENCE_TARGET_TYPE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AudienceTargetType extends BaseEntity implements java.io.Serializable { 

	/**
	 * 
	 */
	private static final long serialVersionUID = 2008550919725590817L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "TARGET_ID", unique = true, nullable = false)
	private Long targetTypeId; 
	
	@Column(name = "NAME", length = 45)
	private String name;
	
	@Column(name = "Status")
	private Boolean status;
	
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="TARGET_TYPE_ID")
	private List<AudienceTargetValues> audienceTargetValues;
	
	public AudienceTargetType() {
	}

	public AudienceTargetType(String name, Boolean status,
			Set<LineItems> lineItemses, 
			Set<AudienceTargetValues> audienceTargetValueses) {
		this.name = name;
		this.status = status;
		
	}

	
	public Long getTargetTypeId() {
		return this.targetTypeId;
	}

	public void setTargetTypeId(Long targetTypeId) {
		this.targetTypeId = targetTypeId;
	}

	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<AudienceTargetValues> getAudienceTargetValues() {
		return audienceTargetValues;
	}

	public void setAudienceTargetValues(List<AudienceTargetValues> audienceTargetValues) {
		this.audienceTargetValues = audienceTargetValues;
	}
	
	
	
}
