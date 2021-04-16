package com.ms.utilities;

import java.io.FileReader;
import java.util.Map.Entry;
import java.util.Set;

import org.testng.annotations.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConfigJsonReader {

	public static Set<Entry<String, JsonElement>> brConfig = null;
	public static String chromedriverpath;
	public static String iedriverpath;
	public static boolean Rerun_Flag;
	public static int Rerun_NumberOfTimes;

	
	public ConfigJsonReader() {
		if (brConfig == null) {
			JsonParser parser = new JsonParser();
			Object obj = null;
			try {
				obj = parser.parse(new FileReader("resources//Configuration.json"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			JsonObject jsonObject = (JsonObject) obj;
			JsonElement brData = jsonObject.get("BrowserConfiguration");
			brConfig = ((JsonElement) brData).getAsJsonObject().entrySet();
			iedriverpath = jsonObject.get("iedriverPath").getAsString();
			chromedriverpath = jsonObject.get("chromedriverPath").getAsString();
			if (jsonObject.get("Rerun_Flag").getAsString().equalsIgnoreCase("yes")) {
				Rerun_Flag = true;
			}
			Rerun_NumberOfTimes = jsonObject.get("Rerun_NumberOfTimes").getAsInt();
		}
	}
}
