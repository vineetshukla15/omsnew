package com.oms.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tavant.api.auth.model.Customer;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.repository.CustomerRepository;
import com.oms.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	final static Logger LOGGER = Logger.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private CustomerRepository customerRepository;

	@Transactional
	public Customer addCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Transactional
	public Customer updateCustomer(Customer customer) throws OMSSystemException {
		if(customer.getCustomerID()!=null && Boolean.FALSE.equals(customer.isDeleted())){
			customerRepository.save(customer);
			return customer;
		}throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
				"Customer with {" + customer.getCustomerID() + "} does not exist.");
		
	}

	@Transactional
	public List<Customer> listCustomers() {
		return customerRepository.findAll();
	}

	@Transactional
	public Customer getCustomerById(Long customerID) throws OMSSystemException {
		if(customerRepository.findById(customerID)!=null){
			return customerRepository.findById(customerID);
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"Customer with id"+customerID+ "does not exist");
	}

	@Transactional
	public Customer removeCustomer(Customer customer) throws OMSSystemException {
		if(customer.getCustomerID()!=null){
			long id=customer.getCustomerID();
			customerRepository.softDelete(id);
			return customer;
		}throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
				"Customer with {" + customer.getCustomerID() + "} does not exist.");
		

	}
	
	

}
