package com.oms.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.NewCreative;
import com.oms.repository.NewCreativeRepository;
import com.oms.service.NewCreativeService;

@Service
public class NewCreativeServiceImpl implements NewCreativeService {
	final static Logger logger = Logger.getLogger(NewCreativeServiceImpl.class);
	
	@Autowired
	private NewCreativeRepository newCreativeRepository;
	
	@Override
	@Transactional
	public NewCreative addCreative(NewCreative creative,Long userId) 
			throws IllegalAccessException, InvocationTargetException, Exception{
		
		try{
			creative.setCreatedBy(userId);			
			creative.setCreated(new Date());
			creative.setUpdatedBy(userId);
			newCreativeRepository.save(creative);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return creative;
		
	}

	@Override
	public List<NewCreative> getAllNewCreatives() {
		List<NewCreative> creative1List = null;
		try {
			creative1List = newCreativeRepository.findByDeletedFalseOrderByNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Unable to retrive Proposal information", ex);
		}
		return creative1List;
	}
	
	@Override
	public NewCreative getNewCreativeById(Long id) throws OMSSystemException {
		try {
			NewCreative newCreative = newCreativeRepository.findByNewCreativeId(id);			
			return newCreative;
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Proposal with id {" + id + "} does not exist.", ex);
		}

	}
	
	
	
}
