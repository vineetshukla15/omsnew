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
import com.oms.model.SeasonalDiscount;
import com.oms.repository.SeasonalDiscountRepository;
import com.oms.service.SeasonalDiscountService;

/**
 * 
 * @author vikas.parashar
 *
 */
@Service
public class SeasonalDiscountServiceImpl implements SeasonalDiscountService {

	final static Logger LOGGER = Logger.getLogger(SeasonalDiscountServiceImpl.class);

	@Autowired
	private SeasonalDiscountRepository seasonalDiscountRepository;

	@Transactional
	public List<SeasonalDiscount> getAllSeasonalDiscount() {
		List<SeasonalDiscount> seasonalDiscounts = null;
		try {
			seasonalDiscounts = seasonalDiscountRepository.findByDeletedFalse();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive SeasonalDiscount information",ex);
		}
		return seasonalDiscounts;
	}

	@Transactional
	public SeasonalDiscount addSeasonalDiscount(SeasonalDiscount seasonalDiscount)
			throws IllegalAccessException, InvocationTargetException, Exception, OMSSystemException {
		LOGGER.info("SeasonalDiscount save end date " + seasonalDiscount.getEndDate());
		try{
			return seasonalDiscountRepository.save(seasonalDiscount);
		}catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Transactional
	public SeasonalDiscount updateSeasonalDiscount(SeasonalDiscount seasonalDiscount)
			throws IllegalAccessException, InvocationTargetException, Exception, OMSSystemException {

		LOGGER.info("Seasonal Discount info " + seasonalDiscount.getId());
		if(seasonalDiscount.getId()!=null && Boolean.FALSE.equals(seasonalDiscount.isDeleted())){
			seasonalDiscountRepository.save(seasonalDiscount);
			return seasonalDiscount;
			
		}throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
				"Seasonal Discount with {" + seasonalDiscount.getId() + "} does not exist.");
	}

	@Transactional
	public SeasonalDiscount getSeasonalDiscount(Long id)
			throws OMSSystemException{
		if (seasonalDiscountRepository.findById(id) != null) {
			return seasonalDiscountRepository.findById(id);
		}
		throw  new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,"Seasonal Discount with is {" + id + "} does not exist.");
	}

	@Transactional
	@Override
	public void deleteSeasonalDiscount(Long seasonalDiscountId)	throws OMSSystemException{
		SeasonalDiscount seasonalDiscount = seasonalDiscountRepository.findById(seasonalDiscountId);
		try{
		if (seasonalDiscount != null && Boolean.FALSE.equals(seasonalDiscount.isDeleted())) {
			seasonalDiscountRepository.softDelete(seasonalDiscountId);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,"Seasonal Discount with is {" + seasonalDiscountId + "} does not exist or already deleted.");
		}
		}catch(OMSSystemException oex){
			throw oex;
		}
		catch(Exception ex){
			throw new OMSSystemException(Status.FAILED.name(),HttpStatus.CONFLICT,"System error occured",ex);
		}
	}

}
