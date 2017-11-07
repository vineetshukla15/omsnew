package com.oms.viewobjects.vpz;

import java.util.Date;

public class VPZCreative {
	
	private String type;
	private String insertionPoint;
	private String assetId;
	private String clickDestinationUri;
	private Date creationDate;
	private Date modificationDate;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInsertionPoint() {
		return insertionPoint;
	}

	public void setInsertionPoint(String insertionPoint) {
		this.insertionPoint = insertionPoint;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getClickDestinationUri() {
		return clickDestinationUri;
	}

	public void setClickDestinationUri(String clickDestinationUri) {
		this.clickDestinationUri = clickDestinationUri;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

}
