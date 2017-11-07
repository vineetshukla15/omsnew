package com.oms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oms.model.CompanyStatus;

@Repository
public interface CompanyStatusRepository extends SoftDeleteRepository<CompanyStatus, Long>{
	
	 @Query("SELECT c from CompanyStatus c where c.deleted= :deleted")
	 List<CompanyStatus> findAllCompanyStatus(@Param("deleted") boolean deleted);

	Page<CompanyStatus> findByDeletedFalse(Pageable request);

	Page<CompanyStatus> findByNameIgnoreCaseContaining(String searchText, Pageable request);

	@Query("SELECT comStatus from CompanyStatus comStatus where comStatus.status= :status")
	Page<CompanyStatus> findUserByStatus(@Param("status") boolean status, Pageable pageRequest);

	List<CompanyStatus> findByDeletedFalseOrderByNameAsc();

}
