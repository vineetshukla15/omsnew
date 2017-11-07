package com.oms.settings.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.oms.model.OpportunityVersion;

public class OpportunityVersionValidator implements ConstraintValidator<CheckDateRange, OpportunityVersion> {

	@Override
	public void initialize(CheckDateRange constraintAnnotation) {
	}

	@Override
	public boolean isValid(OpportunityVersion opportunityVersion, ConstraintValidatorContext context) {
		return opportunityVersion.getStartDate().before(opportunityVersion.getEndDate()) && opportunityVersion.getStartDate().before(opportunityVersion.getDueDate()) && opportunityVersion.getDueDate().before(opportunityVersion.getEndDate());
	}
}