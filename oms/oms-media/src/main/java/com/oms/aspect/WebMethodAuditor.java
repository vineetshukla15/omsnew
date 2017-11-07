package com.oms.aspect;

import java.util.Date;

import javax.transaction.Transactional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.oms.aspect.annotation.Auditable;
import com.oms.model.AuditRecord;
import com.oms.service.AuditRecordService;

@Aspect
@Component
public class WebMethodAuditor {
	@Autowired
	private AuditRecordService auditRecordService;

	@After("@annotation(auditable)")
	@Transactional
	public void afterWebMethodExecution(JoinPoint joinPoint, Auditable auditable) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object[] args = null;
		args = joinPoint.getArgs();
		String userName = "Anonymous";
		if(null!=authentication){
			userName = (String) authentication.getPrincipal();
		}
		String actionType = auditable.actionType().getDescription();
		String methodName = joinPoint.getSignature().toShortString();
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodArguement = (args != null && args.length > 0) ? args[0].toString(): "";
		AuditRecord auditRecord = new AuditRecord(userName, actionType, new Date(), methodName, className, methodArguement);

		auditRecordService.saveUseractivity(auditRecord);

	}

	public void setAuditRecordDAO(AuditRecordService auditRecordService) {
		this.auditRecordService = auditRecordService;
	}

}
