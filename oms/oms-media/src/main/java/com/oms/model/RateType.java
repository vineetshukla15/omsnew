package com.oms.model;


import java.io.Serializable;

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

@Entity
@Table(name = "RATE_TYPE")
public class RateType extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -1654578209598920557L;
	
	@Id
	@GeneratedValue
	@Column(name="RATE_TYPE_ID")
	private Long rateTypeId;
	
	@NotNull
	@NotBlank
	@Pattern(regexp = "^(?=.*[a-zA-Z]).+$", message = "Name has invalid characters")
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name = "STATUS", columnDefinition = "BIT")
	private boolean status;
	
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;


	public Long getRateTypeId() {
		return rateTypeId;
	}


	public void setRateTypeId(Long rateTypeId) {
		this.rateTypeId = rateTypeId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
