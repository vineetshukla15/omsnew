package com.oms.settings.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.oms.model.SeasonalDiscount;

public class SeasonalDiscountValidator implements ConstraintValidator<CheckDateRange, SeasonalDiscount> {

	@Override
	public void initialize(CheckDateRange constraintAnnotation) {
	}

	@Override
	public boolean isValid(SeasonalDiscount seasonalDiscount, ConstraintValidatorContext context) {
		return seasonalDiscount.getStartDate().before(seasonalDiscount.getEndDate()) || seasonalDiscount.getStartDate().equals(seasonalDiscount.getEndDate());
	}
}