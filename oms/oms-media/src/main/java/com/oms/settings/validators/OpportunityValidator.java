package com.oms.settings.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.oms.model.Opportunity;

public class OpportunityValidator implements ConstraintValidator<CheckDateRange, Opportunity> {

	@Override
	public void initialize(CheckDateRange constraintAnnotation) {
	}

	@Override
	public boolean isValid(Opportunity opportunity, ConstraintValidatorContext context) {
		return opportunity.getStartDate().before(opportunity.getEndDate()) && opportunity.getStartDate().before(opportunity.getDueDate()) && opportunity.getDueDate().before(opportunity.getEndDate());
	}
}