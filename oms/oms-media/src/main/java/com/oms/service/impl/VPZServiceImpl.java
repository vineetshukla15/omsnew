package com.oms.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.oms.exceptions.VPZException;
import com.oms.model.CreativeAsset;
import com.oms.repository.CreativeAssetRepository;
import com.oms.service.VPZService;
import com.oms.viewobjects.CreativeAssetDTO;
import com.oms.viewobjects.vpz.VPZAD;
import com.oms.viewobjects.vpz.VPZAsset;
import com.oms.viewobjects.vpz.VPZAssetDTO;
import com.oms.viewobjects.vpz.VPZCampaign;
import com.oms.viewobjects.vpz.VPZCampaigns;
import com.oms.viewobjects.vpz.VPZCreative;
import com.oms.viewobjects.vpz.VPZGoal;
import com.oms.viewobjects.vpz.VPZGoalRule;
import com.oms.viewobjects.vpz.VPZGoalWithAD;
import com.oms.viewobjects.vpz.VPZGoals;
import com.oms.viewobjects.vpz.VPZGoalsWithAD;

@Service
public class VPZServiceImpl implements VPZService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${ooyala.vpz.username}")
	private String username;

	@Value("${ooyala.vpz.password}")
	private String password;

	@Value("${ooyala.vpz.apikey}")
	private String apiKey;

	@Value("${ooyala.vpz.api.url}")
	private String url;

	@Value("${ooyala.vpz.api.secure.url}")
	private String secureurl;

	@Autowired
	private CreativeAssetRepository creativeAssetRepository;

	private String getVPAuthToken() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("username", username);
		map.add("password", password);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url + "/login_process", request, String.class);
		String apiKey = response.getHeaders().getFirst("x-vp-auth");
		return apiKey;

	}

	public VPZCampaigns getCampaigns() throws VPZException {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("X-VP-AUTH", getVPAuthToken());

		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		System.out.println("request headers: " + entity.getHeaders());
		ResponseEntity<VPZCampaigns> campaigns = restTemplate.exchange(url + "/api/1.0/campaign", HttpMethod.GET,
				entity, VPZCampaigns.class);
		System.out.println(campaigns);
		return campaigns.getBody();
	}

	public VPZCampaigns getCampaignByCampaignID(String campaignid) throws VPZException {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("X-VP-AUTH", getVPAuthToken());

		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		System.out.println("request headers: " + entity.getHeaders());
		ResponseEntity<VPZCampaigns> campaigns = restTemplate.exchange(
				url + "/api/1.0/campaign/by_campaign_id;id=" + campaignid, HttpMethod.GET, entity, VPZCampaigns.class);
		System.out.println(campaigns);
		return campaigns.getBody();
	}

	public VPZGoals getGoals(String campaignid) throws VPZException {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("X-VP-AUTH", getVPAuthToken());

		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		System.out.println("request headers: " + entity.getHeaders());
		ResponseEntity<VPZGoals> vpzGoals = restTemplate.exchange(url + "/api/1.0/goal/by_campaign_id;id=" + campaignid,
				HttpMethod.GET, entity, VPZGoals.class);
		System.out.println(vpzGoals);
		return vpzGoals.getBody();
	}

	@Override
	public VPZGoalsWithAD getGoalsWithADs(String campaignid) throws VPZException {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("X-VP-AUTH", getVPAuthToken());

		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		System.out.println("request headers: " + entity.getHeaders());
		ResponseEntity<VPZGoalsWithAD> vpzGoals = restTemplate.exchange(
				url + "/api/1.0/goal/by_campaign_id;id=" + campaignid, HttpMethod.GET, entity, VPZGoalsWithAD.class);
		for (VPZGoalWithAD vozGoal : vpzGoals.getBody().getGoalBean()) {
			String goalId = vozGoal.getId();
			vozGoal.setVpzADs(getADs(goalId));

		}
		return vpzGoals.getBody();
	}

	public VPZAD getAD(String id) throws VPZException {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("x-o-api-key", apiKey);
		headers.add("X-VP-AUTH", getVPAuthToken());

		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		System.out.println("request headers: " + entity.getHeaders());
		ResponseEntity<VPZAD> vpzGoals = restTemplate.exchange(secureurl + "/v1/ads/" + id, HttpMethod.GET, entity,
				VPZAD.class);
		System.out.println(vpzGoals);
		return vpzGoals.getBody();
	}

	@Override
	public VPZAD[] getADs() throws VPZException {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("x-o-api-key", apiKey);
		headers.add("X-VP-AUTH", getVPAuthToken());

		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		System.out.println("request headers: " + entity.getHeaders());
		ResponseEntity<VPZAD[]> vpzAds = restTemplate.exchange(secureurl + "/v1/ads", HttpMethod.GET, entity,
				VPZAD[].class);
		System.out.println(vpzAds);
		return vpzAds.getBody();
	}

	@Override
	public List<VPZAD> getADs(String goalId) throws VPZException {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("x-o-api-key", apiKey);
		headers.add("X-VP-AUTH", getVPAuthToken());

		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		System.out.println("request headers: " + entity.getHeaders());
		ResponseEntity<VPZAD[]> vpzAds = restTemplate.exchange(secureurl + "/v1/ads", HttpMethod.GET, entity,
				VPZAD[].class);
		List<VPZAD> goalAd = new ArrayList<VPZAD>();

		for (VPZAD vpzAd : vpzAds.getBody()) {
			if (goalId.equalsIgnoreCase(vpzAd.getGoalId())) {
				goalAd.add(vpzAd);
			}
		}
		System.out.println(vpzAds);
		return goalAd;
	}

	@Override
	public String createCampaign(VPZCampaign vpzCampaign) throws VPZException {

		ResponseEntity<String> vpzCampaignResponse = null;
		boolean responseReceived = false;

		int trial = 5;
		while (!responseReceived && trial > 0) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-VP-AUTH", getVPAuthToken());
			headers.setContentType(MediaType.APPLICATION_XML);

			HttpEntity<?> entity = new HttpEntity<Object>(vpzCampaign, headers);

			System.out.println("request headers: " + entity.getHeaders());
			try {
				vpzCampaignResponse = restTemplate.exchange(url + "/api/1.0/campaign", HttpMethod.POST, entity,
						String.class);
				responseReceived = true;
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					System.out.println("Un Authorised error occured");
					responseReceived = false;
				}
			} catch (Exception e) {
				throw new VPZException("Error occured while creating campaign"+vpzCampaign.getName(), e);
			} finally {
				trial--;
			}
		}
		return vpzCampaignResponse.getBody();
	}

	@Override
	public String updateCampaign(VPZCampaign vpzCampaign) throws VPZException {

		ResponseEntity<String> vpzCampaignResponse = null;
		boolean responseReceived = false;

		int trial = 5;
		while (!responseReceived && trial > 0) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-VP-AUTH", getVPAuthToken());
			headers.setContentType(MediaType.APPLICATION_XML);

			HttpEntity<?> entity = new HttpEntity<Object>(vpzCampaign, headers);

			System.out.println("request headers: " + entity.getHeaders());
			try {
				vpzCampaignResponse = restTemplate.exchange(
						url + "/api/1.0/campaign/by_campaign_id;id=" + vpzCampaign.getId(), HttpMethod.PUT, entity,
						String.class);
				responseReceived = true;
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					System.out.println("Un Authorised error occured");
					responseReceived = false;
				}
			} catch (Exception e) {
				throw new VPZException("Error occured while updating campaign"+vpzCampaign.getName(), e);
			} finally {
				trial--;
			}
		}
		return vpzCampaignResponse.getBody();
	}

	@Override
	public VPZCampaign addGoalToCampaign(VPZGoal vpzGoal, String campaignId) {
		return null;
	}

	@Override
	public String createGoal(VPZGoal vpzGoal) throws VPZException {

		ResponseEntity<String> vpzGoalResponse = null;
		boolean responseReceived = false;

		int trial = 5;
		while (!responseReceived && trial > 0) {

			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("x-o-api-key", apiKey);
			headers.add("X-VP-AUTH", getVPAuthToken());
			headers.add("Content-Type", "application/xml");

			HttpEntity<?> entity = new HttpEntity<Object>(vpzGoal, headers);

			System.out.println("request headers: " + entity.getHeaders());
			System.out.println("Trial : " + trial);
			try {
				vpzGoalResponse = restTemplate.exchange(url + "/api/1.0/goal", HttpMethod.POST, entity, String.class);
				responseReceived = true;
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					System.out.println("Un Authorised error occured");
					responseReceived = false;
				}
			} catch (Exception e) {
				throw new VPZException("Error occured while creating goal "+vpzGoal.getName(), e);
			} finally {
				trial--;
			}
		}
		return vpzGoalResponse.getBody();
	}

	@Override
	public String updateGoal(VPZGoal vpzGoal) throws VPZException {

		ResponseEntity<String> vpzGoalResponse = null;
		boolean responseReceived = false;

		int trial = 5;
		while (!responseReceived && trial > 0) {

			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("x-o-api-key", apiKey);
			headers.add("X-VP-AUTH", getVPAuthToken());
			headers.add("Content-Type", "application/xml");

			HttpEntity<?> entity = new HttpEntity<Object>(vpzGoal, headers);

			System.out.println("request headers: " + entity.getHeaders());
			System.out.println("Trial : " + trial);
			try {
				vpzGoalResponse = restTemplate.exchange(url + "/api/1.0/goal/by_goal_id;id=" + vpzGoal.getId(),
						HttpMethod.PUT, entity, String.class);
				responseReceived = true;
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					System.out.println("Un Authorised error occured");
					responseReceived = false;
				}
			} catch (Exception e) {
				throw new VPZException("Error occured while updating goal "+vpzGoal.getName(), e);
			} finally {
				trial--;
			}
		}
		return vpzGoalResponse.getBody();
	}

	public String updateGoalRule(String goalId) throws VPZException {

		ResponseEntity<String> vpzGoalRuleResponse = null;
		boolean responseReceived = false;

		int trial = 5;
		while (!responseReceived && trial > 0) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-VP-AUTH", getVPAuthToken());
			headers.setContentType(MediaType.APPLICATION_XML);

			HttpEntity<?> entity = new HttpEntity<Object>(goalId, headers);

			System.out.println("request headers: " + entity.getHeaders());
			try {
				vpzGoalRuleResponse = restTemplate.exchange(url + "/api/1.0/campaign", HttpMethod.PUT, entity,
						String.class);
				responseReceived = true;
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					System.out.println("Un Authorised error occured");
					responseReceived = false;
				}
			} catch (Exception e) {
				throw new VPZException("Error occured while updating goal rule with goal id "+goalId, e);
			} finally {
				trial--;
			}
		}
		return vpzGoalRuleResponse.getBody();
	}

	@Override
	public VPZAD createAd(VPZAD vpzAD) throws VPZException {
		ResponseEntity<VPZAD> vpzAds = null;

		int trial = 5;
		boolean responseReceived = false;
		while (!responseReceived && trial > 0) {
			System.out.println("Trial : " + trial);
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("x-o-api-key", apiKey);
			headers.add("X-VP-AUTH", getVPAuthToken());
			headers.add("Content-Type", "application/json");

			HttpEntity<?> entity = new HttpEntity<Object>(vpzAD, headers);

			System.out.println("request headers: " + entity.getHeaders());

			try {
				vpzAds = restTemplate.exchange(secureurl + "/v1/ads", HttpMethod.POST, entity, VPZAD.class);
				responseReceived = true;
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					System.out.println("Un Authorised error occured");
				}
			} catch (Exception e) {
				throw new VPZException("Error occured while crating ad "+vpzAD.getName(), e);
			} finally {
				trial--;
			}

		}

		return vpzAds.getBody();
	}

	@Override
	public String uploadAsset(VPZAsset vpzAsset) throws IOException, VPZException {

		String assetId = "";
		int trial = 5;
		boolean responseReceived = false;
		while (!responseReceived && trial > 0) {
			System.out.println("Trial : " + trial);
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("x-o-api-key", apiKey);
			headers.add("X-VP-AUTH", getVPAuthToken());
			headers.add("Content-Type", "application/octet-stream");

			InputStream in = new FileSystemResource(vpzAsset.getFileName()).getInputStream();
			HttpEntity<byte[]> entity = new HttpEntity<>(IOUtils.toByteArray(in), headers);
			System.out.println("request headers: " + entity.getHeaders());
			try {
				ResponseEntity<String> vpzAssetLocation = restTemplate.exchange(
						secureurl + "/v1/assets/video?fileName='" + vpzAsset.getFileName(), HttpMethod.POST, entity,
						String.class);
				String location = vpzAssetLocation.getHeaders().getValuesAsList("location").get(0);
				assetId = location.substring(location.indexOf("v1/assets/") + "v1/assets/".length());
				responseReceived = true;
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					System.out.println("Un Authorised error occured");
					responseReceived = false;
				}
			} catch (Exception e) {
				throw new VPZException("Error occured while updating asset ad with asset id"+vpzAsset.getId(), e);
			} finally {
				trial--;
			}
		}
		return assetId;
	}

	@Override
	public String getReport(String reportid) throws VPZException {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("x-o-api-key", apiKey);
		headers.add("X-VP-AUTH", getVPAuthToken());
		headers.add("Accept", "application/json");
		// headers.add("Content-Type", "application/json");

		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		System.out.println("request headers: " + entity.getHeaders());
		ResponseEntity<String> reportresult = restTemplate.exchange(secureurl + "/v2/reports/" + reportid + "/result",
				HttpMethod.GET, entity, String.class);
		System.out.println(reportresult);
		return reportresult.getBody();
	}

	@Override
	public String uploadCreative(VPZCreative vpzCreative) {
		return null;
	}

	@Override
	public CreativeAssetDTO uploadCreative(String name, String type, String insertionpoint, String clickDestinationUri,
			String vastUri, boolean vpaidStrict, boolean vpaidCountdown, MultipartFile file) throws IOException, VPZException {

		byte[] bytes = file.getBytes();
		Path path = Paths.get(file.getOriginalFilename());
		Files.write(path, bytes);

		VPZAsset vpzAsset = new VPZAsset();
		vpzAsset.setFileName(path.toFile().getPath());
		String assetId = uploadAsset(vpzAsset);

		CreativeAsset creativeAsset = new CreativeAsset();
		creativeAsset.setName(name);
		creativeAsset.setAssetid(assetId);
		creativeAsset.setClickDestinationUri(clickDestinationUri);
		creativeAsset.setInsertionpoint(insertionpoint);
		creativeAsset.setType(type);
		creativeAsset.setVastUri(vastUri);
		creativeAsset.setVpaidCountdown(vpaidCountdown);
		creativeAsset.setVpaidStrict(vpaidStrict);

		CreativeAsset creativeAssetSaved = creativeAssetRepository.save(creativeAsset);

		CreativeAssetDTO creativeAssetDTO = new CreativeAssetDTO();
		creativeAssetDTO.setName(creativeAssetSaved.getName());
		creativeAssetDTO.setId(creativeAssetSaved.getId());
		creativeAssetDTO.setAssetid(creativeAssetSaved.getAssetid());
		creativeAssetDTO.setClickDestinationUri(creativeAssetSaved.getClickDestinationUri());
		creativeAssetDTO.setInsertionpoint(creativeAssetSaved.getInsertionpoint());
		creativeAssetDTO.setType(creativeAssetSaved.getType());
		creativeAssetDTO.setVastUri(creativeAssetSaved.getVastUri());
		creativeAssetDTO.setVpaidCountdown(creativeAssetSaved.isVpaidCountdown());
		creativeAssetDTO.setVpaidStrict(creativeAssetSaved.isVpaidStrict());

		return creativeAssetDTO;
	}

	@Override
	public List<CreativeAsset> getCreativeAssets() {
		return creativeAssetRepository.findAll();
	}

	private void deleteCampaign(String campaignid) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-VP-AUTH", getVPAuthToken());
		headers.setContentType(MediaType.APPLICATION_XML);

		HttpEntity<?> entity = new HttpEntity<Object>(headers);

		System.out.println("request headers: " + entity.getHeaders());
		restTemplate.exchange(url + "/api/1.0/campaign/by_campaign_id;id=" + campaignid, HttpMethod.DELETE, entity,
				String.class);
	}

	@Override
	public void deleteCampaigns(String searchText) throws VPZException {
		VPZCampaigns campaigns = getCampaigns();
		for (VPZCampaign campaign : campaigns.getCampaignBean()) {
			if (campaign.getName().contains(searchText)) {
				deleteCampaign(campaign.getId());
			}
		}
	}

	@Override
	public VPZAssetDTO uploadCreativeFile(MultipartFile uploadfile) throws IOException, VPZException {
		byte[] bytes = uploadfile.getBytes();
		Path path = Paths.get(uploadfile.getOriginalFilename());
		Files.write(path, bytes);

		VPZAsset vpzAsset = new VPZAsset();
		vpzAsset.setFileName(path.toFile().getPath());
		String assetId = uploadAsset(vpzAsset);
		VPZAssetDTO vpzAssetDTO = new VPZAssetDTO();
		vpzAssetDTO.setAssetId(assetId);
		return vpzAssetDTO;
	}

	@Override
	public CreativeAssetDTO uploadCreative(String name, String type, String insertionpoint, String clickDestinationUri,
			String vastUri, boolean vpaidStrict, boolean vpaidCountdown, String assetid) {

		CreativeAsset creativeAsset = new CreativeAsset();
		creativeAsset.setName(name);
		creativeAsset.setAssetid(assetid);
		creativeAsset.setClickDestinationUri(clickDestinationUri);
		creativeAsset.setInsertionpoint(insertionpoint);
		creativeAsset.setType(type);
		creativeAsset.setVastUri(vastUri);
		creativeAsset.setVpaidCountdown(vpaidCountdown);
		creativeAsset.setVpaidStrict(vpaidStrict);

		CreativeAsset creativeAssetSaved = creativeAssetRepository.save(creativeAsset);

		CreativeAssetDTO creativeAssetDTO = new CreativeAssetDTO();
		creativeAssetDTO.setName(creativeAssetSaved.getName());
		creativeAssetDTO.setId(creativeAssetSaved.getId());
		creativeAssetDTO.setAssetid(creativeAssetSaved.getAssetid());
		creativeAssetDTO.setClickDestinationUri(creativeAssetSaved.getClickDestinationUri());
		creativeAssetDTO.setInsertionpoint(creativeAssetSaved.getInsertionpoint());
		creativeAssetDTO.setType(creativeAssetSaved.getType());
		creativeAssetDTO.setVastUri(creativeAssetSaved.getVastUri());
		creativeAssetDTO.setVpaidCountdown(creativeAssetSaved.isVpaidCountdown());
		creativeAssetDTO.setVpaidStrict(creativeAssetSaved.isVpaidStrict());

		return creativeAssetDTO;
	}

	@Override
	public CreativeAssetDTO uploadCreative(CreativeAssetDTO creativeAssetDTO) {

		CreativeAsset creativeAsset = new CreativeAsset();
		creativeAsset.setName(creativeAssetDTO.getName());
		creativeAsset.setAssetid(creativeAssetDTO.getAssetid());
		creativeAsset.setClickDestinationUri(creativeAssetDTO.getClickDestinationUri());
		creativeAsset.setInsertionpoint(creativeAssetDTO.getInsertionpoint());
		creativeAsset.setType(creativeAssetDTO.getType());
		creativeAsset.setVastUri(creativeAssetDTO.getVastUri());
		creativeAsset.setVpaidCountdown(creativeAssetDTO.isVpaidCountdown());
		creativeAsset.setVpaidStrict(creativeAssetDTO.isVpaidStrict());

		CreativeAsset creativeAssetSaved = creativeAssetRepository.save(creativeAsset);

		CreativeAssetDTO creativeAssetDTOSaved = new CreativeAssetDTO();
		creativeAssetDTOSaved.setName(creativeAssetSaved.getName());
		creativeAssetDTOSaved.setId(creativeAssetSaved.getId());
		creativeAssetDTOSaved.setAssetid(creativeAssetSaved.getAssetid());
		creativeAssetDTOSaved.setClickDestinationUri(creativeAssetSaved.getClickDestinationUri());
		creativeAssetDTOSaved.setInsertionpoint(creativeAssetSaved.getInsertionpoint());
		creativeAssetDTOSaved.setType(creativeAssetSaved.getType());
		creativeAssetDTOSaved.setVastUri(creativeAssetSaved.getVastUri());
		creativeAssetDTOSaved.setVpaidCountdown(creativeAssetSaved.isVpaidCountdown());
		creativeAssetDTOSaved.setVpaidStrict(creativeAssetSaved.isVpaidStrict());

		return creativeAssetDTOSaved;
	}

	@Override
	public String createGoalRule(VPZGoalRule vpzGoalRule) throws VPZException {

		ResponseEntity<String> vpzGoalResponse = null;
		boolean responseReceived = false;

		int trial = 5;
		while (!responseReceived && trial > 0) {

			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("x-o-api-key", apiKey);
			headers.add("X-VP-AUTH", getVPAuthToken());
			headers.add("Content-Type", "application/json");

			HttpEntity<?> entity = new HttpEntity<Object>(vpzGoalRule, headers);

			System.out.println("request headers: " + entity.getHeaders());
			System.out.println("Trial : " + trial);
			try {
				vpzGoalResponse = restTemplate.exchange(url + "/api/v2/goal/" + vpzGoalRule.getParentId() + "/rules",
						HttpMethod.PUT, entity, String.class);
				responseReceived = true;
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					System.out.println("Un Authorised error occured");
					responseReceived = false;
				}
			} finally {
				trial--;
			}
		}
		return vpzGoalResponse.getBody();
	}
}
