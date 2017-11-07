package com.oms.controller;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.commons.utils.Convertor;
import com.oms.exceptions.OMSSystemException;
import com.oms.model.RateCard;
import com.oms.service.RateCardService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.RateCardVO;
import com.oms.viewobjects.SearchRequestVO;

@RestController
@RequestMapping(value = "/rateCard",produces = MediaType.APPLICATION_JSON_VALUE)
public class RateCardController extends BaseController {
	final static Logger LOGGER = Logger.getLogger(RateCardController.class);

	@Autowired
	private RateCardService rateCardService;

	@RequestMapping( method = RequestMethod.GET)
	public  Collection<RateCardVO> getAllRateCard() {
		List<RateCard> rateCards = rateCardService.getAllRateCard();
		return Convertor.convert(rateCards, Convertor::toRateCardVO);
	}

	@RequestMapping(value = "/{rateCardId}", method = RequestMethod.GET)
	public  RateCardVO getRateCard(@PathVariable Long rateCardId) throws OMSSystemException {
		return Convertor.toRateCardVO(rateCardService.getRateCard(rateCardId));
	}

	@RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.ADD_RATE_CARD)
	public  RateCard addNewRateCard(@RequestBody RateCard rateCardDTO)
			throws OMSSystemException {
		LOGGER.info("rateCard Section" + rateCardDTO.getSectionsName());
		rateCardDTO.setCreatedBy(findUserIDFromSecurityContext());
		rateCardDTO.setUpdatedBy(findUserIDFromSecurityContext());
		return rateCardService.addRateCard(rateCardDTO);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.UPDATE_RATE_CARD)
	public  RateCard updateRateCard(@RequestBody RateCard rateCardDTO)
			throws OMSSystemException {
		LOGGER.info("rate Card info " + rateCardDTO.getBasePrice());
		rateCardDTO.setUpdatedBy(findUserIDFromSecurityContext());
		return rateCardService.updateRateCard(rateCardDTO);
	}
	
	@RequestMapping(value = "/{rateCardId}", method = RequestMethod.DELETE)
	@Auditable(actionType = AuditingActionType.DELETE_RATE_CARD)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteRateCard(@PathVariable("rateCardId") Long rateCardId)
			throws OMSSystemException {
		rateCardService.deleteRateCard(rateCardId);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public PaginationResponseVO<RateCardVO> searchProposal(@RequestBody SearchRequestVO searchRequest ){
		return Convertor.toPaginationVO(rateCardService.searchRateCard(searchRequest), Convertor::toRateCardVO);
	}
	
}