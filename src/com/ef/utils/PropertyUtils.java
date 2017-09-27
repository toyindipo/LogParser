package com.ef.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	private String dailyThresholdTbl;
	private String hourlyThresholdTbl;
	private String logFileName;
	
	public void setProperties() throws FileNotFoundException, IOException {
		Properties props = new Properties();
        try (InputStream in = this.getClass().getResourceAsStream("/db.properties")) {
        	props.load(in);
        	setDbUrl(props.getProperty("db.url"));
        	setDbUser(props.getProperty("db.user"));
        	setDbPassword(props.getProperty("db.passwd"));
        	setDailyThresholdTbl(props.getProperty("table.threshold.daily"));
        	setHourlyThresholdTbl(props.getProperty("table.threshold.hourly"));
        	setLogFileName(props.getProperty("log.file.name"));
        }
	}
	
	public String getDbUrl() {
		return dbUrl;
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getDailyThresholdTbl() {
		return dailyThresholdTbl;
	}
	public void setDailyThresholdTbl(String dailyThresholdTbl) {
		this.dailyThresholdTbl = dailyThresholdTbl;
	}
	public String getHourlyThresholdTbl() {
		return hourlyThresholdTbl;
	}
	public void setHourlyThresholdTbl(String hourlyThresholdTbl) {
		this.hourlyThresholdTbl = hourlyThresholdTbl;
	}

	public String getLogFileName() {
		return logFileName;
	}
	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}		
}
