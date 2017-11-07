package com.oms.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.tavant.api.auth.model.Customer;
import org.tavant.api.auth.model.OMSUser;

@Entity
@Table(name = "newcreative")
public class NewCreative extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1818281004072598544L;
	// Persistent Fields:
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "creativeid")
	Long newCreativeId;
	
	@NotNull(message="Mandatory")
	@NotBlank(message="Mandatory")
	@Pattern(regexp="[A-Za-z_ ]*", message="Creative Name has invalid characters") 
	@Column(name = "creative_name")
	private String name;
	
	@Column(name = "creative_description")
	private String creativeDescription;	
	
	@Column(name = "deviceid")
	private int deviceID;	
	

	@Column(name = "custom_ad_id")
	private Double customeAdID;	
		
	
	
	@Column(name = "creative_uploadlocation")
	private String creativeUploadLocation;
	
	@Column(name = "creative_uploadcaption")
	private String creativeUploadCaption;
	
	
	@Column(name = "creative_uploadsource")
	private String creativeUploadSource;
	
	@Column(name = "creative_source_url")
	private String creativeSourceURL;
	
	@Column(name = "creative_destination_url")
	private String creativeDestinationURL;
	
	
	public String geteName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreativeDescription() {
		return creativeDescription;
	}

	public void setCreativeDescription(String creativeDescription) {
		this.creativeDescription = creativeDescription;
	}

	public int getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}

	public Double getCustomeAdID() {
		return customeAdID;
	}

	public void setCustomeAdID(Double customeAdID) {
		this.customeAdID = customeAdID;
	}

	public String getCreativeUploadLocation() {
		return creativeUploadLocation;
	}

	public void setCreativeUploadLocation(String creativeUploadLocation) {
		this.creativeUploadLocation = creativeUploadLocation;
	}

	public String getCreativeUploadCaption() {
		return creativeUploadCaption;
	}

	public void setCreativeUploadCaption(String creativeUploadCaption) {
		this.creativeUploadCaption = creativeUploadCaption;
	}

	public String getCreativeUploadSource() {
		return creativeUploadSource;
	}

	public void setCreativeUploadSource(String creativeUploadSource) {
		this.creativeUploadSource = creativeUploadSource;
	}

	public String getCreativeSourceURL() {
		return creativeSourceURL;
	}

	public void setCreativeSourceURL(String creativeSourceURL) {
		this.creativeSourceURL = creativeSourceURL;
	}

	public String getCreativeDestinationURL() {
		return creativeDestinationURL;
	}

	public void setCreativeDestinationURL(String creativeDestinationURL) {
		this.creativeDestinationURL = creativeDestinationURL;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((newCreativeId == null) ? 0 : newCreativeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewCreative other = (NewCreative) obj;
		if (newCreativeId == null) {
			if (other.newCreativeId != null)
				return false;
		} else if (!newCreativeId.equals(other.newCreativeId))
			return false;
		return true;
	}

	public Long getNewCreativeId() {
		return newCreativeId;
	}

	public void setNewCreativeId(Long newCreativeId) {
		this.newCreativeId = newCreativeId;
	}
	
}