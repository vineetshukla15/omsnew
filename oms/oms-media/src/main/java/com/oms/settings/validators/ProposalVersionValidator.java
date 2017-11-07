package com.oms.settings.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.oms.model.ProposalVersion;

public class ProposalVersionValidator implements ConstraintValidator<CheckDateRange, ProposalVersion> {

	@Override
	public void initialize(CheckDateRange constraintAnnotation) {
	}

	@Override
	public boolean isValid(ProposalVersion proposalVersion, ConstraintValidatorContext context) {
		return proposalVersion.getStartDate().before(proposalVersion.getEndDate())
				&& proposalVersion.getStartDate().before(proposalVersion.getDueDate())
				&& proposalVersion.getDueDate().before(proposalVersion.getEndDate());
	}
}