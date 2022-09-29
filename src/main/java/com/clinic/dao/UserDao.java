package com.clinic.dao;

import java.util.List;

import com.clinic.entity.Child;
import com.clinic.entity.User;

public interface UserDao {
	
	List < User > getUser() throws Exception;
	List < Child > getChildId (int userId) throws Exception;

}
