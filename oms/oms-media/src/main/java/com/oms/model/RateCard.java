package com.oms.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "RATE_CARD")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RateCard extends BaseEntity implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 3657738576065208498L;
	@Id
	@GeneratedValue
	@Column(name = "RATE_CARD_ID")
	private Long rateCardId;

	@NotNull(message="Mandatory")
	@Column(name = "BASE_PRICE")
	private BigDecimal basePrice;
	
	@Column(name = "SECTIONS_NAME")
	private String sectionsName;
	
	@Column(name = "NOTES")
	private String notes;

	@Column(name = "IS_ACTIVE", columnDefinition = "BIT")
	private boolean isActive = false;

	@Column(name = "IS_RATECARDROUNDED", columnDefinition = "BIT")
	private boolean isRatecardrounded;

	@NotNull(message="Mandatory")
	@OneToOne(fetch=FetchType.EAGER)
	@JsonBackReference
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "RATE_CARD_AD_UNIT", joinColumns = @JoinColumn(name = "RATE_CARD_ID"), inverseJoinColumns = @JoinColumn(name = "AD_UNIT_ID"))
	private Set<ADUnit> adUnits;
	
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name="RATE_CARD_ID")
	private Set<Premium> premiums;
	
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name="RATE_CARD_ID")
	private Set<SeasonalDiscount> seasonalDiscounts;
	
	/**
	 * @return the rateCardId
	 */
	public Long getRateCardId() {
		return rateCardId;
	}

	/**
	 * @param rateCardId
	 *            the rateCardId to set
	 */
	public void setRateCardId(Long rateCardId) {
		this.rateCardId = rateCardId;
	}

	/**
	 * @return the basePrice
	 */
	public BigDecimal getBasePrice() {
		return basePrice;
	}

	/**
	 * @param basePrice
	 *            the basePrice to set
	 */
	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}

	/**
	 * @return the sectionsName
	 */
	public String getSectionsName() {
		return sectionsName;
	}

	/**
	 * @param sectionsName
	 *            the sectionsName to set
	 */
	public void setSectionsName(String sectionsName) {
		this.sectionsName = sectionsName;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 *            the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the isRatecardrounded
	 */
	public boolean isRatecardrounded() {
		return isRatecardrounded;
	}

	/**
	 * @param isRatecardrounded
	 *            the isRatecardrounded to set
	 */
	public void setRatecardrounded(boolean isRatecardrounded) {
		this.isRatecardrounded = isRatecardrounded;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	/**
	 * @return the adUnits
	 */
	public Set<ADUnit> getAdUnits() {
		return adUnits;
	}

	/**
	 * @param adUnits
	 *            the adUnits to set
	 */
	public void setAdUnits(Set<ADUnit> adUnits) {
		this.adUnits = adUnits;
	}
	
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<SeasonalDiscount> getSeasonalDiscounts() {
		return seasonalDiscounts;
	}

	public void setSeasonalDiscounts(Set<SeasonalDiscount> seasonalDiscounts) {
		this.seasonalDiscounts = seasonalDiscounts;
	}

	public Set<Premium> getPremiums() {
		return premiums;
	}

	public void setPremiums(Set<Premium> premiums) {
		this.premiums = premiums;
	}

	
	
	
}
