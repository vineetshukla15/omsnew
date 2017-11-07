package com.oms.settings.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.oms.model.Proposal;

public class ProposalValidator implements ConstraintValidator<CheckDateRange, Proposal> {

	@Override
	public void initialize(CheckDateRange constraintAnnotation) {
	}

	@Override
	public boolean isValid(Proposal proposal, ConstraintValidatorContext context) {
		return proposal.getStartDate().before(proposal.getEndDate())
				&& proposal.getStartDate().before(proposal.getDueDate())
				&& proposal.getDueDate().before(proposal.getEndDate());
	}
}