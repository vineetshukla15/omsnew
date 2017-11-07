package com.oms.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "SPEC_TYPE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecType extends BaseEntity implements Serializable {

	
	private static final long serialVersionUID = 5317850158614098878L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="SPEC_ID",  unique = true, nullable = false)
	private Long spec_Id;
	
	@Column(name="NAME")
	private String name;

	public Long getSpec_Id() {
		return spec_Id;
	}

	public void setSpec_Id(Long spec_Id) {
		this.spec_Id = spec_Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SpecType() {
		super();
	}

	public SpecType(Long spec_Id, String name) {
		super();
		this.spec_Id = spec_Id;
		this.name = name;
	}
	
	

}
