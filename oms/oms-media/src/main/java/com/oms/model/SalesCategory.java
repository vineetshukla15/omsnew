package com.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="SALES_CATAGORY")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesCategory extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SALES_CATAGORY_ID")
	private Long salesCatagoryId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="STATUS")
	private boolean status;
	
	public Long getSalesCatagoryId() {
		return salesCatagoryId;
	}

	public void setSalesCatagoryId(Long salesCatagoryId) {
		this.salesCatagoryId = salesCatagoryId;
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

	public SalesCategory() {
	}

	public SalesCategory(Long salesCatagoryId, String name, boolean status) {
		super();
		this.salesCatagoryId = salesCatagoryId;
		this.name = name;
		this.status = status;
	}
}
