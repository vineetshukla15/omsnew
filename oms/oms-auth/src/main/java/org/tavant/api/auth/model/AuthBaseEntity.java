package org.tavant.api.auth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class AuthBaseEntity {

	private Long createdBy;
	private Date created;
	private Long updatedBy;
	private Date updated;
	private boolean deleted=false;
	
	@JsonIgnore
	@Column(name="deleted", columnDefinition = "BIT")
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	@JsonIgnore
	@Column(name = "created_by")
	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", length = 19)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	@JsonIgnore
	@Column(name = "updated_by")
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

/*	private User getUserProfileFromSecurityContext() {
		 User user =null;
	        try {
	            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	            if (null != authentication && authentication.getPrincipal() instanceof User) {
	            	user =  (User)authentication.getPrincipal();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return user;
	    }*/

}
