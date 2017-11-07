package com.oms.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.tavant.api.auth.model.Customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "AD_UNIT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ADUnit extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1786249397664783762L; 

	@Id
	@GeneratedValue
	@Column(name = "AD_UNIT_ID")
	private Long adUnitId;

	@NotNull(message="Mandatory")
	@NotBlank(message="Mandatory")
	@Pattern(regexp = "^[A-Za-z ]*$", message = "Name has invalid characters")
	@Column(name = "NAME")
	private String name;

	@Column(name = "DISPLAY_NAME")
	private String displayName;

	@Column(name = "IS_ACTIVE", columnDefinition = "BIT")
	private boolean isActive;


	@Column(name = "CAPACITY")
	private BigDecimal capacity;

	@Column(name = "WEIGHT")
	private BigDecimal weight;
	
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	/*@JsonBackReference
	@OneToOne(optional=false, mappedBy="adUnits")
	private RateCard rateCardProfile;*/


	/**
	 * 
	 */
	 public ADUnit() {
		super();
		
	}

	/**
	 * @param salesTargetId
	 * @param name
	 * @param displayName
	 * @param isActive
	 * @param version
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 * @param capacity
	 * @param weight
	 */
	public ADUnit(Long adUnitId, String name, String displayName, boolean isActive, Integer version,
			String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, BigDecimal capacity,
			BigDecimal weight) {
		super();
		this.adUnitId = adUnitId;
		this.name = name;
		this.displayName = displayName;
		this.isActive = isActive;
		this.capacity = capacity;
		this.weight = weight;
	}

	
	
	
/*	*//**
	 * @return the rateCardProfile
	 *//*
	public RateCard getRateCardProfile() {
		return rateCardProfile;
	}

	*//**
	 * @param rateCardProfile the rateCardProfile to set
	 *//*
	public void setRateCardProfile(RateCard rateCardProfile) {
		this.rateCardProfile = rateCardProfile;
	}*/

	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public Long getAdUnitId() {
		return adUnitId;
	}

	public void setAdUnitId(Long adUnitId) {
		this.adUnitId = adUnitId;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the capacity
	 */
	public BigDecimal getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity
	 *            the capacity to set
	 */
	public void setCapacity(BigDecimal capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
