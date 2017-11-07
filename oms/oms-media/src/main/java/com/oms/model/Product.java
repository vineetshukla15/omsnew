package com.oms.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.tavant.api.auth.model.Customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "PRODUCT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -1654578209598920557L;

	@Id
	@GeneratedValue
	@Column(name="PRODUCT_ID")
	private Long productId;
	
	@NotNull(message="Mandatory")
	@NotBlank(message="Mandatory")
	@Pattern(regexp = "^(?=.*[a-zA-Z]).+$", message = "Name has invalid characters")
	@Column(name="NAME", unique = true)
	private String name;
	
	@NotNull(message="Mandatory")
	@NotBlank(message="Mandatory")
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name = "STATUS", columnDefinition = "BIT")
	private boolean status = false;
	
	@NotNull(message="Mandatory")
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_TYPE_ID")
    private ProductType productType;
	
	@NotNull(message="Mandatory")
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "RATE_TYPE_ID")
    private RateType rateType;
	
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;
	
	@NotNull(message="Mandatory")
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="PRODUCT_CREATIVES", 
	 joinColumns=@JoinColumn(name="PRODUCT_ID"),
	  inverseJoinColumns=@JoinColumn(name="CREATIVE_ID"))
	private Set<Creative> creatives ;
	
	@NotNull(message="Mandatory")
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="PRODUCT_AD_UNIT", 
	 joinColumns=@JoinColumn(name="PRODUCT_ID"),
	  inverseJoinColumns=@JoinColumn(name="AD_UNIT_ID"))
	private Set<ADUnit> adUnits;
	
	@OneToOne(mappedBy = "product")
	private RateCard rateCard;
	
	@JsonManagedReference
	@OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
	private Set<ProductTarget> target;
	
	@Column(name = "delivery_impressions", columnDefinition = "ENUM('Evenly', 'Frontloaded', 'As fast as possible')")
	private String deliveryImpressions;
	
	@Column(name = "display_creatives", columnDefinition = "ENUM('Only:one', 'One or more', 'As many as possible', 'All')")
	private String displayCreatives;
	
	@Column(name = "rotate_creatives", columnDefinition = "ENUM('Evenly', 'Optimized', 'Weighted', 'Sequential')")
	private String rotateCreatives;
	
	@Column(name = "priority", columnDefinition = "ENUM('Standard Normal', 'Standard High', 'Standard Low', 'Sponsorship', 'Network', 'Bulk', 'Price priority', 'House')")	
	private String priority;
	
	@Column(name = "priority_values")	
	private int priorityValues;
	
	
	public void setStatus(boolean status) {
		this.status = status;
	}

	public Product() {
		super();
		
	}

	public Set<Creative> getCreatives() {
		return creatives;
	}

	public void setCreatives(Set<Creative> creatives) {
		this.creatives = creatives;
	}

	public Set<ADUnit> getAdUnits() {
		return adUnits;
	}

	public void setAdUnits(Set<ADUnit> adUnits) {
		this.adUnits = adUnits;
	}

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getStatus() {
		return status;
	}


	public ProductType getProductType() {
		return productType;
	}



	public void setProductType(ProductType productType) {
		this.productType = productType;
	}



	public RateType getRateType() {
		return rateType;
	}



	public void setRateType(RateType rateType) {
		this.rateType = rateType;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public RateCard getRateCard() {
		return rateCard;
	}

	public void setRateCard(RateCard rateCard) {
		this.rateCard = rateCard;
	}

	public Set<ProductTarget> getTarget() {
		return target;
	}

	public void setTarget(Set<ProductTarget> target) {
		this.target = target;
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
}
