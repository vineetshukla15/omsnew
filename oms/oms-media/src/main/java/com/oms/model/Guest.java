package com.oms.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GUEST")
public class Guest implements Serializable {
	private static final long serialVersionUID = 1L;

	// Persistent Fields:
	@Id
	@GeneratedValue
	Long id;
	private String name;
	private Date signingDate;
	@OneToMany(mappedBy = "guest", fetch=FetchType.EAGER)
	private List<GuestAddress> addresses;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<GuestAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<GuestAddress> addresses) {
		this.addresses = addresses;
	}

	// Constructors:
	public Guest() {
	}

	public Guest(String name) {
		this.name = name;
		this.signingDate = new Date(System.currentTimeMillis());
	}
}