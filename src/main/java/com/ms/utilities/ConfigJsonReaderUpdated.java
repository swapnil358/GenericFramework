package com.ms.utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class ConfigJsonReaderUpdated {

	public Set<Entry<String, JsonElement>> brConfig = null;
	public String gsEnvironment;
	public String gsAppUserId;
	public String gsAppUserPwd;
	public String iedriverPath;
	public String chromedriverPath;
	public String Report_ALM;
	public String QA07DBConnectionURL;
	public String QA01DBConnectionURL;
	public String DevHubDBConnectionURL;
	public String DbDriver;
	public String ALM_ID;
	public String ALM_Password;
	public String ALM_AppName;
	public String ALM_Environment;
	public String ALM_Path;
	public String ALM_UpdateSteps;
	public String HeadLessExecution;
	public String RFB_KEY;
	public String AREA_INDICATOR;
	public String APP_NAME;
	public String ReportFolder;
	public String SharePointPath;
	public String ReportTag;

	public ConfigJsonReaderUpdated() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		if (brConfig == null) {
			JsonParser parser = new JsonParser();
			Object obj = parser.parse(new FileReader("resources//Configuration.json"));
			JsonObject jsonObject = (JsonObject) obj;
			JsonElement brData = jsonObject.get("BrowserConfiguration");
			brConfig = ((JsonElement) brData).getAsJsonObject().entrySet();
			gsEnvironment = jsonObject.get("gsEnvironment").getAsString();
			gsAppUserId = jsonObject.get("gsAppUserId").getAsString();
			gsAppUserPwd = jsonObject.get("gsAppUserPwd").getAsString();
			HeadLessExecution = jsonObject.get("HeadLessExecution").getAsString();
			iedriverPath = jsonObject.get("iedriverPath").getAsString();
			chromedriverPath = jsonObject.get("chromedriverPath").getAsString();
			QA07DBConnectionURL = jsonObject.get("QA07DBConnectionURL").getAsString();
			QA01DBConnectionURL = jsonObject.get("QA01DBConnectionURL").getAsString();
			DevHubDBConnectionURL = jsonObject.get("DevHubDBConnectionURL").getAsString();
			DbDriver = jsonObject.get("DbDriver").getAsString();
			Report_ALM = jsonObject.get("Report_ALM").getAsString();
			ALM_ID = jsonObject.get("ALM_ID").getAsString();
			ALM_Password = jsonObject.get("ALM_Password").getAsString();
			ALM_AppName = jsonObject.get("ALM_AppName").getAsString();
			ALM_Environment = jsonObject.get("ALM_Environment").getAsString();
			ALM_Path = jsonObject.get("ALM_Path").getAsString();
			ALM_UpdateSteps = jsonObject.get("ALM_UpdateSteps").getAsString();
			RFB_KEY = jsonObject.get("RFB_KEY").getAsString();
			AREA_INDICATOR = jsonObject.get("AREA_INDICATOR").getAsString();
			APP_NAME = jsonObject.get("APP_NAME").getAsString();
			ReportFolder = jsonObject.get("ReportFolder").getAsString();
			SharePointPath = jsonObject.get("SharePointPath").getAsString();
			ReportTag = jsonObject.get("ReportTag").getAsString();

		}
	}

}
