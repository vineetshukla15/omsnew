package com.oms.settings.configuration;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * External Properties Loader class. It is common class for accessing by application methods
 * Currently this class depends on specific file "ConnectionDetails.properties". 
 *
 */
public final class ExternalConfigProperties {

	private static Properties externalProperties = new Properties();
	private static final Logger LOG = Logger.getLogger(ExternalConfigProperties.class.getName());	
	
	/**
	 * Loading all properties using classpath resource
	 */
	{
		if (externalProperties.isEmpty()) {
			LOG.info("Started Loading properties ...");
			try {
				externalProperties.load(getClass().getClassLoader().getResourceAsStream("ConnectionDetails.properties"));			
			} catch (IOException e) {
				LOG.fatal("Error while reading properties file"+ e.getMessage());			
			}
			LOG.info("Loaded Properties..."+externalProperties);
			LOG.info("Completed Loading properties ... Total "+externalProperties.size()+" Properties are loaded");
		}		
	}
	
	public static Properties getExternalProperties() {
		return externalProperties;
	}
	
	public static String getProperty(String key) {
		return externalProperties.getProperty(key);
	}
	
}
