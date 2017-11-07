package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oms.model.RateCard;

@Repository
public interface RateCardRepository extends SoftDeleteRepository<RateCard, Long>,
		JpaSpecificationExecutor<RateCard>, PagingAndSortingRepository<RateCard, Long> {

	List<RateCard> findByDeletedFalseOrderBySectionsNameAsc();

}
