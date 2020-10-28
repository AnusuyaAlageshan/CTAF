package com.coltnc.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigRead {

	private static final Properties prop = new Properties();

	// Reads Configuration property file
	public ConfigRead(String filepath) {

		File file = new File(filepath);
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			prop.load(in);
		} catch (FileNotFoundException e1) {
			//log.error(e1.getMessage());
			System.out.println("Exception: " + e1);
		} catch (IOException e) {
			//log.error(e.getMessage());
			System.out.println("Exception: " + e);
		}
	}

	// Get Config values from the instance of class
	public static String getProperty(String key) {

		String value = notNull(prop.getProperty(key));
		if (value.length() > 0) {
			return value;
		}
		return "";
	}
	
	public static String notNull(String value) {

		if (value == null) {
			return "";
		}
		else {
			return value;
		}
	}

}

