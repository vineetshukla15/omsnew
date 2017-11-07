/**
 * 
 */
package com.oms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oms.viewobjects.DashBoardProposalVO;

/**
 * Service for Dashboard.
 *
 */
@Service
public interface DashboardService {

	public Map<String, Map<String, Long>> getProposalStatistics(String startDate, Long userId);

	List<DashBoardProposalVO> getTasksByAssignee(String assignee);

	Map<String, Long> getProposalCountByStatus(Long userId);

	List<DashBoardProposalVO> getProposalsByUnassignedTask(
			String candidateUserid);

	List<DashBoardProposalVO> getAllProposalsByAssigneeAndStatus(
			Long assignee, List<String> status);
	
}
