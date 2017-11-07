package com.oms.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.AudienceTargetType;
import com.oms.repository.AudienceTargetTypeRepository;
import com.oms.service.AudienceTargetTypeService;

/**
 * 
 * @author vikas.parashar
 *
 */
@Service
public class AudienceTargetTypeServiceImpl implements AudienceTargetTypeService {

	final static Logger LOGGER = Logger.getLogger(AudienceTargetTypeServiceImpl.class);

	@Autowired
	private AudienceTargetTypeRepository audienceTargetTypeRepository;

	@Transactional
	public List<AudienceTargetType> getAllAudienceTargetType() throws IllegalAccessException, InvocationTargetException {
		List<AudienceTargetType> audTargetTypeResponse = null;
		try {
			audTargetTypeResponse = audienceTargetTypeRepository.findByDeletedFalseOrderByNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive AudienceTargetType information",ex);
		}
		return audTargetTypeResponse;
	}
	@Transactional
	public AudienceTargetType addAudienceTargetType(AudienceTargetType audienceTargetType)
			throws IllegalAccessException, InvocationTargetException, Exception {
		LOGGER.info("AudienceTargetType in service " + audienceTargetType.getName());
		return audienceTargetTypeRepository.save(audienceTargetType);
	}

	@Transactional
	public AudienceTargetType updateAudienceTargetType(AudienceTargetType audienceTargetType)
			throws IllegalAccessException, InvocationTargetException, Exception {

		LOGGER.info("AudienceTargetType info " + audienceTargetType.getTargetTypeId());
		if(audienceTargetType.getTargetTypeId()!=null && Boolean.FALSE.equals(audienceTargetType.isDeleted())){
			audienceTargetTypeRepository.save(audienceTargetType);
			return audienceTargetType;
			
		}throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"AudienceTargetType with is {" + audienceTargetType.getTargetTypeId() + "} does not exist.");

	}

	@Transactional
	public AudienceTargetType getAudienceTargetType(Long id)
			throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		if (audienceTargetTypeRepository.findById(id) != null) {
			return audienceTargetTypeRepository.findById(id);
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"AudienceTargetType with is {" + id + "} does not exist.");
	}

	@Transactional
	@Override
	public void deleteAudienceTargetType(Long targetId)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		AudienceTargetType audienceTargetType = audienceTargetTypeRepository.findById(targetId);
		if (audienceTargetType != null && Boolean.FALSE.equals(audienceTargetType.isDeleted())) {
			audienceTargetTypeRepository.softDelete(targetId);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"AudienceTargetType with Id {" + audienceTargetType.getTargetTypeId() + "} does not exist or already deleted.");
		}
	}

}
