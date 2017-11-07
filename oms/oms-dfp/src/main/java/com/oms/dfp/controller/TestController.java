package com.oms.dfp.controller;

import com.oms.dfp.configuration.ReadyDFPSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.ads.dfp.lib.client.DfpSession;

@RestController
@RequestMapping(value = "/dfp", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
	
	@Autowired
	DfpSession dfpsession;
	
		
	@Autowired
	ReadyDFPSession readysession;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String connect() {
		return dfpsession.getApplicationName();
	}
	
	

}
