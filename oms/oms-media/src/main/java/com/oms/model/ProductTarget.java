package com.oms.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product_target", uniqueConstraints = @UniqueConstraint(columnNames = "TARGET_TYPE"))
public class ProductTarget implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4974688643926985008L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PRODUCT_TARGET_ID", unique = true, nullable = false)
	private long id;
	@JsonBackReference
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", insertable = false, updatable = false)
	private Product product;
	
    @OneToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "product_target_value", joinColumns = { @JoinColumn(name = "PRODUCT_TARGET_VALUE") }, inverseJoinColumns = {
			@JoinColumn(name = "product_audience_target_values") })
	private Set<AudienceTargetValues> audienceTargetValues;
    
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "TARGET_TYPE", unique = true)
	private AudienceTargetType audienceTargetType;
	
	@JsonIgnore
	@Column(name="deleted", columnDefinition = "BIT")
	private boolean deleted=false;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Set<AudienceTargetValues> getAudienceTargetValues() {
		return audienceTargetValues;
	}

	public void setAudienceTargetValues(
			Set<AudienceTargetValues> audienceTargetValues) {
		this.audienceTargetValues = audienceTargetValues;
	}

	public AudienceTargetType getAudienceTargetType() {
		return audienceTargetType;
	}

	public void setAudienceTargetType(AudienceTargetType audienceTargetType) {
		this.audienceTargetType = audienceTargetType;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
