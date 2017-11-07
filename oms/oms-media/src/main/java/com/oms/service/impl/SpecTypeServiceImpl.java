package com.oms.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.SpecType;
import com.oms.repository.SpecTypeRepository;
import com.oms.service.SpecTypeService;

@Service
public class SpecTypeServiceImpl implements SpecTypeService {
	final static Logger LOGGER = Logger.getLogger(SpecTypeServiceImpl.class);
	
	@Autowired
	SpecTypeRepository specTypeRepository;

	@Override
	@Transactional
	public List<SpecType> listSpecType() {
		List<SpecType> specTypes = null;
		try {
			specTypes = specTypeRepository.findByDeletedFalseOrderByNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive SpecType information",ex);
		}
		return specTypes;
	}

}
