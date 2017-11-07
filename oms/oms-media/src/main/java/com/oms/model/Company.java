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

import org.hibernate.validator.constraints.NotBlank;
import org.tavant.api.auth.model.Customer;
@Entity
@Table(name = "COMPANY")
public class Company extends BaseEntity implements Serializable {

	 private static final long serialVersionUID = 3195389251580533159L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "COMPANY_ID")
	private Long companyId;

	@NotNull(message = "Mandatory")
	@NotBlank(message = "Mandatory")
	@Column(name = "TYPE", columnDefinition = "ENUM('Agency','Advertiser')")
	private String type;

	@NotNull(message = "Mandatory")
	@NotBlank(message = "Mandatory")
	@Pattern(regexp = "^(?=.*[a-zA-Z]).+$", message = "Name has invalid characters")
	@Column(name = "NAME")
	private String name;

	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "STATUS_ID")
	private CompanyStatus companyStatus;

	@Column(name = "address")
	private String address;
	
	@Column(name = "vpzid")
	private String vpzid;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CompanyStatus getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(CompanyStatus companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVpzid() {
		return vpzid;
	}

	public void setVpzid(String vpzid) {
		this.vpzid = vpzid;
	}
}
