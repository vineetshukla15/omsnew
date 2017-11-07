package com.oms.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oms.settings.validators.CheckDateRange;

/**
 * 
 * @author vikas.parashar
 *
 */
@Entity
@Table(name = "SEASONAL_DISCOUNT")
@CheckDateRange(message = "Dates are invalid.")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeasonalDiscount extends BaseEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6646302171668446848L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE", length = 19)
	private Date startDate;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE", length = 19)
	private Date endDate;
	@Column(name = "DISCOUNT", precision = 10, scale = 0)
	private Double discount;

	@Column(name = "IS_NOT")
	private Integer isNot;
	
	@Column(name = "RULE_NAME")
	private String ruleName;
	
	/*@JsonBackReference
	@ManyToOne
	@JoinColumn(name="RATE_CARD_ID")
	RateCard rateCardProfile;*/

	public SeasonalDiscount() {
	}

	public SeasonalDiscount(Date startDate, Date endDate, Double discount, Integer isNot) {

		this.startDate = startDate;
		this.endDate = endDate;
		this.discount = discount;
		this.isNot = isNot;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getDiscount() {
		return this.discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Integer getIsNot() {
		return this.isNot;
	}

	public void setIsNot(Integer isNot) {
		this.isNot = isNot;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	

	
}
