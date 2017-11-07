
package com.oms.settings.validators;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation that check the Date Range
 */

@Target(value = { ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {OpportunityValidator.class, OpportunityVersionValidator.class, ProposalValidator.class, ProposalVersionValidator.class, SeasonalDiscountValidator.class})

public @interface CheckDateRange {

	String message();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}