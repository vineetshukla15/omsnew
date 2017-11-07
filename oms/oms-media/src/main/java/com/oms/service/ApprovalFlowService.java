package com.oms.service;

import java.lang.reflect.InvocationTargetException;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;

import com.oms.exceptions.OMSSystemException;
import com.oms.viewobjects.MediaPlannerReview;

public interface ApprovalFlowService {

	ProcessInstanceResponse startApprovalProcessWithReview(MediaPlannerReview mpreview,Long version) throws OMSSystemException, IllegalAccessException, InvocationTargetException;

	HistoricTaskInstance getProcess(String processInstanceId);

	public HistoricTaskInstance executeTask(String processInstanceId, String taskid, MediaPlannerReview mediaPlannerReview);

	Object getProcessVariables(String variableName, String executionId);
	
	Task getTaskByProposalid(Long proposalid,String userid);

	Task assignTask(String processInstanceId, String taskId, String assignee, Long userid);



}
