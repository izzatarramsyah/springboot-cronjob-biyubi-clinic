package com.clinic.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.clinic.object.MessageRq;
import com.clinic.object.MessageRs;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestServiceImpl {
	
	protected static final Logger LOG = LogManager.getLogger();
	
	protected boolean post(MessageRq req)  {
		LOG.traceEntry();
		String endpoint = "http://localhost:8000/send-message";
		LOG.info("endpoint : " + endpoint );
		MessageRs res = null;
		try { 
			RestTemplate rest = new RestTemplate();
			res = rest.postForObject(endpoint, req, MessageRs.class);
			LOG.info("Response : " + res.getStatus());
		} catch ( Exception e ) {
			LOG.info("ERR " + e);
			return false;
		}
		LOG.traceExit();
		return res.getStatus();
	}

}
