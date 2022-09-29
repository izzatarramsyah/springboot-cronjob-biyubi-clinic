package com.clinic.dao;

import java.util.Map;

public interface AppConfigDao {

	Map<String, String> loadConfig(String appName, String instance);

}
