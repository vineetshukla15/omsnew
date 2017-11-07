

package com.oms.viewobjects;

import java.math.BigDecimal;
import java.util.Date;

public class OrderLineItemListVO {
private String lineItemName;
private String status;
private Long productId;
private String type;
private Date startDate;
private Date endDate;
private Long impressions;
private BigDecimal lineItemCost;
private String proposalName;
private Long proposalId;
private Long lineItemId;
private Long OrderLineItemId;

public Long getOrderLineItemId() {
	return OrderLineItemId;
}
public void setOrderLineItemId(Long orderLineItemId) {
	OrderLineItemId = orderLineItemId;
}
public String getLineItemName() {
	return lineItemName;
}
public void setLineItemName(String lineItemName) {
	this.lineItemName = lineItemName;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

public Long getProductId() {
	return productId;
}
public void setProductId(Long productId) {
	this.productId = productId;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
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
public Long getImpressions() {
	return impressions;
}
public void setImpressions(Long impressions) {
	this.impressions = impressions;
}
public BigDecimal getLineItemCost() {
	return lineItemCost;
}
public void setLineItemCost(BigDecimal lineItemCost) {
	this.lineItemCost = lineItemCost;
}
public String getProposalName() {
	return proposalName;
}
public void setProposalName(String proposalName) {
	this.proposalName = proposalName;
}
public Long getProposalId() {
	return proposalId;
}
public void setProposalId(Long proposalId) {
	this.proposalId = proposalId;
}
public Long getLineItemId() {
	return lineItemId;
}
public void setLineItemId(Long lineItemId) {
	this.lineItemId = lineItemId;
}



}
