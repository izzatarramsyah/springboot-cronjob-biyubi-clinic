package com.clinic.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.clinic.dao.AppConfigDao;
import com.clinic.dao.UserDao;
import com.clinic.dao.VaccineDao;
import com.clinic.entity.Child;
import com.clinic.entity.User;
import com.clinic.entity.VaccineMaster;
import com.clinic.entity.VaccineMasterDetail;
import com.clinic.entity.VaccineRecord;
import com.clinic.object.MessageRq;
import com.clinic.service.SchedulerService;

@Service("SchedulerService")
@EnableAsync
@EnableCaching
public class SchedulerServiceImpl extends RestServiceImpl implements SchedulerService {

	private static final Logger LOG = LogManager.getLogger();
	
	private static final String APP_NAME = "BackendScheduler";

	private static final String INSTANCE_NAME = "Notif";
	
	@Value("${message.reminder}")
	private String messageReminder;
	  
	@Autowired
	VaccineDao vaccineDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	AppConfigDao appConfigDao;
	
	private Map<String, String> appConfig;

	private void loadDBConfiguration() {
		this.appConfig = appConfigDao.loadConfig(APP_NAME, INSTANCE_NAME);
	}
	
	private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	
	private String getToday() {
		return formatDate.format( new Date() );
	}

	@CacheEvict(value="appConfig", allEntries = true)
	public void resetConfig() {
		// TODO Auto-generated method stub
	}

	@Scheduled(cron = "${cron.scheduler}")
	public void mainTask() {
		LOG.traceEntry();
		loadDBConfiguration();
		Boolean run = new Boolean(appConfig.get("RUN").trim()) != null ? new Boolean(appConfig.get("RUN").trim()) : false;
		if (run) {
			LOG.info("Scheduler is running.");
			invokeTask();
		} else {
			LOG.info("Scheduler is not running, is safe to shutdown the service.");
		}
		LOG.traceExit();
	}

	@Async
	public void invokeScheduler() {
		LOG.traceEntry();
		loadDBConfiguration();
		invokeTask();
		LOG.traceExit();
	}
	
	private void invokeTask() {
		try { 
			
			Boolean run = new Boolean(appConfig.get("RUN").trim()) != null ? new Boolean(appConfig.get("RUN").trim()) : false;
			
			if (run) {
				
				for (User user  : userDao.getUser()) {
					
					for (Child child : userDao.getChildId( user.getId() )) {
						
						long month = calculateMonth( formatDate.format(child.getBirthDate()) , getToday() );
						List < VaccineMasterDetail > detail = vaccineDao.getListVaccineMstDtl( month );
						
						if (detail.size() > 0) {
							
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							Calendar cal = Calendar.getInstance();
							cal.setTime(child.getBirthDate());
							int countDays = (int) (31 * month);
							cal.add(Calendar.DATE, countDays);
							Date scheduleDate = sdf.parse(sdf.format(cal.getTime()));
							
							if ( validDate(scheduleDate) ) {
								
								boolean doVaccine = false;
								for (VaccineMasterDetail dtl : detail) {
									VaccineMaster vm = vaccineDao.getMstVaccine( dtl.getVaccineCode() );
									VaccineRecord vaccineRecord = vaccineDao.getVaccineRecord( user.getId(), child.getId(), dtl.getBatch(), vm.getVaccineCode());
									if ( vaccineRecord == null ) {
										doVaccine = true;
										break;
									}
								}
								
								if ( doVaccine ) {
									long days = calculateDays( formatDate.format( scheduleDate ) , getToday() );
									String daysInString = "";
									if (days == 0) {
										daysInString = "besok";
									} else {
										daysInString = days + "hari lagi";
									}
									String message = messageReminder.replaceAll("<parentName>", user.getFullname()).replaceAll("<childName>", child.getFullname()).replaceAll("<days>", daysInString);
									post( new MessageRq( user.getPhone_no(), message ) );
								}
								
							}
							
						}
					
					}
				
				}
				
			}
		} catch (Exception e) {
			LOG.info("Error : " + e);
		}
	}
	
	private long calculateMonth(String birthDate, String currentDate) {
    	long diff = ChronoUnit.MONTHS.between( LocalDate.parse(birthDate).withDayOfMonth(1),
	            LocalDate.parse(currentDate).withDayOfMonth(1));
        return diff;
    }
	
	private long calculateDays(String scheduleDate, String currentDate) {
    	long diff = ChronoUnit.DAYS.between( LocalDate.parse(scheduleDate), LocalDate.parse(currentDate));
        return diff;
    }
	
	private boolean validDate(Date scheduleDate) throws ParseException{
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -2);
		Date ThreeDaysBeforeToday = sdf.parse(sdf.format(cal.getTime()));
		
		LOG.info("scheduleDate : " + scheduleDate + " ThreeDaysBeforeToday : " + ThreeDaysBeforeToday + " today : " + today);
		if ( scheduleDate.before(ThreeDaysBeforeToday) ) {
			return false;
		} else if ( scheduleDate.after(today) ) {
			return false;
		} else if ( scheduleDate.after(ThreeDaysBeforeToday) &&  scheduleDate.before(today)) {
			return true;
		}
		
		return false;
	}
	
}

