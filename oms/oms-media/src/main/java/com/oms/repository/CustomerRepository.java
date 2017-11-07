package com.oms.repository;

import org.springframework.stereotype.Repository;
import org.tavant.api.auth.model.Customer;

@Repository
public interface CustomerRepository extends SoftDeleteRepository<Customer, Long> {

}
