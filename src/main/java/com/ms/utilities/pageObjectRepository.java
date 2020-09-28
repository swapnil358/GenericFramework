package com.ms.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class pageObjectRepository {

	public static HashMap<String, HashMap<String, String>> objectMap = new HashMap<String, HashMap<String, String>>();

	private pageObjectRepository() {

	}

	public static void initializeObjectRepository() {
		File folder = new File("resources/pageObject");
		ArrayList<File> fileList = new ArrayList<File>(Arrays.asList(folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".properties");
			}
		})));

		fileList.stream().forEach(file -> {
			objectMap.put(file.getName().substring(0, file.getName().lastIndexOf(".")).toLowerCase(),
					getProperyValue(file.getAbsolutePath()));

		});

	}

	public static String smartObjectFind(String pageName, String locatorKey) {
		if (objectMap.get(pageName.toLowerCase()).get(locatorKey.toLowerCase()) == null)
			return objectMap.get("common").get(locatorKey.toLowerCase());
		else
			return objectMap.get(pageName.toLowerCase()).get(locatorKey.toLowerCase());

	}

	private static HashMap<String, String> getProperyValue(String path) {
		Properties props = new Properties();
		HashMap<String, String> tempObjectMap = new HashMap<String, String>();
		FileInputStream fis;
		try {
			fis = new FileInputStream(path);
			props.load(fis);
			Enumeration keys = props.propertyNames();
			while (keys.hasMoreElements()) {
				String tempKey = (String) keys.nextElement();
				tempObjectMap.put(tempKey.toLowerCase(), props.getProperty(tempKey));
			}

			return tempObjectMap;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
