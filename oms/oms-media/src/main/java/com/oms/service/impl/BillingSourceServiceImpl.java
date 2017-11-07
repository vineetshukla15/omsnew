package com.oms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tavant.oms.logging.annotation.Loggable;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.BillingSource;
import com.oms.repository.BillingSourceRepository;
import com.oms.service.BillingSourceService;

@Service
public class BillingSourceServiceImpl implements BillingSourceService {

	@Autowired
	private BillingSourceRepository billingSourceRepository;

	@Loggable
	@Transactional
	public List<BillingSource> getAllBillingSource() {
		List<BillingSource> billingSourceList = null;
		try {
			billingSourceList = billingSourceRepository.findByDeletedFalseOrderByNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Unable to retrive Billing Source information", ex);
		}
		return billingSourceList;

	}

}
