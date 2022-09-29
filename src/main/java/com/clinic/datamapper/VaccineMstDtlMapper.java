package com.clinic.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clinic.entity.VaccineMasterDetail;

public class VaccineMstDtlMapper implements RowMapper<VaccineMasterDetail> {
	@Override
	public VaccineMasterDetail mapRow(ResultSet rs, int row) throws SQLException {
		VaccineMasterDetail result = new VaccineMasterDetail();
		result.setVaccineCode( rs.getString("VACCINE_CODE") );
		result.setBatch( rs.getInt("BATCH") );
		result.setCreatedDtm( rs.getTimestamp("CREATED_DTM") );
		result.setCreatedBy( rs.getString("CREATED_BY") );
		result.setLastUpdDtm(rs.getTimestamp("LASTUPD_DTM"));
		result.setLastUpdBy(rs.getString("LASTUPD_BY"));
		return result;
	}
}