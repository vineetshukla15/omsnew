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
@Table(name = "CREATIVE")
public class Creative extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1818281004072598544L;
	// Persistent Fields:
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "creative_id")
	Long creativeId;
	
	@NotNull(message="Mandatory")
	@NotBlank(message="Mandatory")
	@Pattern(regexp="[A-Za-z_ ]*", message="Name has invalid characters") 
	@Column(name = "name", unique = true)
	private String name;
	
	@NotNull(message="Mandatory")
	@Column(name = "width_1")
	private Double width1;
	
	@Column(name = "width_2")
	private Double width2;
	
	@NotNull(message="Mandatory")
	@Column(name = "height_1")
	private Double height1;
	
	@Column(name = "height_2")
	private Double height2;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	public Long getCreativeId() {
		return creativeId;
	}

	public void setCreativeId(Long creativeId) {
		this.creativeId = creativeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getWidth1() {
		return width1;
	}

	public void setWidth1(Double width1) {
		this.width1 = width1;
	}

	public Double getWidth2() {
		return width2;
	}

	public void setWidth2(Double width2) {
		this.width2 = width2;
	}

	public Double getHeight1() {
		return height1;
	}

	public void setHeight1(Double height1) {
		this.height1 = height1;
	}

	public Double getHeight2() {
		return height2;
	}

	public void setHeight2(Double height2) {
		this.height2 = height2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}