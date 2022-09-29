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
import com.clinic.dao.UserDao;
import com.clinic.datamapper.ChildMapper;
import com.clinic.datamapper.UserMapper;
import com.clinic.entity.Child;
import com.clinic.entity.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	private static final Logger LOG = LogManager.getLogger(UserDaoImpl.class);
	
	public static final String GET_USER = "SELECT A.* FROM TBL_USER A ";

	public static final String GET_CHILD = "SELECT A.* FROM TBL_CHILD A WHERE A.USER_ID = ? ";

	@Autowired
	@Qualifier(ApplicationConstant.BEAN_JDBC_CLINIC)
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List < User > getUser() throws Exception {
		LOG.traceEntry(); 
		LOG.debug("SQL::{}", GET_USER); 
		List < User > result = new ArrayList < User > ();
		try {
			result = jdbcTemplate.query(GET_USER, new Object[] {}, new UserMapper());
		} catch (Exception e) {
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}

	@Override
	public List < Child > getChildId(int userId) throws Exception {
		LOG.traceEntry(); 
		LOG.debug("SQL::{}", GET_CHILD);
		List < Child > result = new ArrayList < Child > ();
		try {
			result = jdbcTemplate.query(GET_CHILD, new Object[] { userId }, new ChildMapper());
		} catch (Exception e) {
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}

	
}
