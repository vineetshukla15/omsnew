package com.oms.model;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class BaseEntity {
	@JsonIgnore
	@Access(AccessType.PROPERTY)
	@Column(name = "created_by", updatable = false)
	private Long createdBy;
	@Column(name = "created", updatable = false)
	private Date created;

	@JsonIgnore
	@Access(AccessType.PROPERTY)
	@Column(name = "updated_by")
	private Long updatedBy;
	private Date updated;
	private boolean deleted = false;

	@JsonIgnore
	@Column(name = "deleted", columnDefinition = "BIT")
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", length = 19)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated", length = 19)
	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@PrePersist
	public void beforeCreate() {
		Date date = new Date();
		this.created = date;
		this.updated = date;
	}

	@PreUpdate
	public void beforeUpdate() {
		this.updated = new Date();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (deleted != other.deleted)
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		return true;
	}

	/*
	 * @Transient private OMSUser findUserProfileFromSecurityContext() { String
	 * user =null; try { Authentication authentication =
	 * SecurityContextHolder.getContext().getAuthentication(); if (null !=
	 * authentication) { user = (String)authentication.getPrincipal();
	 * 
	 * } } catch (Exception e) { e.printStackTrace();
	 * 
	 * } return userService.findByUserName(user);
	 * 
	 * }
	 */
	
}
