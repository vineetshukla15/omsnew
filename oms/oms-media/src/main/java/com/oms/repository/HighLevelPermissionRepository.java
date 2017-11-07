package com.oms.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.tavant.api.auth.model.HighLevelPermission;

@Repository
public interface HighLevelPermissionRepository extends SoftDeleteRepository<HighLevelPermission, Long>{

	List<HighLevelPermission> findByDeletedFalseOrderByPermissionNameAsc();

}
