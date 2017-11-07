package com.oms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.RateCard;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public interface RateCardService {

	public List<RateCard> getAllRateCard() ;
	
	public RateCard getRateCard(Long id) throws OMSSystemException;

	public RateCard addRateCard(RateCard rateCardDTO) throws OMSSystemException;;

	public RateCard updateRateCard(RateCard rateCardDTO) throws OMSSystemException;;

	public void deleteRateCard(Long rateCardId) throws OMSSystemException;

	public PaginationResponseVO<RateCard> searchRateCard(SearchRequestVO searchRequest);;

}
