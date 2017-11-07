package com.oms.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oms.model.SeasonalDiscount;

@Repository
public interface SeasonalDiscountRepository extends SoftDeleteRepository<SeasonalDiscount, Long>{

	List<SeasonalDiscount> findByDeletedFalse();

}
