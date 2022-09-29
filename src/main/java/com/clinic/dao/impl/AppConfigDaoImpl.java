package com.clinic.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.clinic.config.variable.ApplicationConstant;
import com.clinic.dao.AppConfigDao;


@Repository
@Component
public class AppConfigDaoImpl implements AppConfigDao {

private static final Logger LOG = LogManager.getLogger();
	
	private static final String SQL = " SELECT PARAM_NAME, PARAM_VALUE FROM TBL_APP_CONFIG "
			+ "WHERE APP_NAME = ? "
			+ "AND SVC_NAME = ? "
			+ "AND STATUS = 'ACTIVE' ";
	
	@Autowired
	@Qualifier(ApplicationConstant.BEAN_JDBC_CLINIC)
	private JdbcTemplate jdbcTemplate;

	@Override
	@Cacheable("appConfig")
	public Map<String, String> loadConfig(String appName, String instance) {
		LOG.traceEntry();
		LOG.info("Load Scheduler Configuration");
		LOG.debug("SQL::{}||{},{}",SQL, appName, instance);
		Map<String, String> appConfig = jdbcTemplate.query(SQL, new Object[]{appName, instance}, new ResultSetExtractor<Map<String,String>>() {
			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, String> map = new HashMap<>();
				while(rs.next()){
					map.put(rs.getString("PARAM_NAME"), rs.getString("PARAM_VALUE"));
				}
				return map;
			}
		});
		LOG.debug("All configuration loaded Application : {} service name : {}", appName, instance);
		for (Map.Entry<String, String> config : appConfig.entrySet()) {
			LOG.debug("{}:{}",config.getKey(),config.getValue());
		}
		LOG.traceExit();
		return appConfig;
	}


}
