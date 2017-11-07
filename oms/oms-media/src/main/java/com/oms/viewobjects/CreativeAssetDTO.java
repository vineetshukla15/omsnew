package com.oms.viewobjects;

public class CreativeAssetDTO {
	private Long id;

	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String type;

	private String insertionpoint;

	private String clickDestinationUri;

	private String vastUri;

	private boolean vpaidStrict;

	private boolean vpaidCountdown;

	private String assetid;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
