package com.oms.repository;

import java.util.List;

import com.oms.model.RateType;

public interface RateTypeRepository extends SoftDeleteRepository<RateType, Long>{

	List<RateType> findByDeletedFalseOrderByNameAsc();

}
