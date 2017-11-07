package com.oms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tavant.api.auth.model.OMSUser;

import com.oms.model.Proposal;
import com.oms.model.ProposalStatus;

@Repository
public interface ProposalRepository extends SoftDeleteRepository<Proposal, Long>, JpaSpecificationExecutor<Proposal>,
		PagingAndSortingRepository<Proposal, Long> {

	List<Proposal> findByDeletedFalseOrderByNameAsc();

	Proposal findByProposalId(Long id);
	
	@Query("select p.status.name, count(*), DATE(p.updated)  from Proposal p where DATE(p.updated) BETWEEN ?1 AND adddate(?1, 5) and p.updatedBy=?2 group by DATE(p.updated), p.status")
	List<Object[]> getProposalStatistics(Date startDate, Long userId);
	
	@Query("select p.status.statusId, count(p) as count from Proposal p where p.updatedBy=?1 group by p.status")
	List<Object[]> getProposalCountByStatus(Long userId);
	
	@Query("select p from Proposal p where p.assignTo.id=?1 and p.status.statusId=?2")
	List<Proposal> getByAssignToAndStatus(Long assignTo,Long id);
	
	@Query("select p from Proposal p where p.assignTo.id=:assignTo and p.status.statusId in :ids")
	List<Proposal> getByAssignToAndStatus(@Param("assignTo") Long assignTo,@Param("ids")List<Long> ids);
	
	@Query("select p from Proposal p where p.status.statusId = ?1")
	List<Proposal> getProposalByStatus(Long id);
	
	
}
