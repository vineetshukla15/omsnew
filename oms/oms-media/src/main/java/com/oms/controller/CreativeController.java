package com.oms.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.tavant.oms.logging.annotation.Loggable;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.exceptions.OMSSystemException;
import com.oms.model.Creative;
import com.oms.service.CreativeService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@RestController
@RequestMapping(value="/creative",produces = MediaType.APPLICATION_JSON_VALUE)
public class CreativeController extends BaseController {

	final static Logger logger = Logger.getLogger(CreativeController.class);

	@Autowired
	private CreativeService creativeService;

	@RequestMapping( method = RequestMethod.POST)
	@Auditable(actionType = AuditingActionType.ADD_NEW_CREATIVE)
	public @ResponseBody Creative addNewCreative(@RequestBody Creative creativeDTO) throws OMSSystemException {
		creativeDTO.setCreatedBy(findUserIDFromSecurityContext());
		creativeDTO.setCreated(new Date());
		creativeDTO.setUpdatedBy(findUserIDFromSecurityContext());
		creativeDTO.setUpdated(new Date());
		return creativeService.addCreative(creativeDTO);
	}
@Loggable
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(code=HttpStatus.OK)
	public List<Creative> getCreative() {
		List<Creative> creatives = creativeService.getAllCreatives();
		logger.info("List of Creatives " + creatives.toString());
		return creatives;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public PaginationResponseVO<Creative> searchCreatives(@RequestBody SearchRequestVO searchRequest ){
		return creativeService.searchCreatives(searchRequest);
	}

	@RequestMapping(value = "/name/{creativeName}", method = RequestMethod.GET)
	public @ResponseBody List<Creative> getCreativesByName(@PathVariable String creativeName)
			throws OMSSystemException {
		return creativeService.getCreativesByName(creativeName);

	}

	@RequestMapping(value = "/{creativeID}", method = RequestMethod.GET)
	public @ResponseBody Creative getCreativeById(@PathVariable Long creativeID) throws OMSSystemException {
		return creativeService.getCreativeById(creativeID);

	}

	@RequestMapping(method = RequestMethod.PUT)
	@Auditable(actionType = AuditingActionType.UPDATE_CREATIVE)
	public @ResponseBody Creative updateCreative(@RequestBody Creative creative) throws OMSSystemException {
		
		creative.setUpdated(new Date());
		creative.setUpdatedBy(findUserIDFromSecurityContext());
		return creativeService.updateCreative(creative);
	}

	@RequestMapping(value = "/{creativeId}" ,method = RequestMethod.DELETE)
	@Auditable(actionType = AuditingActionType.DELETE_CREATIVE)
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void removeCreative(@PathVariable("creativeId") Long creativeId) throws OMSSystemException {
		creativeService.deleteCreative(creativeId);
	}
}