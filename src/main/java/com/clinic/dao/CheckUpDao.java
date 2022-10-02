package com.clinic.dao;

import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.CheckUpRecord;

public interface CheckUpDao {

	long getDays ( long batch ) throws Exception;
	CheckUpMaster getListMstCheckUp ( long batch ) throws Exception;
	CheckUpRecord getCheckUpRecord ( int userId, int childId, String mstCode) throws Exception;
	
}
