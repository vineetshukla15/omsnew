package com.oms.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;

@Entity
@Table(name = "LINE_ITEMS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItems extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1222121541558515616L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "LINE_ITEM_ID", unique = true, nullable = false)
	private Long lineItemId;

	@NotNull(message = "Mandatory")
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	@Column(name = "FLIGHT_START_DATE")
	private Date flightStartdate;

	@Column(name = "FLIGHT_END_DATE")
	private Date flightEndDate;

	@OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "LINEITEM_ID", referencedColumnName = "LINE_ITEM_ID")
	private Set<LineitemTarget> target;

	@NotNull(message = "Mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SPEC_TYPE_ID")
	private SpecType specType;
	
	@ManyToOne
	@JoinColumn(name = "PROPOSAL_ID", referencedColumnName = "PROPOSAL_ID", insertable = false, updatable = false)
	private Proposal proposal;

	@Column(name = "name")
	private String name;
	@Column(name = "cpms")
	private String cpms;
	@Column(name = "avails")
	private String avails;

	@Column(name = "QUANTITY")
	private Long quantity;
	
	@Column(name = "proposed_cost")
	private BigDecimal proposedCost;
	
	@Column(name = "delivery_impressions", columnDefinition = "ENUM('Evenly', 'Frontloaded', 'As fast as possible')")
	private String deliveryImpressions;
	
	@Column(name = "display_creatives", columnDefinition = "ENUM('Only one', 'One or more', 'As many as possible', 'All')")
	private String displayCreatives;
	
	@Column(name = "rotate_creatives", columnDefinition = "ENUM('Evenly', 'Optimized', 'Weighted', 'Sequential')")
	private String rotateCreatives;
	
	@Column(name = "priority", columnDefinition = "ENUM('Standard Normal', 'Standard High', 'Standard Low', 'Sponsorship', 'Network', 'Bulk', 'Price priority', 'House')")	
	private String priority;
	
	@Column(name = "priority_value")	
	private int priorityValues;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RATE_TYPE_ID")
	private RateType rateType;

	public Long getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(Long lineItemId) {
		this.lineItemId = lineItemId;
	}

	public Set<LineitemTarget> getTarget() {
		return target;
	}

	public void setTarget(Set<LineitemTarget> target) {
		this.target = target;
	}

	public SpecType getSpecType() {
		return specType;
	}

	public void setSpecType(SpecType specType) {
		this.specType = specType;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public LineItems() {
		super();
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getFlightStartdate() {
		return flightStartdate;
	}

	public void setFlightStartdate(Date flightStartdate) {
		this.flightStartdate = flightStartdate;
	}

	public Date getFlightEndDate() {
		return flightEndDate;
	}

	public void setFlightEndDate(Date flightEndDate) {
		this.flightEndDate = flightEndDate;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpms() {
		return cpms;
	}

	public void setCpms(String cpms) {
		this.cpms = cpms;
	}

	public String getAvails() {
		return avails;
	}

	public void setAvails(String avails) {
		this.avails = avails;
	}

	
	public BigDecimal getProposedCost() {
		if(proposedCost!=null)
		return proposedCost;
		else
			return BigDecimal.ZERO;
	}

	public void setProposedCost(BigDecimal proposedCost) {
		this.proposedCost = proposedCost;
	}

	public void getPrice(LineItems lineItem) {
		Product product = lineItem.getProduct();
		if (product.getRateCard() != null) {
			BigDecimal basePrice = product.getRateCard().getBasePrice();
			this.proposedCost=basePrice;
			if (product.getRateCard().getSeasonalDiscounts() != null
					&& product.getRateCard().getSeasonalDiscounts().size() > 0) {
				this.proposedCost = calculteSessionalDiscount(lineItem, product.getRateCard().getSeasonalDiscounts(),
						basePrice);
			} else {
				this.proposedCost = basePrice;
			}
			if (product.getRateCard().getPremiums() != null && product.getRateCard().getPremiums().size() > 0) {
				this.proposedCost = calcultePremium(product.getRateCard().getPremiums(), this.proposedCost);
			}
		} else {
			Exception e = new OMSSystemException(Status.FAILED.name(), HttpStatus.FAILED_DEPENDENCY,
					"Pricing calculation failed! Rate card do not exist!");
			e.printStackTrace();
		}
		
	}

	@Transient
	private BigDecimal priceAfterDiscount;

	public BigDecimal calculteSessionalDiscount(LineItems lineitem, Collection<SeasonalDiscount> seasonalDiscounts,
			BigDecimal basePrice) {
		priceAfterDiscount = BigDecimal.ZERO;
		proposedCost=BigDecimal.ZERO;
		System.out.println("base price before sessional discount" + basePrice);
		BigDecimal divisor = BigDecimal.valueOf(100);
		seasonalDiscounts.forEach(sd -> {
			if (isValidSeasonalDiscount(lineitem, sd)) {
				BigDecimal discount = new BigDecimal(sd.getDiscount());
				BigDecimal temp = basePrice.add((basePrice.multiply(discount)).divide(divisor));
				proposedCost = priceAfterDiscount.add(temp);
			}
		});
		return proposedCost;
	}

	public BigDecimal calcultePremium(Collection<Premium> premiums, BigDecimal costAfterSessionalDsicount) {
		priceAfterDiscount = BigDecimal.ZERO;
		proposedCost=BigDecimal.ZERO;
		BigDecimal divisor = BigDecimal.valueOf(100);
		premiums.forEach(sd -> {
			BigDecimal discount = new BigDecimal(sd.getPremiumPercentage());
			BigDecimal temp = costAfterSessionalDsicount
					.add((costAfterSessionalDsicount.multiply(discount)).divide(divisor));
			proposedCost = priceAfterDiscount.add(temp);
		});
		return proposedCost;
	}

	private boolean isValidSeasonalDiscount(LineItems lineitem, SeasonalDiscount seasonalDiscount) {
		if (lineitem.getFlightStartdate().compareTo(seasonalDiscount.getStartDate()) >= 0
				&& lineitem.getFlightStartdate().compareTo(seasonalDiscount.getEndDate()) <= 0
				&& lineitem.getFlightEndDate().compareTo(seasonalDiscount.getEndDate()) <= 0
				&& lineitem.getFlightEndDate().compareTo(seasonalDiscount.getStartDate()) >= 0) {
			return true;
		}
		return false;
	}

	public String getDeliveryImpressions() {
		return deliveryImpressions;
	}

	public void setDeliveryImpressions(String deliveryImpressions) {
		this.deliveryImpressions = deliveryImpressions;
	}

	public String getDisplayCreatives() {
		return displayCreatives;
	}

	public void setDisplayCreatives(String displayCreatives) {
		this.displayCreatives = displayCreatives;
	}

	public String getRotateCreatives() {
		return rotateCreatives;
	}

	public void setRotateCreatives(String rotateCreatives) {
		this.rotateCreatives = rotateCreatives;
	}


	public String getPriority() {
/*		if(priority!=null)
		switch(priority){
		case "Standard Normal" :
		{
			priority="Standard(Normal)";
			break;
		}
		case "Standard High":{
			priority="Standard(High)";
			break;
		}
		case "Standard Low":{
			priority="Standard(Low)";
			break;
		}
	}*/
		return priority;
	}

	public void setPriority(String priority) {
/*		if(priority!=null)
		switch(priority){
		case "Standard(Normal)" :
		{
			priority="Standard Normal";
			break;
		}
		case "Standard(High)":{
			priority="Standard High";
			break;
		}
		case "Standard(Low)":{
			priority="Standard Low";
			break;
		}
	}*/
		this.priority = priority;
	}
	public int getPriorityValues() {
		return priorityValues;
	}

	public void setPriorityValues(int priorityValues) {
		this.priorityValues = priorityValues;
	}

	public BigDecimal getPriceAfterDiscount() {
		return priceAfterDiscount;
	}

	public void setPriceAfterDiscount(BigDecimal priceAfterDiscount) {
		this.priceAfterDiscount = priceAfterDiscount;
	}

	public RateType getRateType() {
		return rateType;
	}

	public void setRateType(RateType rateType) {
		this.rateType = rateType;
	}
	
	
}
