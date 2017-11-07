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
import com.oms.model.Premium;
import com.oms.repository.PremiumRepository;
import com.oms.service.PremiumService;

/**
 * 
 * @author vikas.parashar
 *
 */
@Service
public class PremiumServiceImpl implements PremiumService {

	final static Logger LOGGER = Logger.getLogger(PremiumServiceImpl.class);

	@Autowired
	private PremiumRepository premiumRepository;

	@Transactional
	public List<Premium> getAllPremium() throws IllegalAccessException, InvocationTargetException {
		List<Premium> premiumList = null;
		try {
			premiumList = premiumRepository.findAll();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive Premium information",ex);
		}
		return premiumList;
	}

	@Transactional
	public Premium addPremium(Premium premium) throws IllegalAccessException, InvocationTargetException, Exception,OMSSystemException {
		LOGGER.info("Premium Profile add target type id " + premium.getTargetTypeId());
		try{
			return premiumRepository.save(premium);
		}catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Transactional
	public Premium updatePremium(Premium premium) throws IllegalAccessException, InvocationTargetException, Exception,OMSSystemException {

		LOGGER.info("Premium info update with id " + premium.getPremiumId());
		if(premium.getPremiumId()!=null && Boolean.FALSE.equals(premium.isDeleted())){
			premiumRepository.save(premium);
			return premium;
		}throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"Premium with is {" + premium.getPremiumId() + "} does not exist.");
		
	}

	@Transactional
	public Premium getPremium(Long id)
			throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		if (premiumRepository.findById(id) != null) {
			return premiumRepository.findById(id);
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"Premium with is {" + id + "} does not exist.");	
	}

	@Transactional
	@Override
	public void deletePremium(Long premiumId)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		Premium premium = premiumRepository.findById(premiumId);
		if (premium != null && Boolean.FALSE.equals(premium.isDeleted())) {
			premiumRepository.softDelete(premiumId);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Premium with is {" + premiumId + "} does not exist or already deleted.");
		}
	}
}
