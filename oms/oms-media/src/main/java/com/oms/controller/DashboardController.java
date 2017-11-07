/**
 * 
 */
package com.oms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oms.service.DashboardService;
import com.oms.viewobjects.DashBoardProposalVO;
import com.oms.viewobjects.DashboardStatusVO;

/**
 * Controller for Dashboard.
 *
 */
@RestController
@RequestMapping(value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
public class DashboardController extends BaseController {

	@Autowired
	private DashboardService dashboardService;

	@RequestMapping(value = "/statistics/{startDate}/{userId}", method = RequestMethod.GET)
	public Map<String, Map<String, Long>> getProposalStatistics(@PathVariable String startDate, @PathVariable Long userId){
		return dashboardService.getProposalStatistics(startDate, userId);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/tasks/{assignee}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DashBoardProposalVO> getTasksByAssignee(@PathVariable String assignee) {
		return dashboardService.getTasksByAssignee(assignee);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/proposalCount/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> getProposalCountByStatus(@PathVariable  Long userId) {
		return dashboardService.getProposalCountByStatus(userId);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/proposal/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  List<DashBoardProposalVO> getProposalsByStatus(@RequestBody  DashboardStatusVO dashboardStatusVO) {
		return dashboardService.getAllProposalsByAssigneeAndStatus(dashboardStatusVO.getUserId(), dashboardStatusVO.getStatus());
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/candidateTasks/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DashBoardProposalVO> getProposalByCandidateTasks(@PathVariable String userId) {
		return dashboardService.getProposalsByUnassignedTask(userId);
	}
}
