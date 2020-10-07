package com.ms.utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSONFile {

	public static ArrayList<Double> getScenarioduration() {

		JSONParser parserl = new JSONParser();
		String scenarioResult = "PASS";
		String stepInfo = "";
		String scenarioInfo = "";
		String scenarioErrorMsg = "";
		double scenarioDuration = 0.0;
		double durationInMin = 0.0;
		ArrayList<Double> scenarioDuartion = null;

		String statusColor = "";
		String stepResult = "";
		String stepErrorMsg = "";
		double testCaseDurationInSecond = 0.0;
		double testCaseDurationInMilliSecond = 0.0;

		String scenarioName = "";
		String status = "";
		double duration = 0;
		double totalDuration = 0;
		try {
			Object obj = null;
			Object objl = null;
			JSONParser parser = new JSONParser();
			Object unitsObj = parser.parse(new FileReader("./Report/cucumber-reports/cucumber1.json"));
			JSONArray array = (JSONArray) unitsObj;
			int count = 0;
			Iterator<String> mainElementsIterator = array.iterator();
			scenarioDuartion = new ArrayList<Double>();
			while (mainElementsIterator.hasNext()) {

				Object mainElson = mainElementsIterator.next();
				JSONObject mainElementsJson = (JSONObject) mainElson;
				JSONArray elements = (JSONArray) mainElementsJson.get("elements");
				Iterator<String> elementsIterator = elements.iterator();

				while (elementsIterator.hasNext()) {
					totalDuration = 0;
					Object elementsObj = elementsIterator.next();
					JSONObject elementsJson = (JSONObject) elementsObj;
					scenarioName = (String) elementsJson.get("name");
					System.out.println("scenarioName == > " + scenarioName);

					String id = (String) elementsJson.get("id");
					// System.out.println("id == > "+id);

					obj = parser.parse(elementsJson.get("steps").toString());
					JSONArray jsonSteps = (JSONArray) obj;
					scenarioErrorMsg = "";

					scenarioInfo = "";
					Iterator<String> stepsIterator = jsonSteps.iterator();
					while (stepsIterator.hasNext()) {
						Object stepJsonObj = stepsIterator.next();
						JSONObject stepJson = (JSONObject) stepJsonObj;

						objl = parser.parse(stepJson.get("result").toString());

						JSONObject jsonResult = (JSONObject) parser.parse(stepJson.get("result").toString());
						;

						if (jsonResult.get("status") != null)
							status = (String) jsonResult.get("status");
						// System.out.println(" status === > "+status);

						if (jsonResult.get("duration") != null)
							duration = (long) jsonResult.get("duration");

						if (status.equalsIgnoreCase("passed")) {

							totalDuration = totalDuration + duration;

						} else {
							if (stepResult == "failed") {
								totalDuration = totalDuration + duration;

								break;
							}
						}
						testCaseDurationInMilliSecond = totalDuration / 1000000;
						// System.out.println("TOTAL DURATION in testCaseDurationInMiliSecond === >
						// "+testCaseDurationInMilliSecond);

						// testCaseDurationInSecond = TimeUnit.MILLISECONDS.toSeconds((long)
						// testCaseDurationInMilliSecond);
						testCaseDurationInSecond = testCaseDurationInMilliSecond / 1000;

						// System.out.println("TOTAL DURATION in testCaseDurationIn Second === >
						// "+testCaseDurationInSecond);

						scenarioDuration = testCaseDurationInSecond / 60;

					}
					durationInMin = Math.floor(scenarioDuration * 100) / 100;
					System.out.println(
							" Adding >.> TOTAL DURATION in testCaseDurationIn Minutes === > " + durationInMin + "\n");

					scenarioDuartion.add(durationInMin);

				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(" durationInMin » " + durationInMin);
		return scenarioDuartion;

	}

}
