package com.oms.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.commons.utils.OMSConstants;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.Proposal;
import com.oms.service.ApprovalFlowService;
import com.oms.service.OMSUserService;
import com.oms.service.ProposalService;
import com.oms.viewobjects.MediaPlannerReview;


@Service
public class ApprovalFlowServiceImpl implements ApprovalFlowService{

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	RestResponseFactory restResponseFactory;
	
	@Autowired
	RepositoryService repositoryService;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ProposalService proposalService;
	
	@Autowired
	private OMSUserService oMSUserService;

	
	@Override
	@Transactional
	public ProcessInstanceResponse startApprovalProcessWithReview(MediaPlannerReview mpReview,Long version) throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		if(getTaskByProposalid(mpReview.getProposal().getProposalId())!=null){
			throw new OMSSystemException(Status.FAILED.name(),HttpStatus.CONFLICT, "Proposal id : "+mpReview.getProposal().getProposalId()+" already in work flow!");
		}
		Proposal actualproposal=proposalService.getProposalById(mpReview.getProposal().getProposalId());
		if(actualproposal!=null){	
			actualproposal=proposalService.getProposalById(mpReview.getProposal().getProposalId());
			Map<String, Object> vars =	 new HashMap<String, Object>();
			vars.put("action", mpReview.getAction());
			vars.put("status", mpReview.getProposalStatus());
			vars.put("comment", mpReview.getComment());
			vars.put("userid", mpReview.getUserId());
			vars.put("process_proposal", mpReview.getProposal());
			vars.put("proposal", mpReview.getProposal());
			vars.put("version",mpReview.getVersion());
			proposalService.updateProposalForWorkFlow(mpReview.getProposal(),
					mpReview.getProposalStatus(),"", mpReview.getVersion(),
					mpReview.getComment(),mpReview.getUserId(),true);
			ProcessInstance process = runtimeService.startProcessInstanceByKey("oms_proposal_review", null, vars); 
			ProcessInstanceResponse reponse = restResponseFactory.createProcessInstanceResponse(process);
			return reponse;
		}else{
			throw new OMSSystemException(Status.FAILED.name(),HttpStatus.CONFLICT, "Proposal do not exist with id : "+mpReview.getProposal().getProposalId());
		}

	}
	@Override
	public Object getProcessVariables(String variableName, String executionId){
		return runtimeService.getVariable(executionId, variableName);
	}
	
	@Transactional
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }
	@Override
	@Transactional
    public HistoricTaskInstance getProcess(String processInstanceId) {
	
		List<HistoricTaskInstance> taskList = historyService
				  .createHistoricTaskInstanceQuery()
				  .processInstanceId(processInstanceId)
				  .list();
		for(HistoricTaskInstance hs:taskList){
			if(null==hs.getEndTime()){
				return hs;
			}
		}
	    	return null;
		
        
    }

	@Override
	@Transactional
	public HistoricTaskInstance executeTask(String processInstanceId, String taskid, MediaPlannerReview mediaPlannerReview) {
		try{
			execute(processInstanceId, taskid, mediaPlannerReview);
			return getProcess(processInstanceId);
		}catch(Exception ie){
			ie.printStackTrace();
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, "Unable to update proposal in work flow", ie);
		}
	}
	
	
	
	@Override
	public Task getTaskByProposalid(Long proposalid,String userid) {
		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(userid).list();
		List<Task> assignedTasks = taskService.createTaskQuery().taskAssignee(userid).list();
		tasks.addAll(assignedTasks);
		for(Task task:tasks){
			Object proposalObject = null;
			try {
				proposalObject = getProcessVariables("process_proposal", task.getExecutionId());
				if(proposalObject!=null && proposalObject instanceof Proposal){
					Proposal proposal = (Proposal)proposalObject;
					if(proposal.getProposalId().equals(proposalid)){
						return task;
					}
				}
			}catch(ActivitiException e){
				//before go live we need to put this exception , this ignores data error in db
			}
			catch (Exception ex) {
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.UNPROCESSABLE_ENTITY, "unable to fetch due to incosistent proposal object", ex);
			}
		}
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_ACCEPTABLE, "unable to find proposal in work flow w.r.o. userid");

		}
	@Override
	public Task assignTask(String processInstanceId, String taskId,
			String assignee, Long userid) {
		taskService.setAssignee(taskId, assignee);
		Task taskSelected = taskService.createTaskQuery().processInstanceId(processInstanceId).taskId(taskId)
				.singleResult();
		Proposal proposal = (Proposal)getProcessVariables("process_proposal", taskSelected.getExecutionId());
		Proposal actualProposal=null;
		try {
			actualProposal = proposalService.getProposalById(proposal.getProposalId());
		} catch (OMSSystemException | IllegalAccessException
				| InvocationTargetException e) {
			e.printStackTrace();
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_ACCEPTABLE, "unable to find proposal in work flow with proposal id :");
		} 
		try{
			actualProposal.setAssignTo(oMSUserService.findByUserId(Long.valueOf(assignee)));
			proposalService.updateProposal(actualProposal,userid);
		}catch (Exception e){
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.UNPROCESSABLE_ENTITY, "unable to update proposal assign to field");

		}
		
		return taskSelected;
	}
	
	private void execute(String processInstanceId, String taskid, MediaPlannerReview mediaPlannerReview){
		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("action", mediaPlannerReview.getAction());
		taskVariables.put("statusDesc", mediaPlannerReview.getStatusDesc());
		taskVariables.put("status", mediaPlannerReview.getProposalStatus());
		taskVariables.put("comment", mediaPlannerReview.getComment());
		taskVariables.put("userid", mediaPlannerReview.getUserId());
		taskVariables.put("proposal", mediaPlannerReview.getProposal());
		taskVariables.put("version",mediaPlannerReview.getVersion());
/*		proposalService.updateProposalForWorkFlow(mediaPlannerReview.getProposal(),
				mediaPlannerReview.getProposalStatus(),mediaPlannerReview.getVersion(),
				mediaPlannerReview.getComment(),mediaPlannerReview.getUserId());*/
		taskService.complete(taskid, taskVariables);
	}
	
	public Task getTaskByProposalid(Long proposalid){
		List<Task> alltask=taskService.createTaskQuery().list();
		Task result=null;
		for(Task task:alltask){
			Object proposalObject = null;
			try {
				proposalObject = getProcessVariables("process_proposal", task.getExecutionId());
				if(proposalObject!=null && proposalObject instanceof Proposal){
					Proposal proposal = (Proposal)proposalObject;
					if(proposal.getProposalId().equals(proposalid)){
						result= task;
					}
				}
			}catch(ActivitiException e){
				//before go live we need to put this exception , this ignores data error in db
			}
			catch (Exception ex) {
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.UNPROCESSABLE_ENTITY, "unable to fetch due to incosistent proposal object", ex);
			}
		}
			return result;
	}
}
