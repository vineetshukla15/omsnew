package com.oms.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
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
import javax.persistence.OrderBy;
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
@Table(name = "proposal", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME" }) })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Proposal extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROPOSAL_ID")
	private Long proposalId;

	@NotNull
	@NotBlank
	@Pattern(regexp = "^(?=.*[a-zA-Z]).+$", message = "Name has invalid characters")
	@Column(name = "NAME")

	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@OneToOne
	@JoinColumn(name = "OPPORTUNITY_ID")
	private Opportunity opportunity;

	@ManyToOne
	@JoinColumn(name = "STATUS_ID")
	private ProposalStatus status;

	@ManyToOne
	@JoinColumn(name = "AGENCY_ID", referencedColumnName = "COMPANY_ID")
	private Company agency;

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

	@ManyToOne
	@JoinColumn(name = "ADVERTISER_ID", referencedColumnName = "COMPANY_ID")
	private Company advertiser;

	@Column(name = "ADVERTISER_DISCOUNT")
	private Double advertiserDiscount;

	@Column(name = "BUDGET")
	private Double budget;

	@NotNull
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
	@JoinColumn(name = "BILLING_SOURCE_ID")
	private BillingSource billingSource;

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

	@OneToMany(mappedBy = "proposal", cascade = CascadeType.ALL)
	@OrderBy("version ASC")
	private Set<ProposalVersion> versions;

	@OneToMany(mappedBy = "proposalId", cascade = CascadeType.ALL)
	private List<ProposalAudit> audits;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "PROPOSAL_ID", referencedColumnName = "PROPOSAL_ID")
	private Set<LineItems> lineItems;
	@Transient
	private BigDecimal proposalCost;

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

	@OneToMany(mappedBy = "proposal", fetch = FetchType.LAZY)
	private Collection<ProposalDocument> documents;

	@Column(name = "AGENCY_MARGIN")
	private Double agencyMargin;

	public Long getProposalId() {
		return proposalId;
	}

	public void setProposalId(Long proposalId) {
		this.proposalId = proposalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<ProposalAudit> getAudits() {
		return audits;
	}

	public void setAudits(List<ProposalAudit> proposalAudits) {
		this.audits = proposalAudits;
	}

	public ProposalStatus getStatus() {
		return status;
	}

	public void setStatus(ProposalStatus status) {
		this.status = status;
	}

	public Set<LineItems> getLineItems() {
		return lineItems;
	}

	public void setLineItems(Set<LineItems> lineItems) {
		this.lineItems = lineItems;
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

	public BigDecimal getProposalCost() {
		BigDecimal proposalcost = BigDecimal.ZERO;
		String pricingModel=this.pricingModel;
		Set<LineItems> lineitems = getLineItems();
		if (lineitems != null && !lineitems.isEmpty()){
			for(LineItems li: lineitems){
				if (li.getProposedCost() != null) {
					BigDecimal cost = li.getProposedCost();
					if (pricingModel != null && pricingModel.equalsIgnoreCase(PricingModel.GROSS.name())
							&& this.agencyMargin != null) {
						cost = applyAgencyMargin(this.agencyMargin, cost);
					}
					if (li.getProduct().getRateType() != null)
						cost = applyPricingType(li.getProduct().getRateType().getName(), cost, li.getQuantity());
					proposalcost = proposalcost.add(cost);
				}
			}
		}
		if (proposalcost.compareTo(BigDecimal.ZERO) > 0) {
			if (advertiserDiscount != null && advertiserDiscount > 0) {
				proposalcost = applyAdvertiserDiscount(proposalcost, getAdvertiserDiscount());
			}
			if (proposalDiscount != null && proposalDiscount > 0) {
				proposalcost = applyProposalDiscount(proposalcost, getProposalDiscount());
			}
		}
		return proposalcost;
	}

	public Float getProposalDiscount() {
		if (this.proposalDiscount == null) {
			proposalDiscount = 0f;
		}
		return proposalDiscount;
	}

	public void setProposalDiscount(Float proposalDiscount) {
		this.proposalDiscount = proposalDiscount;
	}

	public void setProposalCost(BigDecimal proposalCost) {
		if (proposalCost == null) {
			proposalCost = BigDecimal.ZERO;
		}
		this.proposalCost = proposalCost;
	}

	
	private BigDecimal applyAgencyMargin(Double agencyMargin, BigDecimal cost) {
		BigDecimal divisor = BigDecimal.valueOf(100);
		BigDecimal discount = new BigDecimal(agencyMargin);
		BigDecimal nowActualCost = cost;
		if (agencyMargin > 0 && cost.compareTo(BigDecimal.ZERO) > 0) {
			nowActualCost = cost.add((cost.multiply(discount)).divide(divisor));
		}
		return nowActualCost;
	}

	private BigDecimal applyPricingType(String name, BigDecimal cost, Long noofImpression) {
		BigDecimal divisor = BigDecimal.valueOf(10000);
		if (noofImpression != null) {
			BigDecimal impression = BigDecimal.valueOf(noofImpression);
			switch (name) {
			case "CPM":
				cost = cost.multiply(impression).divide(divisor);
				break;

			case "CPC":
				cost = cost.multiply(impression);
				break;

			case "CPV":
				cost = cost.multiply(impression);
				break;

			case "CPI":
				cost = cost.multiply(impression);
				break;

			case "CPL":
				cost = cost.multiply(impression);
				break;

			case "CPD":
				cost = cost.multiply(impression);
				break;

			}
		}
		return cost;
	}

	private BigDecimal applyProposalDiscount(BigDecimal proposalCost, Float propsalDiscount) {
		BigDecimal divisor = BigDecimal.valueOf(100);
		BigDecimal discount = new BigDecimal(propsalDiscount);
		BigDecimal nowActualCost = proposalCost;
		if (propsalDiscount > 0 && proposalCost.compareTo(BigDecimal.ZERO) > 0) {
			nowActualCost = proposalCost.subtract((proposalCost.multiply(discount)).divide(divisor));
		}
		return nowActualCost;
	}

	private BigDecimal applyAdvertiserDiscount(BigDecimal proposalCost, Double avertiserDiscount) {
		BigDecimal divisor = BigDecimal.valueOf(100);
		BigDecimal discount = new BigDecimal(avertiserDiscount);
		BigDecimal nowActualCost = proposalCost;
		if (avertiserDiscount > 0 && proposalCost.compareTo(BigDecimal.ZERO) > 0) {
			nowActualCost = proposalCost.subtract((proposalCost.multiply(discount)).divide(divisor));
		}
		return nowActualCost;
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

	public Company getAgency() {
		return agency;
	}

	public void setAgency(Company agency) {
		this.agency = agency;
	}

	public Company getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Company advertiser) {
		this.advertiser = advertiser;
	}

	public OMSUser getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(OMSUser salesPerson) {
		this.salesPerson = salesPerson;
	}

	public OMSUser getTrafficker() {
		return trafficker;
	}

	public void setTrafficker(OMSUser trafficker) {
		this.trafficker = trafficker;
	}

	public BillingSource getBillingSource() {
		return billingSource;
	}

	public void setBillingSource(BillingSource billingSource) {
		this.billingSource = billingSource;
	}

	public Terms getTerms() {
		return terms;
	}

	public void setTerms(Terms terms) {
		this.terms = terms;
	}

	public Set<ProposalVersion> getVersions() {
		return versions;
	}

	public void setVersions(Set<ProposalVersion> versions) {
		this.versions = versions;
	}

	public OMSUser getMediaPlanner() {
		return mediaPlanner;
	}

	public void setMediaPlanner(OMSUser mediaPlanner) {
		this.mediaPlanner = mediaPlanner;
	}

	public Collection<ProposalDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(Collection<ProposalDocument> documents) {
		this.documents = documents;
	}

	public OMSUser getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(OMSUser assignTo) {
		this.assignTo = assignTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((proposalId == null) ? 0 : proposalId.hashCode());
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
		Proposal other = (Proposal) obj;
		if (proposalId == null) {
			if (other.proposalId != null)
				return false;
		} else if (!proposalId.equals(other.proposalId))
			return false;
		return true;
	}
	
}
