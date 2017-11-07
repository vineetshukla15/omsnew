package com.oms.repository;

import org.springframework.stereotype.Repository;

import com.oms.model.Premium;

/**
 * 
 * @author vikas.parashar
 *
 */
@Repository
public interface PremiumRepository extends SoftDeleteRepository<Premium, Long>{

}
