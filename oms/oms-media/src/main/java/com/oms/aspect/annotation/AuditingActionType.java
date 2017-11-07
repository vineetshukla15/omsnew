package com.oms.aspect.annotation;

public enum AuditingActionType {
	USER_REGISTRATION("User registration"),
	RESET_PASSWORD("Reset Password"),
	UPDATE_USER("update user"),
	DELETE_USER("delete user"),
	CREATE_NEW_USER("create new user"),
	
	ADD_NEW_ROLE("Add new role"),
	UPDATE_ROLE("update role"),
	DELETE_ROLE("delete role"), 
	
	ADD_NEW_CONTACT("Add new contact"),
	UPDATE_CONTACT("update contact"),
	DELETE_CONTACT("delete contact"),
	
	ADD_NEW_COMPANY("Add new company"),
	UPDATE_COMPANY("update company"),
	DELETE_COMPANY("delete company"),
	
	ADD_NEW_COMPANYSTATUS("Add new companyStatus"),
	UPDATE_COMPANYSTATUS("update companyStatus"),
	DELETE_COMPANYSTATUS("delete companyStatus"),
	
	ADD_NEW_LINEITEM("Add new lineItem"),
	UPDATE_LINEITEM("update lineItem"),
	DELETE_LINEITEM("delete lineItem"), 
	
	GET_ALL_CREATIVE("get all creatives"),
	ADD_NEW_CREATIVE("Add new creative"),
	UPDATE_CREATIVE("update creative"),
	DELETE_CREATIVE("delete creative"), 
	
	ADD_NEW_OPPORTUNITY("Add new opportunity"),
	UPDATE_OPPORTUNITY("update opportunity"),
	DELETE_OPPORTUNITY("delete opportunity"),
	COPY_OPPORTUNITY("copy opportunity"),
	
	ADD_NEW_OPPORTUNITY_VERSION("Add new opportunity version"),
	UPDATE_OPPORTUNITY_VERSION("update opportunity version"),
	
	ADD_NEW_PROPOSAL("Add new proposal"),
	UPDATE_PROPOSAL("update proposal"),
	DELETE_PROPOSAL("delete proposal"),
	COPY_PROPOSAL("copy proposal"),
	
	ADD_NEW_PROPOSAL_VERSION("Add new proposal version"),
	UPDATE_PROPOSAL_VERSION("update proposal version"),
	
	UPDATE_PRODUCT("update product"),
	DELETE_PRODUCT("delete product"), 
	
	ADD_NEW_SEASONAL_DISCOUNT("Add new seasonal discount"),
	UPDATE_SEASONAL_DISCOUNT("update seasonal discount"),
	DELETE_SEASONAL_DISCOUNT("delete seasonal discount"),
	
	START_PROPOSAL_APPROVAL_PROCESS("start Proposal Approval Process"),
	EXECUTE_TASK("execute task"),
	ASSIGN_TASK("assign task"),
	
	ADD_RATE_TYPE("Add rate type"),
	UPDATE_RATE_TYPE("update rate type"),
	DELETE_RATE_TYPE("delete rate type"),
	
	ADD_PRODUCT_TYPE("Add product type"),
	UPDATE_PRODUCT_TYPE("update product type"),
	DELETE_PRODUCT_TYPE("delete product type"),
	
	ADD_PREMIUM("Add premium"),
	UPDATE_PREMIUM("update premium"),
	DELETE_PREMIUM("delete premium"),
	
	ADD_RATE_CARD("Add rate card"),
	UPDATE_RATE_CARD("update rate card"),
	DELETE_RATE_CARD("delete rate card"),
	
	UPLOAD_PROPOSAL_DOCUMENT("upload proposal document"),
	UPLOAD_OPPORTUNITY_DOCUMENT("upload opportunity document"), 
	UPDATE_OPPORTUNITY_DOCUMENT("update opportunity document"),
	UPDATE_PROPOSAL_DOCUMENT("update proposal document"), ADD_NEW_PRODUCT("add new product"), 
	ADD_NEW_ADUNIT("add new adUnit"), UPDATE_ADUNIT("update adUnit"), DELETE_ADUNIT("delete adUnit"),
	ADD_AUDIENCE_TARGET_TYPE("add new audience target type"), 
	UPDATE_AUDIENCE_TARGET_TYPE("update audience target type"), DELETE_AUDIENCE_TARGET_TYPE("delete audience target type");
	
	String description;

	private AuditingActionType(String description) {
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}

}