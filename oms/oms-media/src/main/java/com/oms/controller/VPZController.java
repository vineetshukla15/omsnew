package com.oms.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.VPZException;
import com.oms.model.CreativeAsset;
import com.oms.service.OMSOrderService;
import com.oms.service.VPZService;
import com.oms.viewobjects.CreativeAssetDTO;
import com.oms.viewobjects.vpz.VPZAD;
import com.oms.viewobjects.vpz.VPZAsset;
import com.oms.viewobjects.vpz.VPZAssetDTO;
import com.oms.viewobjects.vpz.VPZCampaigns;
import com.oms.viewobjects.vpz.VPZGoals;
import com.oms.viewobjects.vpz.VPZGoalsWithAD;

@RestController
public class VPZController {

	@Autowired
	private VPZService vpzService;
	
	@Autowired
	private OMSOrderService omsOrderService;

	@RequestMapping(value = "/vpzcampaigns", produces = MediaType.APPLICATION_JSON_VALUE)
	public VPZCampaigns getCampaigns() throws VPZException {
		return vpzService.getCampaigns();
	}

	@RequestMapping(value = "/vpzcampaign/{campaignid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public VPZCampaigns getCampaignByCampaignID(@PathVariable String campaignid) throws VPZException {
		return vpzService.getCampaignByCampaignID(campaignid);
	}

	@RequestMapping(value = "/vpzgoal/{campaignid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public VPZGoals getGoalByCampaignID(@PathVariable String campaignid) throws VPZException {
		return vpzService.getGoals(campaignid);
	}

	@RequestMapping(value = "/vpzads", produces = MediaType.APPLICATION_JSON_VALUE)
	public VPZAD[] getADs() throws VPZException {
		return vpzService.getADs();
	}

	@RequestMapping(value = "/vpzgoalswithads/{campaignid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public VPZGoalsWithAD getVPZGoalsWithADs(@PathVariable String campaignid) throws VPZException {
		return vpzService.getGoalsWithADs(campaignid);
	}

	@RequestMapping(value = "/vpzads/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public VPZAD getADByADID(@PathVariable String id) throws VPZException {
		return vpzService.getAD(id);
	}

	@RequestMapping(value = "/vpzasset", produces = MediaType.TEXT_PLAIN_VALUE, method = RequestMethod.POST)
	public String uploadAsset(@RequestBody VPZAsset vpzAsset) throws IOException, VPZException {
		return vpzService.uploadAsset(vpzAsset);
	}

	/*
	 * @RequestMapping(value = "/vpzCreative",produces =
	 * MediaType.TEXT_PLAIN_VALUE, method = RequestMethod.POST) public String
	 * uploadCreative(@RequestBody VPZCreative vpzCreative) throws IOException {
	 * return vpzService.uploadCreative(vpzCreative); }
	 */

	@RequestMapping(value = "/cretiveasset", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody CreativeAssetDTO uploadCreative(@RequestParam("name") String name,@RequestParam("type") String type,
			@RequestParam("insertionpoint") String insertionpoint,
			@RequestParam("clickDestinationUri") String clickDestinationUri, @RequestParam("vastUri") String vastUri,
			@RequestParam("vpaidStrict") boolean vpaidStrict, @RequestParam("vpaidCountdown") boolean vpaidCountdown,
			@RequestParam("file") MultipartFile uploadfile)
			throws OMSSystemException, IOException, VPZException {
		return vpzService.uploadCreative(name, type,insertionpoint,clickDestinationUri, vastUri, vpaidStrict, vpaidCountdown, uploadfile);
	}
	
	@RequestMapping(value = "/cretiveassetcreate", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody CreativeAssetDTO uploadCreative(@RequestParam("name") String name,@RequestParam("type") String type,
			@RequestParam("insertionpoint") String insertionpoint,
			@RequestParam("clickDestinationUri") String clickDestinationUri, @RequestParam("vastUri") String vastUri,
			@RequestParam("vpaidStrict") boolean vpaidStrict, @RequestParam("vpaidCountdown") boolean vpaidCountdown,
			@RequestParam("assetid") String assetid)
			throws OMSSystemException, IOException, VPZException {
		return vpzService.uploadCreative(name, type,insertionpoint,clickDestinationUri, vastUri, vpaidStrict, vpaidCountdown, assetid);
	}
	
	
	@RequestMapping(value = "/creativeassetjson", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody CreativeAssetDTO uploadCreative(@RequestBody CreativeAssetDTO creativeAssetDTO)
			throws OMSSystemException, IOException, VPZException {
		return vpzService.uploadCreative(creativeAssetDTO);
	}
	
	@RequestMapping(value = "/uploadcretiveasset", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody VPZAssetDTO uploadCreativeFile(
@RequestPart("file") MultipartFile uploadfile)
			throws OMSSystemException, IOException, VPZException {
		return vpzService.uploadCreativeFile(uploadfile);
	}
	


	@RequestMapping(value = "/campaignreport/{reportid}", produces = MediaType.TEXT_PLAIN_VALUE, method = RequestMethod.GET)
	public String uploadAsset(@PathVariable String reportid) throws IOException, VPZException {
		return vpzService.getReport(reportid);
	}
	
	@RequestMapping(value = "/cretiveasset", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<CreativeAsset>  getCreativeAssets() throws VPZException {
		return vpzService.getCreativeAssets();
	}
	
	@RequestMapping(value = "/deleteallcampaigns/{searchtext}", method = RequestMethod.DELETE)
	public void  deleteCampaigns(@PathVariable String searchtext) throws VPZException {
		vpzService.deleteCampaigns(searchtext); 
	}
	
	@RequestMapping(value = "/createOrderFromProposal/{proposalid}", method = RequestMethod.GET)
	public void  createOrderFromProposal(@PathVariable long proposalid) {
		omsOrderService.createOrder(proposalid);
	}

}
