package com.oms.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.commons.utils.SearchUtil;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.RateCard;
import com.oms.model.specification.RateCardProfileSpecification;
import com.oms.repository.RateCardRepository;
import com.oms.service.RateCardService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;


@Service
public class RateCardServiceImpl implements RateCardService {

	final static Logger LOGGER = Logger.getLogger(RateCardServiceImpl.class);
	
	
	@Autowired
	private RateCardRepository rateCardRepository;


	@Transactional 
	public List<RateCard> getAllRateCard() {
		List<RateCard> rateCardProfileList = null;
		try {
			rateCardProfileList = rateCardRepository.findByDeletedFalseOrderBySectionsNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive RateCard information",ex);
		}
		return rateCardProfileList;
	}

	@Transactional
	public RateCard addRateCard(RateCard rateCardDTO)	throws OMSSystemException {
		 return rateCardRepository.save(rateCardDTO);
	}

	@Transactional
	public RateCard updateRateCard(RateCard rateCardDTO) throws OMSSystemException  {
		if(rateCardDTO.getRateCardId() !=null && Boolean.FALSE.equals(rateCardDTO.isDeleted())){
		rateCardRepository.save(rateCardDTO);
		return rateCardDTO;
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
				"Rate Card with {" + rateCardDTO.getRateCardId() + "} does not exist.");
	}

	@Transactional
	public RateCard getRateCard(Long id)
			throws OMSSystemException {
		if(rateCardRepository.findById(id) != null){
			return rateCardRepository.findById(id);
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
				"Rate Card with {" + id + "} does not exist.");
		
	}

	@Transactional
	@Override
	public void deleteRateCard(Long rateCardId) throws OMSSystemException {
		RateCard rateCard = rateCardRepository.findById(rateCardId);
		if(rateCard !=null && Boolean.FALSE.equals(rateCard.isDeleted())){
			rateCardRepository.softDelete(rateCardId);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Rate Card with {" + rateCardId + "} does not exist or already deleted.");
		}
	}

	@Override
	public PaginationResponseVO<RateCard> searchRateCard(SearchRequestVO searchRequest) {
		Page<RateCard> pageResponse = null;
		PaginationResponseVO<RateCard> response = null; 
		try {
			pageResponse = rateCardRepository.findAll(RateCardProfileSpecification.getRateCardSpecification(searchRequest),SearchUtil.getPageable(searchRequest));
			response= new PaginationResponseVO<RateCard>(pageResponse.getTotalElements(), searchRequest.getDraw(), pageResponse.getTotalElements(), pageResponse.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
