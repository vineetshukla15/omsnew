package com.oms.viewobjects;

import java.math.BigDecimal;
import java.util.Set;

import com.oms.model.ADUnit;
import com.oms.model.Premium;
import com.oms.model.Product;
import com.oms.model.RateCard;
import com.oms.model.SeasonalDiscount;

public class RateCardVO {
	private Long rateCardId;
	private BigDecimal basePrice;
	private String sectionsName;
	private String notes;
	private boolean isActive = false;
	private boolean isRatecardrounded;
	private RateCardProductVO product;
	private Set<ADUnit> adUnits;
	private Set<Premium> premiums;
	private Set<SeasonalDiscount> seasonalDiscounts;

	public RateCardVO() {
		super();
	}

	public RateCardVO(RateCard rateCard) {
		this.rateCardId = rateCard.getRateCardId();
		this.basePrice = rateCard.getBasePrice();
		this.sectionsName = rateCard.getSectionsName();
		this.notes = rateCard.getNotes();
		this.isActive = rateCard.isActive();
		this.isRatecardrounded = rateCard.isRatecardrounded();
		this.adUnits = rateCard.getAdUnits();
		this.premiums = rateCard.getPremiums();
		this.seasonalDiscounts = rateCard.getSeasonalDiscounts();
		this.product = new RateCardProductVO(rateCard.getProduct());
	}

	public Long getRateCardId() {
		return rateCardId;
	}

	public void setRateCardId(Long rateCardId) {
		this.rateCardId = rateCardId;
	}

	public BigDecimal getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}

	public String getSectionsName() {
		return sectionsName;
	}

	public void setSectionsName(String sectionsName) {
		this.sectionsName = sectionsName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isRatecardrounded() {
		return isRatecardrounded;
	}

	public void setRatecardrounded(boolean isRatecardrounded) {
		this.isRatecardrounded = isRatecardrounded;
	}

	public RateCardProductVO getProduct() {
		return product;
	}

	public void setProduct(RateCardProductVO product) {
		this.product = product;
	}

	public Set<ADUnit> getAdUnits() {
		return adUnits;
	}

	public void setAdUnits(Set<ADUnit> adUnits) {
		this.adUnits = adUnits;
	}

	public Set<Premium> getPremiums() {
		return premiums;
	}

	public void setPremiums(Set<Premium> premiums) {
		this.premiums = premiums;
	}

	public Set<SeasonalDiscount> getSeasonalDiscounts() {
		return seasonalDiscounts;
	}

	public void setSeasonalDiscounts(Set<SeasonalDiscount> seasonalDiscounts) {
		this.seasonalDiscounts = seasonalDiscounts;
	}

	public static class RateCardProductVO {
		private Long productId;
		private String name;

		public RateCardProductVO() {
			super();
		}

		public RateCardProductVO(Long productId, String name) {
			super();
			this.productId = productId;
			this.name = name;
		}

		public RateCardProductVO(Product product) {
			this.productId = product.getProductId();
			this.name = product.getName();
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
