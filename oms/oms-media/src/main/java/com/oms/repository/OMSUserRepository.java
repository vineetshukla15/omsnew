package com.oms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tavant.api.auth.model.OMSUser;

/**
 * The AccountRepository interface is a Spring Data JPA data repository for
 * Account entities. The AccountRepository provides all the data access
 * behaviors exposed by <code>JpaRepository</code> and additional custom
 * behaviors may be defined in this interface.
 */
@Repository
public interface OMSUserRepository extends JpaRepository<OMSUser, Long>, JpaSpecificationExecutor<OMSUser>,PagingAndSortingRepository<OMSUser,Long>  {

    /**
     * Query for a single Account entity by username.
     *
     * @param username A String username value to query the repository.
     * @return An Account or <code>null</code> if none found.
     */
    @Query("SELECT a FROM OMSUser a WHERE a.username = :username")
    OMSUser findByUsername(@Param("username") String username);

    @Query("SELECT a FROM OMSUser a WHERE a.emailId = :email")
	List<OMSUser> findByEmail(@Param("email")String email);
    
	@Query("SELECT a FROM OMSUser a WHERE a.id = :id and deleted = 0")
	OMSUser findByUserId(@Param("id")long id);
	
	@Query("SELECT user from OMSUser user where user.role.roleName like :roleName order by user.firstName")
	List<OMSUser> findByRoleName(@Param("roleName") String roleName);

    List<OMSUser> findByDeletedFalse(); 
    /**
    * for search Api
    */
    
    Page<OMSUser> findByFirstNameIgnoreCaseContaining(String firstName, Pageable pageRequest);
    
    Page<OMSUser> findByLastNameIgnoreCaseContaining(String lastName, Pageable pageRequest);
    
    Page<OMSUser> findByUsernameIgnoreCaseContaining(String username, Pageable pageRequest);
    
    Page<OMSUser> findByEmailIdIgnoreCaseContaining(String emailId, Pageable pageRequest);
    
    @Query("SELECT user from OMSUser user where user.enabled= :enabled") 
    Page<OMSUser> findUserByStatus(@Param("enabled") boolean enabled, Pageable pageRequest);
    
    @Query("SELECT user from OMSUser user where user.role.roleName like :roleName") 
    Page<OMSUser> findByRole(@Param("roleName") String roleName, Pageable pageRequest);

}