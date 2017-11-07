/**
 * 
 */
package com.oms.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.tavant.api.auth.model.OMSUser;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.Proposal;
import com.oms.model.ProposalStatus;
import com.oms.repository.OMSUserRepository;
import com.oms.repository.ProposalRepository;
import com.oms.repository.ProposalStatusRepository;
import com.oms.service.ApprovalFlowService;
import com.oms.service.DashboardService;
import com.oms.viewobjects.DashBoardProposalVO;

/**
 * Service for Dashboard.
 *
 */
@Service
public class DashboardServiceImpl implements DashboardService {

	final static Logger logger = Logger.getLogger(DashboardServiceImpl.class);

	@Autowired
	private ProposalRepository proposalRepository;
	@Autowired
	private ProposalStatusRepository proposalStatusRepository;
	

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ApprovalFlowService approvalFlowService;
	
	@Autowired
	private OMSUserRepository omsUserRepository;
	
	private Map<String,Long> countMapByStatus=null;
	
	@Override
	public Map<String, Map<String, Long>> getProposalStatistics(String startDate, Long userId) throws OMSSystemException {
		Map<String, Map<String, Long>> statisticsMap = new HashMap<String, Map<String, Long>>();
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			Date beginDate = (Date)formatter.parse(startDate);
			List<Object[]> statsList = proposalRepository.getProposalStatistics(beginDate, userId);
			
			String strDate = "";
			Map<Long, String> statusMap=getStatusNameById();
			if (!statsList.isEmpty()) {
				for (Object[] obj : statsList) {
					String statusId = (String) obj[0];
					Long count = (Long) obj[1];
					Date date = (Date) obj[2];
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					strDate = dateFormat.format(date);
					if(statisticsMap.containsKey(strDate)){
						countMapByStatus.put(statusId, count);
					}else{
						countMapByStatus=new HashMap<String, Long>();
						statusMap.forEach((key,value)->{countMapByStatus.put(value, 0L);});
						countMapByStatus.put(statusId, count);
					}
								
					statisticsMap.put(strDate, countMapByStatus);
				}			
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND, "Proposal Statistics not found.",
					ex);
		}
		return statisticsMap;
	}
	@Override
	public List<DashBoardProposalVO> getTasksByAssignee(String assignee) {
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
		List<DashBoardProposalVO> proposals = new ArrayList<DashBoardProposalVO>();
		ProposalStatus proposalStatus= proposalStatusRepository.findByName("Draft");
		List<Proposal> draftproposals = proposalRepository.getByAssignToAndStatus(Long.valueOf(assignee), proposalStatus.getStatusId());
		draftproposals.forEach(dprop->{
			proposals.add(getProposalVO(dprop));
		});
		tasks.forEach((task) -> {
			Object proposalObject = null;
			try {
				proposalObject = approvalFlowService.getProcessVariables("proposal", task.getExecutionId());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
				if(proposalObject!=null){
					if(proposalObject instanceof Proposal){
						Proposal proposal = (Proposal)proposalObject;
						proposals.add(getProposalVO(proposal));
						
					}else{
						//System.out.println("Not a proposal var : "+proposalObject);
					}
				}
		});
		Collections.sort(proposals, (p1,p2)->p2.getUpdatedDate().compareTo(p1.getUpdatedDate()));
		return proposals;
		}
	@Override
	public List<DashBoardProposalVO> getAllProposalsByAssigneeAndStatus(Long assignee,List<String> status) {
		List<DashBoardProposalVO> proposals = new ArrayList<DashBoardProposalVO>();
		List<ProposalStatus> listofproposalStatus= proposalStatusRepository.findAll();
		List<Long> listofStatusIds=listofproposalStatus.stream().filter(ps->status.contains(ps.getName())).map(ProposalStatus :: getStatusId).collect(Collectors.toList());
		List<Proposal> allproposals = proposalRepository.getByAssignToAndStatus(assignee, listofStatusIds);
		allproposals.forEach(prop->{
			proposals.add(getProposalVO(prop));
		});
		Collections.sort(proposals, (p1,p2)->p2.getUpdatedDate().compareTo(p1.getUpdatedDate()));
		return proposals;
		}
	
	@Override
	public Map<String,Long> getProposalCountByStatus(Long userId){
		Map<String,Long> countMapByStatus=new HashMap<String, Long>();
		Map<Long, String> statusMap=getStatusNameById();
		statusMap.forEach((key,value)->{countMapByStatus.put(value, 0L);});
		List<Object[]> result = proposalRepository.getProposalCountByStatus(userId);
	
		result.forEach((obj)->{Long statusid=(Long)obj[0];
		Long count= (Long)obj[1];
		countMapByStatus.put(statusMap.get(statusid),count);});
		return countMapByStatus;
	}
	
	final Map<Long, String> getStatusNameById(){
		Map<Long, String> map=new HashMap<Long, String>();
		List<ProposalStatus> proposalStatus = proposalStatusRepository.findAll();
		proposalStatus.forEach(propStatus->{map.put(propStatus.getStatusId(), propStatus.getName());});
		return map;
	}
	
	@Override
	public List<DashBoardProposalVO> getProposalsByUnassignedTask( String candidateUserid) {
		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(candidateUserid).list();
		List<DashBoardProposalVO> proposals = new ArrayList<DashBoardProposalVO>();
		tasks.forEach((task) -> {
			Object proposalObject = null;
			try {
				proposalObject = approvalFlowService.getProcessVariables("proposal", task.getExecutionId());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
				if(proposalObject!=null){
					if(proposalObject instanceof Proposal){
						Proposal proposal = (Proposal)proposalObject;
						DashBoardProposalVO proposalVO=new DashBoardProposalVO();
						proposalVO.setDueDate(proposal.getDueDate());
						proposalVO.setAdvertiser(proposal.getAdvertiser().getName());
						proposalVO.setProposalCost(proposal.getProposalCost());
						proposalVO.setProposalId(proposal.getProposalId());
						proposalVO.setProposalName(proposal.getName());
						proposalVO.setSalesPerson(proposal.getSalesPerson().getFirstName()+proposal.getSalesPerson().getLastName());
						proposalVO.setStatus(proposal.getStatus().getName());
						proposalVO.setProposalTrafficker(proposal.getTrafficker().getFirstName()+proposal.getTrafficker().getLastName());
						proposalVO.setSalesCategory(proposal.getSalesCategory().getName());
						proposalVO.setUpdatedDate(proposal.getUpdated());
						proposals.add(proposalVO);
						
					}else{
						//System.out.println("Not a proposal var : "+proposalObject);
					}
				}
		});
		Collections.sort(proposals, (p1,p2)->{
			try {
				System.out.println(p2.getUpdatedDate().compareTo(p1.getUpdatedDate()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return p2.getUpdatedDate().compareTo(p1.getUpdatedDate());
			});
		return proposals;
		}

	private DashBoardProposalVO getProposalVO(Proposal proposal){
		DashBoardProposalVO proposalVO=new DashBoardProposalVO();
		proposalVO.setDueDate(proposal.getDueDate());
		if(proposal.getAdvertiser()!=null){
		proposalVO.setAdvertiser(proposal.getAdvertiser().getName());
		}
		if(proposal.getProposalCost()!=null){
			proposalVO.setProposalCost(proposal.getProposalCost());
		}else{
			proposalVO.setProposalCost(BigDecimal.ZERO);
		}
		proposalVO.setProposalId(proposal.getProposalId());
		proposalVO.setProposalName(proposal.getName());
		proposalVO.setSalesPerson(proposal.getSalesPerson().getFirstName()+proposal.getSalesPerson().getLastName());
		proposalVO.setStatus(proposal.getStatus().getName());
		proposalVO.setProposalTrafficker(proposal.getTrafficker().getFirstName()+proposal.getTrafficker().getLastName());
		if(proposal.getSalesCategory()!=null){
			proposalVO.setSalesCategory(proposal.getSalesCategory().getName());
		}
		proposalVO.setUpdatedDate(proposal.getUpdated());
		if(proposal.getMediaPlanner()!=null)
		proposalVO.setPlanner(proposal.getMediaPlanner().getFirstName()+" "+proposal.getMediaPlanner().getLastName());
		return proposalVO;
	}
}
