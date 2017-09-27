package com.ef.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FileReaderUtils {
	private static final String delimiter = "\\|";
	
	public static Map<String, Integer> readIpLogsFromFile(String fileName, 
		Date startTime, Date endTime) throws FileNotFoundException, 
		IOException, ParseException {
		try(FileReader fileReader = 
                new FileReader(fileName); BufferedReader bufferedReader = 
                new BufferedReader(fileReader);) {
			Map<String, Integer> map = new HashMap<>();
			String line = null;
			Date timeOfLog = null;
			Integer count = null;
	        while((line = bufferedReader.readLine()) != null) {
	                String[] content = line.split(delimiter);
	            timeOfLog = DateLib.getDateWithTimeFromString(content[1]);
	            if (DateLib.dateBetween(timeOfLog, startTime, endTime)) {
	            	count = map.get(content[0]);
	                if (count == null) {
	                    map.put(content[0], 1);
	                } else {
	                    map.put(content[0], count + 1);
	                }
	            }                	                
            }
	        return map;
		}
	}
}
