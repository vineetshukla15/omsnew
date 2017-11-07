package com.oms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oms.model.Company;

@Repository
public interface CompanyRepository extends SoftDeleteRepository<Company, Long>, JpaSpecificationExecutor<Company>,
		PagingAndSortingRepository<Company, Long> {

	@Query("SELECT c from Company c where c.deleted= :deleted")
	List<Company> findAllCompany(@Param("deleted") boolean deleted);

	Page<Company> findByDeletedFalse(Pageable pageRequest);

	Page<Company> findByTypeIgnoreCaseContaining(String searchText, Pageable pageRequest);

	Page<Company> findByNameIgnoreCaseContaining(String searchText, Pageable pageRequest);

	List<Company> findByDeletedFalseOrderByNameAsc();
	
	@Query("SELECT c from Company c where c.deleted= :deleted and lower(c.name)=:companyName")
	Company findCompanyByName(@Param("companyName") String companyName, @Param("deleted") boolean deleted);

}
