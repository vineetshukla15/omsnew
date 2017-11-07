package com.oms.repository;

import org.springframework.stereotype.Repository;

import com.oms.model.AuditRecord;
@Repository
public interface AuditRecordRepository extends SoftDeleteRepository<AuditRecord, Long>{

}
