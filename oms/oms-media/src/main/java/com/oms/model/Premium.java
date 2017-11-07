package com.oms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author vikas.parashar
 *
 */
@Entity
@Table(name = "PREMIUM")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Premium extends BaseEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 321494695334468301L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PREMIUM_ID", unique = true, nullable = false)
	private Long premiumId;
	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "TARGET_TYPE_ID")
	//private AudienceTargetType audienceTargetType;

	@Column(name = "PREMIUM_PERCENTAGE", precision = 22, scale = 0)
	private Double premiumPercentage;
	@Column(name = "STATUS")
	private Boolean status;
	
	@NotNull
	@Column(name = "TARGET_TYPE_ID", unique = true, nullable = false)
	private Long targetTypeId;
	/*
	//@JsonBackReference
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="RATE_CARD_ID")
	RateCard rateCardProfile;*/
	
	
	public Premium() {
	}

	public Premium(Long premiumId) {
		this.premiumId = premiumId;
	}

	public Long getPremiumId() {
		return this.premiumId;
	}

	public void setPremiumId(Long premiumId) {
		this.premiumId = premiumId;
	}


	public Double getPremiumPercentage() {
		return this.premiumPercentage;
	}

	public void setPremiumPercentage(Double premiumPercentage) {
		this.premiumPercentage = premiumPercentage;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Long getTargetTypeId() {
		return targetTypeId;
	}

	public void setTargetTypeId(Long targetTypeId) {
		this.targetTypeId = targetTypeId;
	}

	
}
