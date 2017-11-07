package org.tavant.api.auth.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The OMSUser class is an entity model object. An OMSUser describes the
 * security credentials and authentication flags that permit access to
 * application functionality.
 */
@Entity
@Table(name = "USER")
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OMSUser extends AuthBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;

	@NotNull
	@UniqueUsername(message = "User with Login Name already exists")
	@Size(min = 4, max = 255, message = "Login name have to be greater than 4 characters")
	@Column(unique = true, name = "LOGIN_NAME")
	private String username;

	@Size(min = 8, max = 255, message = "Password have to be greater than 8 characters")
	private String password;

	@NotNull
	@Column(name = "ACTIVE")
	private boolean enabled = true;

	@NotNull
	private boolean credentialsexpired = false;

	@NotNull
	private boolean expired = false;

	@NotNull
	private boolean locked = false;
	
	@Pattern(regexp = "^[A-Za-z ]*$", message = "Name has invalid characters")
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Pattern(regexp = "^[A-Za-z ]*$", message = "Name has invalid characters")
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Email
	@Column(name = "EMAIL_ID")
	private String emailId;
	
	@Column(name = "ADDRESS_1")
	private String address1;
	
	@Column(name = "ADDRESS_2")
	private String address2;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "ZIP")
	private String zip;
	
	@Column(name = "TELEPHONE")
	private String telelphoneNo;
	
	@Column(name = "MOBILE_NO")
	private String mobileNo;
	
	@Column(name = "FAX")
	private String faxNo;
	
	@Column(columnDefinition = "ENUM('internal','register','social')")
	private String source;
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ROLE_ID")
	private Role role;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isCredentialsexpired() {
		return credentialsexpired;
	}

	public void setCredentialsexpired(boolean credentialsexpired) {
		this.credentialsexpired = credentialsexpired;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getTelelphoneNo() {
		return telelphoneNo;
	}

	public void setTelelphoneNo(String telelphoneNo) {
		this.telelphoneNo = telelphoneNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public OMSUser(OMSUser user) {
		super();
		this.id = user.id;
		this.username = user.username;
		this.password = user.password;
		this.enabled = user.enabled;
		this.credentialsexpired = user.credentialsexpired;
		this.expired = user.expired;
		this.locked = user.locked;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.emailId = user.emailId;
		this.address1 = user.address1;
		this.address2 = user.address2;
		this.city = user.city;
		this.state = user.state;
		this.country = user.country;
		this.zip = user.zip;
		this.telelphoneNo = user.telelphoneNo;
		this.mobileNo = user.mobileNo;
		this.faxNo = user.faxNo;
		this.source = user.source;
		this.role = user.role;
	}

	public OMSUser() {
	}

	public OMSUser(Long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return id +"";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OMSUser other = (OMSUser) obj;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
