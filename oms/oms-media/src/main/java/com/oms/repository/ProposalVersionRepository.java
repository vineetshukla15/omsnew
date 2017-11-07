package com.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oms.model.ProposalVersion;

@Repository
public interface ProposalVersionRepository extends JpaRepository<ProposalVersion, Long> {

	@Query("select max(a.version) from ProposalVersion a where a.proposal.proposalId = :proposalId")
	Long findMaxVersionOfProposalById(@Param("proposalId") Long proposalId);

}
