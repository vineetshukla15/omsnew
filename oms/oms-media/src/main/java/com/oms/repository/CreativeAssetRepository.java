package com.oms.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oms.model.CreativeAsset;
	
	@Repository
	public interface CreativeAssetRepository extends SoftDeleteRepository<CreativeAsset, Long>, JpaSpecificationExecutor<CreativeAsset>,
			PagingAndSortingRepository<CreativeAsset, Long> {

}
