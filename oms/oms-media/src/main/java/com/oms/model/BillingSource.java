package com.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.tavant.api.auth.model.Customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "BILLING_SOURCE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingSource extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BILLING_SOURCE_ID")
	private long billingSourceId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "STATUS")
	private boolean status;
	
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	public long getBillingSourceId() {
		return billingSourceId;
	}

	public void setBillingSourceId(long billingSourceId) {
		this.billingSourceId = billingSourceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
