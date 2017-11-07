package com.oms.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "proposal_version")
// @CheckDateRange(message = "Dates are invalid.")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProposalVersion implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEQ_NO")
	private Long seqNo;

	@JsonBackReference
	@NotNull
	@ManyToOne
	@JoinColumn(name = "PROPOSAL_ID", referencedColumnName = "PROPOSAL_ID")
	private Proposal proposal;


	@Column(name = "VERSION")
	private Long version;

	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "OPPORTUNITY_ID")
	private Opportunity opportunity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATUS_ID")
	private ProposalStatus status;

	@NotNull
	@Column(name = "START_DATE")
	private Timestamp startDate;

	@NotNull
	@Column(name = "END_DATE")
	private Timestamp endDate;

	@NotNull
	@Column(name = "DUE_DATE")
	private Timestamp dueDate;

	@Column(name = "CURRENCY", columnDefinition = "enum('USD','INR','EUR')")
	private String currency;

	@Column(name = "PRICING_MODEL", columnDefinition = "enum('Net','Gross')")
	private String pricingModel;

	@Column(name = "ADVERTISER_DISCOUNT")
	private Double advertiserDiscount;

	@Column(name = "BUDGET")
	private Double budget;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TERM_ID")
	private Terms terms;

	@NotNull
	@Column(name = "PERCENTAGE_OF_CLOSE")
	private int percentageOfClose;

	@ManyToOne
	@JoinColumn(name = "SALES_CATAGORY_ID")
	private SalesCategory salesCategory;

	@Column(name = "SUBMITTED")
	private boolean submitted;

	@Column(name = "NOTES")
	private String notes;

	@OneToMany(mappedBy = "versionId",  fetch = FetchType.LAZY)
	private List<ProposalAudit> audits;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "previous_status")
	private ProposalStatus  previousStatus;
	
	@Column(name = "status_desc")
	private String  proopsalStatusDesc;

	public Long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Opportunity getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(Opportunity opportunity) {
		this.opportunity = opportunity;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Timestamp getDueDate() {
		return dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPricingModel() {
		return pricingModel;
	}

	public void setPricingModel(String pricingModel) {
		this.pricingModel = pricingModel;
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

	public int getPercentageOfClose() {
		return percentageOfClose;
	}

	public void setPercentageOfClose(int percentageOfClose) {
		this.percentageOfClose = percentageOfClose;
	}

	public SalesCategory getSalesCategory() {
		return salesCategory;
	}

	public void setSalesCategory(SalesCategory salesCategory) {
		this.salesCategory = salesCategory;
	}

	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public ProposalStatus getStatus() {
		return status;
	}

	public void setStatus(ProposalStatus status) {
		this.status = status;
	}

	public Terms getTerms() {
		return terms;
	}

	public void setTerms(Terms terms) {
		this.terms = terms;
	}

	public List<ProposalAudit> getAudits() {
		return audits;
	}

	public void setAudits(List<ProposalAudit> audits) {
		this.audits = audits;
	}

	public ProposalStatus getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(ProposalStatus previousStatus) {
		this.previousStatus = previousStatus;
	}

	public String getProopsalStatusDesc() {
		return proopsalStatusDesc;
	}

	public void setProopsalStatusDesc(String proopsalStatusDesc) {
		this.proopsalStatusDesc = proopsalStatusDesc;
	}
	
}
