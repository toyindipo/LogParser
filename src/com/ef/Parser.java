package com.ef;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.time.DateUtils;

import com.ef.db.DataBaseUtil;
import com.ef.utils.DateLib;
import com.ef.utils.FileReaderUtils;
import com.ef.utils.PropertyUtils;

public class Parser {
	private static final String startDateFlag = "--startDate";
	private static final String durationFlag = "--duration";
	private static final String thresholdFlag = "--threshold";
	
	private static String startDateStr = null;
	private static String duration = null;
	private static int threshold;
	
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.printf("Please pass the three arguments: %s", 
					"--startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100");			
		} else {
			int flag = 0;
					
			for (String str: args) {
				if (str.startsWith(startDateFlag)) {
					startDateStr = str.split("=")[1];
					flag += 1;
				} else if (str.startsWith(durationFlag)) {
					duration = str.split("=")[1];
					flag += 2;
				} else if (str.startsWith(thresholdFlag)) {
					threshold = Integer.valueOf(str.split("=")[1]);
					flag += 4;
				}
			}
			if (flag == 7) {				
				processLog();
			} else {
				System.out.printf("Please pass the three arguments: %s", 
						"--startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100");
			}
		}
	}
	
	private static void processLog() {
		Connection connection = null;
		try {
			PropertyUtils propertyUtils = new PropertyUtils();
			propertyUtils.setProperties();
			
			Date startDate = DateLib.getDateWithTimeFromString(startDateStr);
			Date endDate = null;
			String tableName = null;
			
			if (duration.equals("hourly")) {
				endDate = DateUtils.addHours(startDate, 1);
				tableName = propertyUtils.getHourlyThresholdTbl();
			} else {
				endDate = DateUtils.addDays(startDate, 1);
				tableName = propertyUtils.getDailyThresholdTbl();
			}
			
			Map<String, Integer> logMap = FileReaderUtils.readIpLogsFromFile
					(propertyUtils.getLogFileName(), startDate, endDate);
			
			connection = DataBaseUtil.getConnection(propertyUtils.getDbUrl(), 
					propertyUtils.getDbUser(), propertyUtils.getDbPassword());
			PreparedStatement statement = DataBaseUtil.getPreparedStatement(connection, tableName);
			
			String commentFormat = String.format("Blocked for making $num number of "
					+ "requests between %s and %s%n Which is above the specified threshold", 
					startDateStr, DateLib.dateToString(endDate));
			
			String comment = null;
			for(Entry<String, Integer> entry: logMap.entrySet()) {
				if (entry.getValue() > threshold) {
					comment = commentFormat.replace("$num", entry.getValue().toString());
					DataBaseUtil.insertIntoTable(connection, statement, entry.getKey(), comment);
					System.out.printf("%s: %s%n", entry.getKey(), comment);
				}
			}
		} catch(IOException | ParseException ex) {			
			System.out.println("Error reading or parsing file");
		} catch (SQLException e) {
			System.out.println("Error communicating with database");
		}
		finally{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
