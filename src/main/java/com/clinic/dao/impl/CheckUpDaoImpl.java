package com.clinic.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.clinic.config.variable.ApplicationConstant;
import com.clinic.dao.CheckUpDao;
import com.clinic.datamapper.CheckUpMasterMapper;
import com.clinic.datamapper.CheckUpRecordMapper; 
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.CheckUpRecord;

@Repository
public class CheckUpDaoImpl implements CheckUpDao{

	private static final Logger LOG = LogManager.getLogger(CheckUpDaoImpl.class);
	
	public static final String GET_DAYS_CHECK_UP = "SELECT SUM(A.NEXT_CHECK_UP_DAYS) AS DAYS "
			+ " FROM TBL_CHECK_UP_MASTER A "
			+ " WHERE A.BATCH < ? ";
	
	public static final String GET_LIST_MST_CHECK_UP_ = "SELECT A.* "
			+ " FROM TBL_CHECK_UP_MASTER A "
			+ " WHERE A.BATCH = ? ";

	public static final String GET_CURRENT_CHECK_HEALTH_BY_BATCH_MST_CODE = "SELECT A.*  "
			+ "FROM TBL_CHECK_UP_RECORD A "
			+ "WHERE A.USER_ID = ? "
			+ "AND A.CHILD_ID = ? "
			+ "AND A.MST_CODE = ? ";
	
	@Autowired
	@Qualifier(ApplicationConstant.BEAN_JDBC_CLINIC)
	private JdbcTemplate jdbcTemplate;

	@Override
	public long getDays( long batch ) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_DAYS_CHECK_UP);
		long result = 0;
		try{
			result = jdbcTemplate.queryForObject(GET_DAYS_CHECK_UP, new Object[] { batch }, new GetDaysMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}
	
	private class GetDaysMapper implements RowMapper<Long> {
		@Override
		public Long mapRow(ResultSet rs, int row) throws SQLException {
			return rs.getLong("DAYS");
		}
		
	}
	
	@Override
	public CheckUpMaster getListMstCheckUp( long batch ) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_LIST_MST_CHECK_UP_);
		List < CheckUpMaster > result = new ArrayList <CheckUpMaster>();
		try{
			result = jdbcTemplate.query(GET_LIST_MST_CHECK_UP_, new Object[] { batch }, new CheckUpMasterMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() > 0 ? result.get(0) : null;
	}


	@Override
	public CheckUpRecord getCheckUpRecord(int userId, int childId, String mstCode) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_CURRENT_CHECK_HEALTH_BY_BATCH_MST_CODE);
		List < CheckUpRecord > result = new ArrayList < CheckUpRecord >();
		try{
			result = jdbcTemplate.query(GET_CURRENT_CHECK_HEALTH_BY_BATCH_MST_CODE, new Object[] { userId, childId, mstCode }, new CheckUpRecordMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() > 0 ? result.get(0) : null;
	}
	
}
