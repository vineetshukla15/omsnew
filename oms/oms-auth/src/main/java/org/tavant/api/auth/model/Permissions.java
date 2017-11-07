package org.tavant.api.auth.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "PERMISSION")
public class Permissions extends AuthBaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6094826071545593656L;

	@Id
	@GeneratedValue
	@Column(name = "PERMISSION_ID")
	private Long permissionId;

	@Column(name = "DISPLAY_NAME")
	private String permissionName;

	@Column(name = "VALUE")
	private String value;
	
	@NotBlank
	@Column(name = "permission_key", unique=true)
	private String key;
	
	  @JsonBackReference("highLevelPermission")
	  @ManyToOne
	  @JoinColumn(name="HIGH_LEVEL_PERMISSION_ID")
	  private HighLevelPermission highLevelPermission;

	@JsonBackReference("role")
	@ManyToMany(mappedBy = "permissions")
	private Collection<Role> role;
	
	public Collection<Role> getRole() {
		return role;
	}

	public void setRole(Collection<Role> role) {
		this.role = role;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public HighLevelPermission getHighLevelPermission() {
		return highLevelPermission;
	}

	public void setHighLevelPermission(HighLevelPermission highLevelPermission) {
		this.highLevelPermission = highLevelPermission;
	}


}
