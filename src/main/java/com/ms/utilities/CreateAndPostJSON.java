package com.ms.utilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import static io.restassured.RestAssured.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import io.restassured.response.Response;

public class CreateAndPostJSON {
	static int noOfStepsFailed;

	public static void createJSONFile() throws Exception {
		JSONObject executionDetails1 = new JSONObject();
		JSONArray allSteps = new JSONArray();
		executionDetails1.put("RFB KEY", new ConfigJsonReaderUpdated().RFB_KEY);
		executionDetails1.put("AREA_INDICATOR", new ConfigJsonReaderUpdated().AREA_INDICATOR);
		executionDetails1.put("APP_NAME", new ConfigJsonReaderUpdated().APP_NAME);
		executionDetails1.put("ENV", new ConfigJsonReaderUpdated().gsEnvironment);
		executionDetails1.put("EXECUTION DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		executionDetails1.put("RAG_STATUS", GenerateEmail.status.replace("*", "").trim());

		for (int i = 1; i <= GenerateEmailReportUpdated.executionDetaiLs.size(); i++) {
			JSONObject stepInfo = new JSONObject();
			stepInfo.put("STEP_NO", i);
			stepInfo.put("TEST_DESCRIPTION",
					GenerateEmailReportUpdated.executionDetaiLs.get(i).get("TEST_DESCRIPTION"));
			stepInfo.put("STATUS", GenerateEmailReportUpdated.executionDetaiLs.get(i).get("STATUS"));
			if (stepInfo.get("STATUS").equals("Fail")) {
				noOfStepsFailed = noOfStepsFailed + 1;
			}

			stepInfo.put("STEP_TIME", GenerateEmailReportUpdated.executionDetaiLs.get(i).get("STEP_TIME"));
			stepInfo.put("TESTDATA", GenerateEmailReportUpdated.executionDetaiLs.get(i).get("TESTDATA"));
			
			allSteps.add(stepInfo);

			executionDetails1.put("STEP", allSteps);

			try {
				FileWriter file = new FileWriter("resources//ExecutionDetails.json");
				file.write(executionDetails1.toJSONString());
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		System.out.println("Execution Details Uploaded");
		if (noOfStepsFailed == 0) {

			InputStream is = new FileInputStream("resources//ExecutionDetails.json");
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}
			String fileAsString = sb.toString();

			Response res = given().contentType("application/json").body(fileAsString)
					.post("http://iapp459.devini.ms.com:5000/test/pushRFBData").then().log().all().extract().response();
			System.out.println("Status code ##### " + res.getStatusCode());

			System.out.println("response @#@#@#" + res.asString());

			if (!(res.getStatusCode() == 200)) {
				throw new Exception("POST operation went unsuccessful . Received status code as " + res.getStatusCode()
						+ "instead of 200");
			}
		}
	}

}
