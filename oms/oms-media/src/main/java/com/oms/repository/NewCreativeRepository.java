package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oms.model.NewCreative;


@Repository
public interface NewCreativeRepository extends SoftDeleteRepository<NewCreative, Long>, JpaSpecificationExecutor<NewCreative>,
		PagingAndSortingRepository<NewCreative, Long> {

	List<NewCreative> findByDeletedFalseOrderByNameAsc();

	NewCreative findByNewCreativeId(Long id);	
		
}
