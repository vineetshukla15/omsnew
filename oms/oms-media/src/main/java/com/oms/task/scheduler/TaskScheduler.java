package com.oms.task.scheduler;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.api.email.service.EmailService;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.VPZException;
import com.oms.model.AudienceTargetType;
import com.oms.model.AudienceTargetValues;
import com.oms.model.CreativeAsset;
import com.oms.model.Order;
import com.oms.model.OrderLineItems;
import com.oms.model.OrderLineitemTarget;
import com.oms.service.OMSOrderService;
import com.oms.service.VPZService;
import com.oms.viewobjects.Budget;
import com.oms.viewobjects.vpz.FrequencyRuleBean;
import com.oms.viewobjects.vpz.TagAndPartnerRuleBean;
import com.oms.viewobjects.vpz.UserAgentRuleBean;
import com.oms.viewobjects.vpz.VPZAD;
import com.oms.viewobjects.vpz.VPZCampaign;
import com.oms.viewobjects.vpz.VPZCreative;
import com.oms.viewobjects.vpz.VPZGoal;
import com.oms.viewobjects.vpz.VPZGoalRule;

@Component
public class TaskScheduler {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private VPZService vpzService;

	@Autowired
	private OMSOrderService omsOrderService;
	
	@Autowired
	public EmailService emailService; 

	@Scheduled(fixedRate = 2 * 60 * 1000)
	public void scheduleTask() throws MessagingException {
		pushOrderToVPZ();
	}

