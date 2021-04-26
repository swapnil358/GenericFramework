package com.ms.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utilities {

	Properties properties;
	private static String strAbsolutepath = new File("").getAbsolutePath();
	public static String testString = null;

	public static String getConfigValue(String key, String path) {
		Properties properties = new Properties();
		InputStream input = null;
		// System.out.println("Getting Ab5plut PAth:"+strAbsolutepath);
		try {
			input = new FileInputStream(strAbsolutepath + "\\config\\config.properties");
			properties.load(input);

		} catch (Exception e) {

			System.out.println(e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		return properties.getProperty(key);
	}

	public Utilities(String mapFile) {
		properties = new Properties();
		try {
			FileInputStream in = new FileInputStream(mapFile);
			properties.load(in);
			in.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public String ReadProperty(String propkey) {
		try {
			String propval = properties.getProperty(propkey);
			return propval;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}

}
