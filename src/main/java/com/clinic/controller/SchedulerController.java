package com.clinic.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.object.InvokeSchedulerResponse;
import com.clinic.object.ResetConfigSchedulerResponse;
import com.clinic.service.SchedulerService;

@RestController
@RequestMapping(value="/scheduler")
public class SchedulerController {
	
	private static final Logger LOG = LogManager.getLogger(SchedulerController.class);

	@Autowired
	SchedulerService schedulerService;
	
	@RequestMapping(value="/run",method= RequestMethod.GET,produces= MediaType.APPLICATION_JSON_VALUE)
	public InvokeSchedulerResponse runManual(){
		LOG.traceEntry();
		LOG.info("runManual");
		schedulerService.invokeScheduler();
		LOG.traceExit();
		return new InvokeSchedulerResponse("00","Scheduler run.");
	}
	
	@RequestMapping(value="/reset",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResetConfigSchedulerResponse resetConfig(){
		LOG.traceEntry();
		LOG.info("reset");
		schedulerService.resetConfig();
		LOG.traceExit();
		return new ResetConfigSchedulerResponse("00", "Remove cache.");
	}

}