	@Transactional
	public void pushOrderToVPZ() throws MessagingException {

		try {
			List<Order> orders = omsOrderService.findOrdersForVPZ();
			for (Order order : orders) {
				String savedcampaignId = order.getVpzCampaignId();

				/*
				 * update or crate campaign only when campaiign id is null i.e
				 * order is just created or order is long created but updated
				 * since last job executed
				 */
				if ((savedcampaignId == null || "".equalsIgnoreCase(savedcampaignId))
						|| isChanged(order.getUpdated())) {
					String createdOrSavedcampaignId = uploadCampaign(order);

					if (createdOrSavedcampaignId != null) {

						System.out.println("campaignId ceated : " + createdOrSavedcampaignId);

						Set<OrderLineItems> orderLineItems = order.getLineItems();
						System.out.println(orderLineItems.size());
						for (OrderLineItems orderLineItem : orderLineItems) {
							CreativeAsset creativeAsset = orderLineItem.getCreativeAsset();
							String savedGoalId = orderLineItem.getVpzGoalId();
							/*
							 * update or crate Goal only when creative is
							 * associated with goal and goalid is null i.e line
							 * item is just created or creative is associated
							 * with goal and Goal is long created but updated
							 * since last job executed
							 */
							if (creativeAsset != null && ((savedGoalId == null || "".equalsIgnoreCase(savedGoalId))
									|| isChanged(orderLineItem.getUpdated()))) {

								String goalId = uploadGoal(order, orderLineItem, createdOrSavedcampaignId);
								System.out.println("goalId ceated or updated : " + goalId);
								if (goalId != null) {

									// create goal Rule
									uploadGoalRule(orderLineItem, goalId);
									VPZAD vpzAD = new VPZAD();
									vpzAD.setName(creativeAsset.getName());
									vpzAD.setGoalId(goalId);
									vpzAD.setWeight(orderLineItem.getPriorityValues());
									// vpzAD.setStart(orderLineItem.getFlightStartdate());
									// vpzAD.setStart(orderLineItem.getFlightEndDate());
									String assetId = creativeAsset.getAssetid();
									if (assetId != null && !"".equalsIgnoreCase(assetId)) {

										VPZCreative vpzCreative = new VPZCreative();
										vpzCreative.setAssetId(assetId);
										vpzCreative.setInsertionPoint(creativeAsset.getInsertionpoint());
										vpzCreative.setType(creativeAsset.getType().toLowerCase());
										vpzAD.setCreative(vpzCreative);

										VPZAD vpzADCreated = vpzService.createAd(vpzAD);
										System.out.println("Ad Created :" + vpzADCreated.toString());
									}
								}

							}

						}
					}
					// campaign loop
				}
			}
		} catch (OMSSystemException e) {
			e.printStackTrace();
		}  catch (VPZException e) {
			//
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String uploadCampaign(Order order) throws VPZException {

		VPZCampaign vpzCampaign = new VPZCampaign();
		vpzCampaign.setName(order.getName());

		// @TODO
		// vpzCampaign.setAdvertiserId(order.getAdvertiser().getVpzid());
		// vpzCampaign.setAgencyId(order.getAgency().getVpzid());

		
		Budget budget = new Budget();
		budget.setValue(new BigDecimal(order.getBudget()));
		vpzCampaign.setBudget(budget);
		
		

		vpzCampaign.setDescription(order.getDescription());
		vpzCampaign.setEnabled(true);
		// vpzCampaign.setFrontLoadFactor(proposal.get);
		vpzCampaign.setVastEnabled(true);
		String campaignId = order.getVpzCampaignId();
		if (campaignId != null && !"".equalsIgnoreCase(campaignId)) {
			vpzCampaign.setId(campaignId);
			vpzService.updateCampaign(vpzCampaign);
		} else {
			campaignId = vpzService.createCampaign(vpzCampaign);
			order.setVpzCampaignId(campaignId);
		}
		omsOrderService.updateOrder(order);
		// set campaignid for Order
		return campaignId;

	}

	private String uploadGoal(Order order, OrderLineItems orderLineItem, String campaignId) throws VPZException {

		VPZGoal vpzGoal = new VPZGoal();
		vpzGoal.setCampaignId(campaignId);
		vpzGoal.setStart(orderLineItem.getFlightStartdate());
		vpzGoal.setEnd(orderLineItem.getFlightEndDate());
		vpzGoal.setName(orderLineItem.getName());
		// setting frontload
		// vpzGoal.setName(orderLineItem.getFrontLoad());
		vpzGoal.setType("IMPRESSION");
		vpzGoal.setVariant("NORMAL");
		vpzGoal.setGoalValue(new BigDecimal(orderLineItem.getQuantity()));


		String vpzGoalId = orderLineItem.getVpzGoalId();

		if (vpzGoalId != null && !"".equalsIgnoreCase(vpzGoalId)) {
			vpzGoal.setId(vpzGoalId);
			vpzService.updateGoal(vpzGoal);
		} else {
			vpzGoalId = vpzService.createGoal(vpzGoal);
			orderLineItem.setVpzGoalId(vpzGoalId);
			omsOrderService.updateOrderLineItem(orderLineItem);
		}
		return vpzGoalId;
	}

	private void uploadGoalRule(OrderLineItems orderLineItem, String goalId) throws VPZException {

		VPZGoalRule vpzGoalRule = new VPZGoalRule();

		vpzGoalRule.setParentId(goalId);
		List<TagAndPartnerRuleBean> tagAndPartnerRules = vpzGoalRule.getTagAndPartnerRules();

		TagAndPartnerRuleBean tagAndPartnerRuleBeanTag = new TagAndPartnerRuleBean();
		tagAndPartnerRuleBeanTag.setResourceType("TAG");
		tagAndPartnerRuleBeanTag.setRuleType("ALL_OF");
		tagAndPartnerRuleBeanTag.setTag("sport");

		tagAndPartnerRules.add(tagAndPartnerRuleBeanTag);

		Set<OrderLineitemTarget> orderLineItemTargets = orderLineItem.getTarget();
		for (OrderLineitemTarget orderLineItemTarget : orderLineItemTargets) {
			AudienceTargetType audienceTargetType = orderLineItemTarget.getAudienceTargetType();
			if (audienceTargetType.getName().equalsIgnoreCase("Age")) {

			} else if (audienceTargetType.getName().equalsIgnoreCase("Gender")) {

			} else if (audienceTargetType.getName().equalsIgnoreCase("States")) {

			} else if (audienceTargetType.getName().equalsIgnoreCase("Countries")) {

			} else if (audienceTargetType.getName().equalsIgnoreCase("Device")) {

			} else if (audienceTargetType.getName().equalsIgnoreCase("Browser")
					|| audienceTargetType.getName().equalsIgnoreCase("OS")) {

				List<UserAgentRuleBean> userAgentRules = vpzGoalRule.getUserAgentRules();
				if (audienceTargetType.getName().equalsIgnoreCase("Browser")) {

					UserAgentRuleBean userAgentRuleBean = new UserAgentRuleBean();
					userAgentRuleBean.setAccess("ALLOW");
					List<AudienceTargetValues> audienceTargetValues = audienceTargetType.getAudienceTargetValues();
					List<String> browsers = userAgentRuleBean.getBrowsers();
					for (AudienceTargetValues audienceTargetValue : audienceTargetValues) {

						// "IE" | "FIREFOX" | "CHROME" | "SAFARI" | "OPERA" |
						// "OTHER" , ...
						browsers.add(audienceTargetValue.getValue().toUpperCase());

					}
					userAgentRuleBean.setBrowsers(browsers);
					userAgentRules.add(userAgentRuleBean);

				} else if (audienceTargetType.getName().equalsIgnoreCase("OS")) {
					UserAgentRuleBean userAgentRuleBean = new UserAgentRuleBean();
					userAgentRuleBean.setAccess("ALLOW");
					List<AudienceTargetValues> audienceTargetValues = audienceTargetType.getAudienceTargetValues();
					List<String> operatingSystems = userAgentRuleBean.getOperatingSystems();
					for (AudienceTargetValues audienceTargetValue : audienceTargetValues) {
						String vpzVal = "WINDOWS";
						if ("windows".equalsIgnoreCase(audienceTargetValue.getValue())) {
							vpzVal = "WINDOWS";
						} else if ("Mac".equalsIgnoreCase(audienceTargetValue.getValue())) {
							vpzVal = "MACOSX";
						}
						operatingSystems.add(vpzVal);
					}
					userAgentRuleBean.setOperatingSystems(operatingSystems);
					userAgentRules.add(userAgentRuleBean);
				}

			} else if (audienceTargetType.getName().equalsIgnoreCase("Frequency cap")) {

				List<FrequencyRuleBean> frequencyRuleBeans = vpzGoalRule.getFrequencyRules();

				Set<AudienceTargetValues> audienceTargetValues = orderLineItemTarget.getAudienceTargetValues();
				for (AudienceTargetValues audienceTargetValue : audienceTargetValues) {
					FrequencyRuleBean frequencyRuleBean = new FrequencyRuleBean();
					if ("Days".equalsIgnoreCase(audienceTargetValue.getValue())) {
						frequencyRuleBean.setTimeUnit("DAY");
					} else if ("Lifetime".equalsIgnoreCase(audienceTargetValue.getValue())) {
						frequencyRuleBean.setTimeUnit("GOAL_LIFETIME");
					} else if ("Month".equalsIgnoreCase(audienceTargetValue.getValue())) {
						frequencyRuleBean.setTimeUnit("MONTH");
					} else if ("Week".equalsIgnoreCase(audienceTargetValue.getValue())) {
						frequencyRuleBean.setTimeUnit("WEEK");
					} else if ("Hours".equalsIgnoreCase(audienceTargetValue.getValue())) {
						frequencyRuleBean.setTimeUnit("HOUR");
					}
					frequencyRuleBean.setImpressions(orderLineItem.getQuantity());
					frequencyRuleBeans.add(frequencyRuleBean);
				}
			}
		}

		vpzService.createGoalRule(vpzGoalRule);

	}

	private boolean isChanged(Date modifiedDate) {
		Date currentDateTime = new Date();
		long currentTime = currentDateTime.getTime() ;
		long modificationTime =  modifiedDate.getTime();
		long timeDiff = currentTime - modificationTime;
		if (timeDiff <= 2 * 60 * 1000) {
			return true;
		}
		return false;
	}

}
