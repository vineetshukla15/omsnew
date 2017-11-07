package com.oms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.activiti.rest.service.api.runtime.task.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.service.ApprovalFlowService;
import com.oms.viewobjects.MediaPlannerReview;

@RestController
public class OMSApprovalActivityWorkFlow extends BaseController {

	@Autowired
	RestResponseFactory restResponseFactory;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ApprovalFlowService approvalFlowService;

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/start-proposal-approval", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProcessInstanceResponse startProposalApprovalProcess(@RequestBody MediaPlannerReview mpreview)
			throws OMSSystemException, IllegalAccessException, InvocationTargetException {

		mpreview.getProposal().setUpdatedBy(findUserIDFromSecurityContext());
		mpreview.getProposal().setUpdated(new Date());
		return approvalFlowService.startApprovalProcessWithReview(mpreview,mpreview.getVersion());
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/process/{processInstanceId}/task/{assignee}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskResponse> getTasks(@PathVariable String processInstanceId, @PathVariable String assignee) {
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee(assignee)
				.list();
		List<TaskResponse> taskResponses = new ArrayList<TaskResponse>();

		tasks.forEach((task) -> {
			TaskResponse taskResponse = restResponseFactory.createTaskResponse(task);
			taskResponses.add(taskResponse);
		});
		return taskResponses;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/process/{processInstanceId}/task/candidategroup/{candidateGroup}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskResponse> getCandidateGroupTasks(@PathVariable String processInstanceId,
			@PathVariable String candidateGroup) {
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId)
				.taskCandidateGroup(candidateGroup).list();
		List<TaskResponse> taskResponses = new ArrayList<TaskResponse>();

		tasks.forEach((task) -> {
			TaskResponse taskResponse = restResponseFactory.createTaskResponse(task);
			taskResponses.add(taskResponse);
		});
		return taskResponses;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/process/{processInstanceId}/task/candidateuser/{candidateUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskResponse> getCandidateUserTasks(@PathVariable String processInstanceId,
			@PathVariable String candidateUser) {
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId)
				.taskCandidateUser(candidateUser).list();
		List<TaskResponse> taskResponses = new ArrayList<TaskResponse>();

		tasks.forEach((task) -> {
			TaskResponse taskResponse = restResponseFactory.createTaskResponse(task);
			taskResponses.add(taskResponse);
		});
		return taskResponses;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/task/candidateuser/{candidateUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskResponse> getCandidateUserAllTasks(@PathVariable String candidateUser) {
		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(candidateUser).list();
		List<TaskResponse> taskResponses = new ArrayList<TaskResponse>();

		tasks.forEach((task) -> {
			TaskResponse taskResponse = restResponseFactory.createTaskResponse(task);
			taskResponses.add(taskResponse);
		});
		return taskResponses;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/process/{processInstanceId}/executeTask/{taskid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.EXECUTE_TASK)
	public HistoricTaskInstance executeTask(@PathVariable String processInstanceId, @PathVariable String taskid,
			@RequestBody MediaPlannerReview mediaPlannerReview) {
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskId(taskid).singleResult();

		if (StringUtils.hasText(task.getAssignee())
				&& task.getAssignee().equals(String.valueOf(findUserIDFromSecurityContext()))) {
			mediaPlannerReview.setUserId(findUserIDFromSecurityContext());
			return approvalFlowService.executeTask(processInstanceId, taskid, mediaPlannerReview);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.FORBIDDEN, "Not valid assignee");
		}
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/process/{processInstanceId}/assignTask/{taskId}/{assignee}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.ASSIGN_TASK)
	public List<TaskResponse> assignTask(@PathVariable String processInstanceId, @PathVariable String taskId,
			@PathVariable String assignee) {
		List<TaskResponse> taskResponses = new ArrayList<TaskResponse>();
		TaskResponse taskResponse = restResponseFactory.createTaskResponse(approvalFlowService.assignTask(processInstanceId,taskId,assignee,findUserIDFromSecurityContext()));
		taskResponses.add(taskResponse);
		return taskResponses;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/processinfo/{processInstanceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public HistoricTaskInstance getProcess(@PathVariable String processInstanceId) {
		return approvalFlowService.getProcess(processInstanceId);

	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/proposal/task/{proposalId}/{candidateUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public TaskResponse getTaskOfProposal(@PathVariable Long proposalId,
			@PathVariable String candidateUser) {
		Task task = approvalFlowService.getTaskByProposalid(proposalId, candidateUser);
		return restResponseFactory.createTaskResponse(task);

	}
}