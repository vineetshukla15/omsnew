package com.oms.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.tavant.api.auth.model.OMSUser;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "ordertab", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME" }) })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Order extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private Long orderId;	

	@OneToOne
	@JoinColumn(name = "PROPOSAL_ID")
	private Proposal proposal;

	@NotNull
	@NotBlank
	@Pattern(regexp = "^(?=.*[a-zA-Z]).+$", message = "Name has invalid characters")
	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "STATUS_ID")
	private ProposalStatus status;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")
	private Set<OrderLineItems> lineItems;

	@ManyToOne
	@JoinColumn(name = "AGENCY_ID", referencedColumnName = "COMPANY_ID")
	private Company agency;

	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "DUE_DATE")
	private Date dueDate;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "PRICING_MODEL")
	private String pricingModel;

	@ManyToOne
	@JoinColumn(name = "ADVERTISER_ID", referencedColumnName = "COMPANY_ID")
	private Company advertiser;

	@Column(name = "ADVERTISER_DISCOUNT")
	private Double advertiserDiscount;

	@Column(name = "BUDGET")
	private Double budget;
		
	@ManyToOne
	@JoinColumn(name = "SALES_PERSON_ID", referencedColumnName = "USER_ID")
	private OMSUser salesPerson;	

	@ManyToOne
	@JoinColumn(name = "assign_to", referencedColumnName = "USER_ID")
	private OMSUser assignTo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "TRAFFICKER_ID", referencedColumnName = "USER_ID")
	private OMSUser trafficker;

	@ManyToOne
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
	
	@Column(name = "proposal_discount")
	private Float proposalDiscount;

	@Column(name = "type", columnDefinition = "enum('Agency','Advertiser')")
	private String type;

	@Column(name = "planner_name")
	private String plannerName;

	@Transient
	private String action;

	@ManyToOne
	@JoinColumn(name = "MEDIA_PANNER_ID", referencedColumnName = "USER_ID")
	private OMSUser mediaPlanner;
	
	@Column(name = "AGENCY_MARGIN")
	private Double agencyMargin;

	@Column(name = "pushed")
	private boolean pushed;
	
	@Column(name = "vpz_campaign_id")
	private String vpzCampaignId;
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	
	public Set<OrderLineItems> getLineItems() {
		return lineItems;
	}

	public void setLineItems(Set<OrderLineItems> lineItems) {
		this.lineItems = lineItems;
	}

	public String getName() {
		return name;
	}

	public void setName(String orderName) {
		this.name = orderName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
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

	public Float getProposalDiscount() {
		return proposalDiscount;
	}

	public void setProposalDiscount(Float proposalDiscount) {
		this.proposalDiscount = proposalDiscount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlannerName() {
		return plannerName;
	}

	public void setPlannerName(String plannerName) {
		this.plannerName = plannerName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Double getAgencyMargin() {
		return agencyMargin;
	}

	public void setAgencyMargin(Double agencyMargin) {
		this.agencyMargin = agencyMargin;
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
	
	public boolean isPushed() {
		return pushed;
	}

	public void setPushed(boolean pushed) {
		this.pushed = pushed;
	}

	public String getVpzCampaignId() {
		return vpzCampaignId;
	}

	public void setVpzCampaignId(String vpzCampaignId) {
		this.vpzCampaignId = vpzCampaignId;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public ProposalStatus getStatus() {
		return status;
	}

	public void setStatus(ProposalStatus status) {
		this.status = status;
	}

	public OMSUser getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(OMSUser salesPerson) {
		this.salesPerson = salesPerson;
	}

	public OMSUser getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(OMSUser assignTo) {
		this.assignTo = assignTo;
	}

	public OMSUser getTrafficker() {
		return trafficker;
	}

	public void setTrafficker(OMSUser trafficker) {
		this.trafficker = trafficker;
	}

	public Terms getTerms() {
		return terms;
	}

	public void setTerms(Terms terms) {
		this.terms = terms;
	}

	public SalesCategory getSalesCategory() {
		return salesCategory;
	}

	public void setSalesCategory(SalesCategory salesCategory) {
		this.salesCategory = salesCategory;
	}

	public OMSUser getMediaPlanner() {
		return mediaPlanner;
	}

	public void setMediaPlanner(OMSUser mediaPlanner) {
		this.mediaPlanner = mediaPlanner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}	
}
