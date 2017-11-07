package com.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "PRODUCT_TYPE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductType extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -1654578209598920557L;

	@Id
	@GeneratedValue
	@Column(name="TYPE_ID")
	private Long typeId;
	
	@Column(name="TYPE_NAME")
	private String typeName;
	

	@Column(name="SUBTYPE_NAME")
	private String subTypeName;
	
	
	@Column(name="DESCRIPTION")
	private String description;
	

	@Column(name = "STATUS", columnDefinition = "BIT") 
	private boolean status;

	public Long getTypeId() {
		return typeId;
	}


	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public String getSubTypeName() {
		return subTypeName;
	}


	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
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


}
