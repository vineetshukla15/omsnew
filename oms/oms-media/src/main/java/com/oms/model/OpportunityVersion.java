package com.oms.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the OPPORTUNITY database table.
 * 
 */
@Entity
@Table(name = "OPPORTUNITY_VERSION")
// @CheckDateRange(message = "Dates are invalid.")
public class OpportunityVersion extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEQ_NO")
	private Long seqNo;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "OPPORTUNITY_ID", referencedColumnName = "OPPORTUNITY_ID")
	private Opportunity opportunity;

	@Column(name = "ADVERTISER_DISCOUNT")
	private Double advertiserDiscount;

	@Column(name = "BUDGET")
	private Double budget;

	@Column(name = "CURRENCY", columnDefinition = "enum('USD','INR','EUR')")
	private String currency;

	@Column(name = "DESCRIPTION")
	private String description;

	@NotNull
	@Column(name = "DUE_DATE")
	private Timestamp dueDate;

	@NotNull
	@Column(name = "END_DATE")
	private Timestamp endDate;

	@NotNull
	@NotBlank
	@Pattern(regexp = "^(?=.*[a-zA-Z]).+$", message = "Name has invalid characters")
	@Column(name = "NAME")
	private String name;

	@Column(name = "NOTES")
	private String notes;

	@NotNull
	@Column(name = "PERCENTAGE_OF_CLOSE")
	private int percentageOfClose;

	@Column(name = "PRICING_MODEL", columnDefinition = "enum('Net','Gross')")
	private String pricingModel;

	@ManyToOne
	@JoinColumn(name = "SALES_CATAGORY_ID")
	private SalesCategory salesCategory;

	@NotNull
	@Column(name = "START_DATE")
	private Timestamp startDate;

	@ManyToOne
	@JoinColumn(name = "TERM_ID")
	private Terms terms;

	@Column(name = "VERSION")
	private Long version;

	@Column(name = "SUBMITTED")
	private boolean submitted;

	public Long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}

	public Opportunity getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(Opportunity opportunity) {
		this.opportunity = opportunity;
	}

	public Double getAdvertiserDiscount() {
		return advertiserDiscount;
	}

	public void setAdvertiserDiscount(Double advertiserDiscount) {
		this.advertiserDiscount = advertiserDiscount;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getDueDate() {
		return dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getPercentageOfClose() {
		return percentageOfClose;
	}

	public void setPercentageOfClose(int percentageOfClose) {
		this.percentageOfClose = percentageOfClose;
	}

	public String getPricingModel() {
		return pricingModel;
	}

	public void setPricingModel(String pricingModel) {
		this.pricingModel = pricingModel;
	}

	public SalesCategory getSalesCategory() {
		return salesCategory;
	}

	public void setSalesCategory(SalesCategory salesCatagoryId) {
		this.salesCategory = salesCatagoryId;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public Terms getTerms() {
		return terms;
	}

	public void setTerms(Terms terms) {
		this.terms = terms;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}