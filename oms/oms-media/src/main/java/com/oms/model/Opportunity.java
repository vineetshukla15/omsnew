package com.oms.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.tavant.api.auth.model.OMSUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "OPPORTUNITY")
@JsonIgnoreProperties(ignoreUnknown = true)
// @CheckDateRange(message = "Dates are invalid.")
public class Opportunity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OPPORTUNITY_ID")
	private Long opportunityId;

	@Column(name = "ADVERTISER_DISCOUNT")
	private Double advertiserDiscount;

	@ManyToOne
	@JoinColumn(name = "ADVERTISER_ID", referencedColumnName = "COMPANY_ID")
	private Company advertiser;

	@ManyToOne
	@JoinColumn(name = "AGENCY_ID", referencedColumnName = "COMPANY_ID")
	private Company agency;

	@ManyToOne
	@JoinColumn(name = "BILLING_SOURCE_ID")
	private BillingSource billingSource;

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
	@ManyToOne
	@JoinColumn(name = "SALES_PERSON_ID", referencedColumnName = "USER_ID")
	private OMSUser salesPerson;

	@NotNull
	@Column(name = "START_DATE")
	private Timestamp startDate;

	@ManyToOne
	@JoinColumn(name = "TERM_ID")
	private Terms terms;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "TRAFFICKER_ID", referencedColumnName = "USER_ID")
	private OMSUser trafficker;

	@Column(name = "SUBMITTED")
	private boolean submitted;

	@OneToMany(mappedBy = "opportunity", cascade = javax.persistence.CascadeType.ALL)
	@OrderBy("version DESC")
	private Set<OpportunityVersion> versions;

	@OneToOne
	@JoinColumn(name = "ASSIGNED_BY", referencedColumnName = "USER_ID")
	private OMSUser assignedBy;

	@OneToMany(mappedBy = "opportunity", fetch = FetchType.LAZY)
	private List<OpportunityDocument> documents;

	@JsonIgnore
	@OneToOne(mappedBy = "opportunity", fetch = FetchType.LAZY)
	private Proposal proposal;

	@ManyToOne
	@JoinColumn(name = "MEDIA_PANNER_ID", referencedColumnName = "USER_ID")
	private OMSUser mediaPlanner;

	public Long getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(Long opportunityId) {
		this.opportunityId = opportunityId;
	}

	public Double getAdvertiserDiscount() {
		return advertiserDiscount;
	}

	public void setAdvertiserDiscount(Double advertiserDiscount) {
		this.advertiserDiscount = advertiserDiscount;
	}

	public Company getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Company advertiser) {
		this.advertiser = advertiser;
	}

	public Company getAgency() {
		return agency;
	}

	public void setAgency(Company agency) {
		this.agency = agency;
	}

	public BillingSource getBillingSource() {
		return billingSource;
	}

	public void setBillingSource(BillingSource opportunityBillingSource) {
		this.billingSource = opportunityBillingSource;
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

	public OMSUser getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(OMSUser salesPerson) {
		this.salesPerson = salesPerson;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Terms getTerms() {
		return terms;
	}

	public void setTerms(Terms opportunityTerms) {
		this.terms = opportunityTerms;
	}

	public OMSUser getTrafficker() {
		return trafficker;
	}

	public void setTrafficker(OMSUser trafficker) {
		this.trafficker = trafficker;
	}

	public SalesCategory getSalesCategory() {
		return salesCategory;
	}

	public void setSalesCategory(SalesCategory salesCategory) {
		this.salesCategory = salesCategory;
	}

	public Set<OpportunityVersion> getVersions() {
		return versions;
	}

	public void setVersions(Set<OpportunityVersion> opportunityVersions) {
		this.versions = opportunityVersions;
	}

	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public OMSUser getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(OMSUser assignedBy) {
		this.assignedBy = assignedBy;
	}

	public List<OpportunityDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<OpportunityDocument> documents) {
		this.documents = documents;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public OMSUser getMediaPlanner() {
		return mediaPlanner;
	}

	public void setMediaPlanner(OMSUser opportunityMediaPlanner) {
		this.mediaPlanner = opportunityMediaPlanner;
	}

}