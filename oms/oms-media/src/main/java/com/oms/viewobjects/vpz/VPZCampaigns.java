package com.oms.viewobjects.vpz;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="collection")
public class VPZCampaigns {
	private List<VPZCampaign> campaignBean;

	public List<VPZCampaign> getCampaignBean() {
		return campaignBean;
	}

	public void setCampaignBean(List<VPZCampaign> campaignBean) {
		this.campaignBean = campaignBean;
	}


}
