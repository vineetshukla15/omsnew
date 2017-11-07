package com.oms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oms.model.AudienceTargetType;

/**
 * 
 * @author vikas.parashar
 *
 */
@Repository
public interface AudienceTargetTypeRepository extends SoftDeleteRepository<AudienceTargetType, Long>{

	Page<AudienceTargetType> findByNameIgnoreCaseContaining(String searchText, Pageable pageRequest);

	@Query("SELECT att from AudienceTargetType att where att.status= :status") 
	Page<AudienceTargetType> findUserByStatus(@Param("status") boolean status, Pageable pageRequest);

	Page<AudienceTargetType> findByDeletedFalse(Pageable pageRequest);

	List<AudienceTargetType> findByDeletedFalseOrderByNameAsc();

}
