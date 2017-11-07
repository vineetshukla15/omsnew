package com.oms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oms.model.ADUnit;

@Repository
public interface ADUnitRepository extends SoftDeleteRepository<ADUnit, Long>, JpaSpecificationExecutor<ADUnit>,
		PagingAndSortingRepository<ADUnit, Long> {

	Page<ADUnit> findByNameIgnoreCaseContaining(String searchText, Pageable pageRequest);

	Page<ADUnit> findByDisplayNameIgnoreCaseContaining(String searchText, Pageable pageRequest);

	Page<ADUnit> findByCapacityIgnoreCaseContaining(String searchText, Pageable pageRequest);

	Page<ADUnit> findByWeightIgnoreCaseContaining(String searchText, Pageable pageRequest);

	@Query("SELECT adUnit from ADUnit adUnit where adUnit.isActive= :isActive")
	Page<ADUnit> findUserByStatus(@Param("isActive") boolean enabled, Pageable pageRequest);

	Page<ADUnit> findByDeletedFalse(Pageable pageRequest);

	List<ADUnit> findByDeletedFalseOrderByNameAsc();

}
