package com.oms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.tavant.api.auth.model.Customer;

import com.oms.exceptions.OMSSystemException;

@Service
public interface CustomerService {
	
	public Customer addCustomer(Customer customer);
	public Customer updateCustomer(Customer customer)throws OMSSystemException;
	public List<Customer> listCustomers();
	public Customer getCustomerById(Long customerID) throws OMSSystemException;
	public Customer removeCustomer(Customer customer) throws OMSSystemException;

}
