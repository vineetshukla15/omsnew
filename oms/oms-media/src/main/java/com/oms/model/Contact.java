package com.oms.model;

import static javax.persistence.GenerationType.IDENTITY;

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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "CONTACT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -190298017222756228L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CONTACT_ID")
	private Long contactId;

	@NotNull(message = "Mandatory")
	@NotBlank(message = "Mandatory")
	@Pattern(regexp = "^[A-Za-z ]*$", message = "Name has invalid characters")
	@Column(name = "NAME")
	private String contactName;

	@NotNull(message = "Mandatory")
	@NotBlank(message = "Mandatory")
	@Email
	@Column(name = "EMAIL")
	private String contactEmail;

	@Size(min = 10)
	@Pattern(regexp = "^[0-9]*$", message = "Phone number has invalid characters")
	@Column(name = "PHONE")
	private String contactPhone;

	@Size(min = 10)
	@Pattern(regexp = "^[0-9]*$", message = "Mobile number has invalid characters")
	@Column(name = "MOBILE")
	private String contactMobile;

	@Column(name = "ADDRESS")
	private String contactAddress;

	@ManyToOne
	@JoinColumn(name = "COMPANY_ID")
	private Company company;

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
