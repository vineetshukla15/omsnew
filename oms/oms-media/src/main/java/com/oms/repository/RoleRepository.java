package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tavant.api.auth.model.Role;

/**
 *
 */
@Repository
public interface RoleRepository extends SoftDeleteRepository<Role, Long>, JpaSpecificationExecutor<Role>,PagingAndSortingRepository<Role,Long>  {

	List<Role> findByDeletedFalseOrderByRoleNameAsc();

	@Query("SELECT r from Role r where r.deleted= :deleted and lower(r.roleName)=:roleName")
	Role findRoleByName(@Param("roleName") String roleName, @Param("deleted") boolean deleted);
  

}
