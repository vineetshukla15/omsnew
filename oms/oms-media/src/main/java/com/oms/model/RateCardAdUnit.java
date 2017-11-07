package com.oms.model;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * RateCardAdUnit generated by hbm2java
 */
@Entity
@Table(name = "RATE_CARD_AD_UNIT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RateCardAdUnit implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 857016878112677342L;
	private RateCardAdUnitId id;
	private ADUnit adUnit;
	private RateCard rateCard;

	public RateCardAdUnit() {
	}

	public RateCardAdUnit(RateCardAdUnitId id) {
		this.id = id;
	}

	public RateCardAdUnit(RateCardAdUnitId id, ADUnit adUnit,
			RateCard rateCard) {
		this.id = id;
		this.adUnit = adUnit;
		this.rateCard = rateCard;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "rateCardId", column = @Column(name = "RATE_CARD_ID")),
			@AttributeOverride(name = "adUnitId", column = @Column(name = "AD_UNIT_ID")) })
	public RateCardAdUnitId getId() {
		return this.id;
	}

	public void setId(RateCardAdUnitId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AD_UNIT_ID", insertable = false, updatable = false)
	public ADUnit getAdUnit() {
		return this.adUnit;
	}

	public void setAdUnit(ADUnit adUnit) {
		this.adUnit = adUnit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RATE_CARD_ID", insertable = false, updatable = false)
	public RateCard getRateCardProfile() {
		return this.rateCard;
	}

	public void setRateCardProfile(RateCard rateCard) {
		this.rateCard = rateCard;
	}

}
