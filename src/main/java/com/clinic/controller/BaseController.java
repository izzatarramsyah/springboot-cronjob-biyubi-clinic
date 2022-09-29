package com.clinic.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseController {

	 private static final Logger LOG = LogManager.getLogger(BaseController.class);
	 
	 protected <T> T ObjectMapper(String content, Class<T> valueType)
	            throws JsonParseException, JsonMappingException, IOException {
	     LOG.traceEntry();
	     ObjectMapper mapper = new ObjectMapper();
	     T t = (T) mapper.readValue(content, valueType);
	     LOG.traceExit();
	     return t;  
	 }
	
}	
