package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oms.model.SpecType;


@Repository
public interface SpecTypeRepository extends SoftDeleteRepository<SpecType, Long> {
	
	 @Query("SELECT s from SpecType s where s.deleted= :deleted")
	 List<SpecType> findAllSpecType(@Param("deleted") boolean deleted);

	List<SpecType> findByDeletedFalseOrderByNameAsc();

}
