package org.tavant.api.auth.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "HIGH_LEVEL_PERMISSION")
public class HighLevelPermission extends AuthBaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8354269103244102294L;

	@Id
	@GeneratedValue
	@Column(name = "PERMISSION_COMPONENT_ID")
	private Long permissionComponentId;
	
	@Column(name = "PERMISSION_NAME")
	private String permissionName;
	
	@Column(name = "PERMISSION_DESCRIPTION")
	private String permissionDesc;
	
	@OneToMany(mappedBy="highLevelPermission",fetch = FetchType.EAGER)
	private List<Permissions> permissions;

	public List<Permissions> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permissions> permissions) {
		this.permissions = permissions;
	}

	public HighLevelPermission() {
		super();
	}

	
	public HighLevelPermission(Long permissionComponentId, String permissionName, String permissionDesc,
			List<Permissions> permissions) {
		super();
		this.permissionComponentId = permissionComponentId;
		this.permissionName = permissionName;
		this.permissionDesc = permissionDesc;
		this.permissions = permissions;
	}

	public Long getPermissionComponentId() {
		return permissionComponentId;
	}

	public void setPermissionComponentId(Long permissionComponentId) {
		this.permissionComponentId = permissionComponentId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionDesc() {
		return permissionDesc;
	}

	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}

}
