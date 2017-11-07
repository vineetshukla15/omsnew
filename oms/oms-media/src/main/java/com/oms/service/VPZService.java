package com.oms.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.oms.exceptions.VPZException;
import com.oms.model.CreativeAsset;
import com.oms.viewobjects.CreativeAssetDTO;
import com.oms.viewobjects.vpz.VPZAD;
import com.oms.viewobjects.vpz.VPZAsset;
import com.oms.viewobjects.vpz.VPZAssetDTO;
import com.oms.viewobjects.vpz.VPZCampaign;
import com.oms.viewobjects.vpz.VPZCampaigns;
import com.oms.viewobjects.vpz.VPZCreative;
import com.oms.viewobjects.vpz.VPZGoal;
import com.oms.viewobjects.vpz.VPZGoalRule;
import com.oms.viewobjects.vpz.VPZGoals;
import com.oms.viewobjects.vpz.VPZGoalsWithAD;

public interface VPZService {
	public VPZCampaigns getCampaigns() throws VPZException;
	public VPZCampaigns getCampaignByCampaignID(String campaignid) throws VPZException;
	public VPZGoals getGoals(String campaignid) throws VPZException;
	public VPZAD[]  getADs() throws VPZException;
	public VPZAD getAD(String campaignid) throws VPZException;
	public String createCampaign(VPZCampaign vpzCampaign) throws VPZException;
	public String updateCampaign(VPZCampaign vpzCampaign) throws VPZException;
	public VPZCampaign addGoalToCampaign(VPZGoal vpzGoal, String campaignId) throws VPZException;
	public String createGoal(VPZGoal vpzGoal) throws VPZException;
	VPZAD createAd(VPZAD vpzAD) throws VPZException;
	List<VPZAD> getADs(String goalId) throws VPZException;
	VPZGoalsWithAD getGoalsWithADs(String campaignid) throws VPZException;
	String uploadAsset(VPZAsset vpzAsset) throws IOException, VPZException;
	public String getReport(String reportid) throws VPZException;
	public String uploadCreative(VPZCreative vpzCreative) throws VPZException;
	public CreativeAssetDTO uploadCreative(String name,String type, String insertionpoint, String clickDestinationUri,
			String vastUri, boolean vpaidStrict, boolean vpaidCountdown,MultipartFile file) 
			throws IOException, VPZException;
	public List<CreativeAsset>  getCreativeAssets() throws VPZException;
	public void deleteCampaigns(String searchText) throws VPZException;
	public VPZAssetDTO uploadCreativeFile(MultipartFile uploadfile) throws IOException, VPZException;
	public CreativeAssetDTO uploadCreative(String name, String type, String insertionpoint, String clickDestinationUri,
			String vastUri, boolean vpaidStrict, boolean vpaidCountdown, String assetid) throws VPZException;
	public String updateGoal(VPZGoal vpzGoal) throws VPZException;
	CreativeAssetDTO uploadCreative(CreativeAssetDTO creativeAssetDTO) throws VPZException;
	public String createGoalRule(VPZGoalRule vpzGoalRule) throws VPZException;
}
