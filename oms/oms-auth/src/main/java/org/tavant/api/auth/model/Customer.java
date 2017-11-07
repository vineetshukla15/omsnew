package org.tavant.api.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
// Generated Apr 21, 2017 12:57:50 PM by Hibernate Tools 3.4.0.CR1

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "CUSTOMER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer extends AuthBaseEntity implements Serializable {

	private static final long serialVersionUID = -6975275943668047845L;

	@Id
	@GeneratedValue
	@Column(name = "CUSTOMER_ID")
	private Long customerID;
	
	@Column(name = "NAME")
	@NotNull
	@NotBlank
	private String customerName;
	
	@Column(name="EMAIL")
	@Email
	private String customerEmail;
	
	@Column(name="PHONE")
	@Size(min=10)
	private String customerPhone;
	
	@Column(name="STATUS")
	private int status;
	
	@Column(name="ADDRESS")
	private String customerAddress;

	public Long getCustomerID() {
		return customerID;
	}
	

	/**
	 * 
	 */
	 public Customer() {
		super();
		
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
