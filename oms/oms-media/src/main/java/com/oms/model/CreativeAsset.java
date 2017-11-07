package com.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "creative_asset")
public class CreativeAsset  extends BaseEntity implements Serializable {
	@Id
	@GeneratedValue
	@Column(name="creative_id")
	private Long id;
	
	
	@Column(name = "name")	
	private String name;
	
	@Column(name = "type")	
	private String type;
	
	@Column(name = "insertionpoint")	
	private String insertionpoint;
	
	@Column(name = "click_destination_uri")	 
	private String clickDestinationUri;
	
	@Column(name = "vast_uri")	
	private String vastUri;
	
	
	@Column(name = "vpaid_strict")	
	private boolean vpaidStrict;
	
	@Column(name = "vpaid_countdown")	
	private boolean vpaidCountdown;
	
	@Column(name = "assetid")	
	private String assetid;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInsertionpoint() {
		return insertionpoint;
	}

	public void setInsertionpoint(String insertionpoint) {
		this.insertionpoint = insertionpoint;
	}

	public String getClickDestinationUri() {
		return clickDestinationUri;
	}

	public void setClickDestinationUri(String clickDestinationUri) {
		this.clickDestinationUri = clickDestinationUri;
	}

	public String getVastUri() {
		return vastUri;
	}

	public void setVastUri(String vastUri) {
		this.vastUri = vastUri;
	}

	public boolean isVpaidStrict() {
		return vpaidStrict;
	}

	public void setVpaidStrict(boolean vpaidStrict) {
		this.vpaidStrict = vpaidStrict;
	}

	public boolean isVpaidCountdown() {
		return vpaidCountdown;
	}

	public void setVpaidCountdown(boolean vpaidCountdown) {
		this.vpaidCountdown = vpaidCountdown;
	}

	public String getAssetid() {
		return assetid;
	}

	public void setAssetid(String assetid) {
		this.assetid = assetid;
	}
	
	
	
	
}
