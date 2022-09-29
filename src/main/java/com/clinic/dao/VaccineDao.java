package com.clinic.dao;

import java.util.List;

import com.clinic.entity.VaccineMaster;
import com.clinic.entity.VaccineMasterDetail;
import com.clinic.entity.VaccineRecord;

public interface VaccineDao {

	VaccineMaster getMstVaccine (String vaccineCode) throws Exception;
	List < VaccineMasterDetail > getListVaccineMstDtl (long batch) throws Exception;
	VaccineRecord getVaccineRecord (int userId, int childId, int batch, String vaccineCode) throws Exception;
	
}
