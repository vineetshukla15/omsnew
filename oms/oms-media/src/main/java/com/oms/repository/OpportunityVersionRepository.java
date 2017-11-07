package com.oms.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oms.model.OpportunityVersion;

@Repository
public interface OpportunityVersionRepository extends SoftDeleteRepository<OpportunityVersion, Long>{

	@Query("select max(a.version) from OpportunityVersion a where a.opportunity.opportunityId = :opportunityId")
	Long findMaxVersionOfOpportunity(@Param("opportunityId") Long opportunityId);

}