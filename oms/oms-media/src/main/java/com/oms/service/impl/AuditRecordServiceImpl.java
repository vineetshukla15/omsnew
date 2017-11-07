package com.oms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.model.AuditRecord;
import com.oms.repository.AuditRecordRepository;
import com.oms.service.AuditRecordService;
@Service
public class AuditRecordServiceImpl implements AuditRecordService{
	@Autowired
	private AuditRecordRepository auditRecordRepository;
	
	@Override
	@Transactional
	public void saveUseractivity(AuditRecord audit) {
		try {
			auditRecordRepository.save(audit);
		} catch (Exception e) {
			System.out.println("in exception");
		}
		
	}
}
