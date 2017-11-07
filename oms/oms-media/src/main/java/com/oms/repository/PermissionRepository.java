package com.oms.repository;

import java.util.List;

import org.tavant.api.auth.model.Permissions;

public interface PermissionRepository extends SoftDeleteRepository<Permissions, Long> {

	List<Permissions> findByDeletedFalseOrderByPermissionName();

}
