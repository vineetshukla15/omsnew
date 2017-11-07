package org.tavant.api.auth.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Role class is an entity model object. A Role describes a privilege level
 * within the application. A Role is used to authorize an Account to access a
 * set of application resources.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class Role extends AuthBaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2983737605320687052L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
	private Long roleId;

	@NotNull(message="Mandatory")
	@NotBlank(message="Mandatory")
	@Pattern(regexp = "^[A-Za-z ]*$", message = "Name has invalid characters")
	@Column(name = "NAME")
	private String roleName;

	@Column(name = "DESCRIPTION")
	private String roleDesc;
	
	
	@Column(name="ACTIVE", columnDefinition = "BIT")
	private boolean active;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ROLE_PERMISSION", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "PERMISSION_ID") })
	private Collection<Permissions> permissions;
	
	

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// Constructors:
	public Role() {

	}

	public Role(String roleName, String roleDesc) {

		this.roleName = roleName;
		this.roleDesc = roleDesc;
	}


	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + ", roleDesc=" + roleDesc + ", active=" + active
				+ ", permissions=" + permissions + "]";
	}

	public Collection<Permissions> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<Permissions> permissions) {
		this.permissions = permissions;
	}

	public Role(Long roleId, String roleName, String roleDesc) { 
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		
	}

	public Role(Long roleId, String roleName, String roleDesc, List<Permissions> permissions) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.permissions = permissions;
	}

	
	

}
