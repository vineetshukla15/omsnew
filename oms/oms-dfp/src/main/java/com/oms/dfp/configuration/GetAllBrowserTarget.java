package com.oms.dfp.configuration;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201705.Pql;
import com.google.api.ads.dfp.axis.utils.v201705.StatementBuilder;
import com.google.api.ads.dfp.axis.v201705.PublisherQueryLanguageServiceInterface;
import com.google.api.ads.dfp.axis.v201705.ResultSet;
import com.google.api.ads.dfp.lib.client.DfpSession;

//@Configuration
public class GetAllBrowserTarget {/*
	final static Logger logger = Logger.getLogger(GetAllBrowserTarget.class);
	
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
	public Map<String,String> getAllBrowser(DfpSession session){
		DfpServices dfpServices = new DfpServices();
		// Get the PublisherQueryLanguageService.
	    PublisherQueryLanguageServiceInterface pqlService =
	        dfpServices.get(session, PublisherQueryLanguageServiceInterface.class);

	    // Create statement to select all browsers.
	    StatementBuilder statementBuilder = new StatementBuilder()
	        .select("Id, BrowserName, MajorVersion, MinorVersion")
	        .from("Browser")
	        .orderBy("BrowserName ASC")
	        .offset(0)
	        .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT);
	 // Default for result sets.
	    ResultSet combinedResultSet = null;
	    ResultSet resultSet;
	    int i = 0;
	    try {
	    do {
	        // Get all browsers.
	       
				resultSet = pqlService.select(statementBuilder.toStatement());
			

	        // Combine result sets with previous ones.
	        combinedResultSet = combinedResultSet == null
	            ? resultSet
	            : Pql.combineResultSets(combinedResultSet, resultSet);

	        System.out.printf("%d) %d criteria beginning at offset %d were found.%n", i++,
	            resultSet.getRows() == null ? 0 : resultSet.getRows().length,
	            statementBuilder.getOffset());

	        statementBuilder.increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
	        
	      } while (resultSet.getRows() != null && resultSet.getRows().length > 0);
	    } catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    logger.warn(Pql.resultSetToStringArrayList(combinedResultSet));
	    
	    List<String[]> strList =Pql.resultSetToStringArrayList(combinedResultSet);
	    
	    Map<String,String> browserMap = new HashMap<String,String>();
	    
	    for(String[] strArr:strList){
	   	//System.out.println(strArr[0]+" "+strArr[1]);
	    	browserMap.put(strArr[1], strArr[0]);
	    }	    
	    return browserMap;
	}
	*/
}
