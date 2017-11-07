package com.oms.model;

public enum PricingModel {
	GROSS("Gross"), NET("Net");

	private String model;

	private PricingModel(String model) {
		this.model = model;
	}

	public String getPricingModel() {
		return model;
	}

}

