package org.tavant.api.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tavant.api.auth.model.OMSUser;

/**
 * The AccountRepository interface is a Spring Data JPA data repository for
 * Account entities. The AccountRepository provides all the data access
 * behaviors exposed by <code>JpaRepository</code> and additional custom
 * behaviors may be defined in this interface.
 */

@Repository
public interface UserRepository extends JpaRepository<OMSUser, Long>{

    /**
     * Query for a single Account entity by username.
     *
     * @param username A String username value to query the repository.
     * @return An Account or <code>null</code> if none found.
     */
    OMSUser findByUsername(String username);

}