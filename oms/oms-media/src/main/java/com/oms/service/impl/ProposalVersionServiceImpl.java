package com.oms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oms.model.Proposal;
import com.oms.model.ProposalVersion;
import com.oms.repository.ProposalVersionRepository;
import com.oms.service.ProposalVersionService;

@Service
public class ProposalVersionServiceImpl implements ProposalVersionService {

	@Autowired
	private ProposalVersionRepository proposalVersionRepository;

}
