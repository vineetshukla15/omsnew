package com.oms.dfp.configuration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.api.client.auth.oauth2.Credential;

@Configuration
public class ReadyDFPSession  {
	
	final static Logger logger = Logger.getLogger(ReadyDFPSession.class);
	
	//final static Logger LOGGER = Logger.getLogger(DFPServiceImpl.class);
//	@Autowired
//	private RestTemplate restTemplate;
	
	

	@Value("${api.dfp.refreshToken}")
	private String refreshToken;

	@Value("${api.dfp.clientId}")
	private String clientId;

	@Value("${api.dfp.applicationName}")
	private String appName;

	@Value("${api.dfp.networkCode}")
	private String networkCode;
	
	@Value("${api.dfp.clientSecret}")
	private String clientSecret;
	
	DfpSession dfpSession;
	
	@Bean
	public DfpSession getReadyDfpSession(){
		try {
			Credential credential = new OfflineCredentials.Builder().forApi(Api.DFP)
					.withClientSecrets(clientId, clientSecret)
					.withRefreshToken(refreshToken)
					.build().generateCredential();
			
		 this.dfpSession = new DfpSession.Builder().withOAuth2Credential(credential)
					.withApplicationName(appName)
					.withNetworkCode(networkCode).build();
		} catch (OAuthException | ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return dfpSession;
	}

//	@Override
//	public String getAppNameFromDFPSession() {
//		return authenticate().getApplicationName();
//		
//	}
	
	

}
