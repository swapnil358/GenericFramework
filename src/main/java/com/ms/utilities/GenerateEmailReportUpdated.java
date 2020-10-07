
package com.ms.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.json.simple.JSONArray;

import java.util.Properties;

public class GenerateEmailReportUpdated {

	public static String parentDir = "./Report";
	public static String runLogfiLe = "runLog.txt";
	public static String jsonReport = "./Report/cucumber-reports/cucumberl.json";
	public static String config_Properties = "./resources/config.properties";
	public static String htmLReportName = "./Report/emailer_report.html";
	public static String sharedFoLder = "////pcg/root/Group/automation_repository/Coverage_Central_reportsr/";
	public static String sharedPath = "";
	// public static String pathSeparator = "/"; // public static String
	// fileIdentifier = "file://"; public static String reportFiLeNameToLink =
	// "overview-features.html";
	public static String appLicationName = "";
	public static String executionEnvironment = "";
	public static String Execution_Type = "";
	public static String Report_Type = "";
	public static String date_Format = "MM/dd/yyyy";
	public static String getStarTimeFormat = "MM/dd/yyyy hh:mm:ss a";
	public static boolean mailSubject_Pass = false;
	public static boolean mailSubject_Fail = false;
	public static boolean mailSubject_RED = false;
	public static String scenarioStatus_FinaL = "Pass";
	public static String reports = "";
	public static Date dateTime = new Date();
	public static File folder = new File(parentDir);
	public static File[] ListOfFiLes = folder.listFiles();

	public static String[] testResuLts;
	public static int sceenshotFileIndex = 0;
	public static String finaLizedHIML;
	public static int stepNum = 0;
	public static String htmlSteps;
	public static String htmlStepsLists = "";
	public static String htmlStepserrorMessage = "";
	public static String statusCoLor = "";
	public static String executionStartTime = "";
	public static String executionStartDate = "";
	public static String executionEndTime = "";
	public static Map<String, String> scenarioStatus = new LinkedHashMap<String, String>();
	static ArrayList<Double> scenarioDuartion = null;

	// public static Map<String, String> scenarioStatus = new LinkedHashMap<String,
	// String>();

	public static Map<Integer, LinkedHashMap<String, Object>> executionDetaiLs = new TreeMap<Integer, LinkedHashMap<String, Object>>();
	public static LinkedHashMap<String, Object> executionStepslnfo = new LinkedHashMap<String, Object>();

	public static String htmlFirstPart = "<html><head> <meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'></head><body lang='EN-us' link='blue' vlink='purple' style.'tab-interval:.5in'><div class='sectionl'> <table class= msoNormalTable' border='0' cellspacing=.0' cellpadding='0' width='1012'style='width:759.15pt;border-collapse:collapse;mso-yfti-tbllook:1184; mso-padding-alt:Oin Oin Oin Oin'> <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes;height:241.65pt'> <td style='border:solid #333333 1.0pt;padding:15.0pt 15.0pt 15.0pt 15.0pt; height:241.65pt'> <table class='msoNormalTable' border='0' cellspacing='0' cellpadding='0' width='898'style='width:9.35in;margin-left:.35pt;border-collapse:collapse;mso-yfti-tbllook:1184;mso-padding-alt:Oin Oin Oin Oin'> <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:108.45pt'> <td style='background:#080838;padding:5.0pt 5.0pt 5.Opt 5.0pt;height:108.45pt'> <table class= msoNormalTable' border='0' cellpadding='0' width='99%'style.'width:99.26%;mso-cellspacing:1.5pt;background:#060B3B;mso-yfti-tbllook:1184;mso-padding-alt:Oin Oin Oin Oin'> <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:25.4pt'> <td style='background:transparent;padding:Oin Oin Oin Oin;height:25.4pt ><span style='color:white'></span> <span style='font-size:8.0pt;font-family:Arial;color:white;letter-spacing:1.2pt'> kdiv align='left'> wm Technology</div></span></td><td style=*background:transparent;padding:Oin Oin Oin Oin;hei9ht:25.4pt'><span style='font-size:24.5pt;font-family:Arial;color:white;letter-spacing:1.2pt'><div align='right'> Morgan stanley </div></span></td></tr><tr style='mso-yfti-irow:2;height:17.4pt'> <td colspan='2'style='background:transparent;padding:Oin Oin Oin Oin;height:17.4pt;background-position-x:0%;background-position-y:0%;background-attachment:scroll'> <p> <i> <span style.'font-size:11.0pt;font-family:cambria;color:white'›{FAcT RFB Alert} </span> </i> </p> </td></tr><tr style='mso-yfti-irow:4;mso-yfti-lastrow:yes;height:44.4pt'> <td colspan='2'style='background:transparent;padding:Oin Oin Oin Oin;height:44.4pt;background-position-x:0%;background-position-y:0%;background-attachment:scroll'> <p style='margin:Oin;margin-bottom:.0001pt'> <span style.'font-size:18.0pt;font-family:Cambria;color:white'> {APPL_NAMEJ {ENV} Completion status </span> </p> </td></tr></table></td></tr><tr style='mso-yfti-irow:1;height:12.65pt'> <td style='background:#848589;padding:Oin 5.0pt Oin 5.0pt;height:12.65pt; background-position-x:0%;background-position-y:0%;background-attachment: scroll'> <table class='MsoNormalTable' border='0' cellpadding='0' width='99%'style.'width:99.24%;mso-cellspacing:1.5pt;background:#848589;mso-yfti-tbllook:1184;mso-padding-alt:Oin Oin Oin Oin'> <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes;height:9.5pt'> <td style='background:transparent;padding:Oin Oin Oin Oin;height:9.5pt'> <p class='msoNormal'style='mso-margin-top-alt:auto;mso-margin-bottom-alt: auto;line-height:9.5pt'><span style='font-size:7.5pt;font-famlly:Arial;color:white;letter-spacing:1.2pt'> FOR INTERNAL USE ONLY - NOT FOR REoisTRIBuTioN</span> </p> </td> <td style='background:transparent;padding:Oin Oin Oin Oin;height:9.5pt;background-position-x:0%;background-position-y:0%;background-attachment:scroll'> <p class='MsoNormal' align='right'style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;text-align:right;line-height:9.5pt'><span style='font-size:8.0pt;font-family:Arial;color:whlte'> <span><a href='mailto:sf-coveragecentral-automations'style='font-size:7.5pt;color:white'>contact Us</a> </span>I {EXEC_DATE} </span> </p; </td> </tr> </table> </td></tr><tr style='mso-yfti-irow:2;mso-yfti-lastrow:yes;height:105.9pt'> <td style='padding•.75pt .75pt .75pt .75pt;height:105.9pt;font-color:black'> <table class='MsoNormalTable' border='0' cellspacing='0' cellpadding='0'width='99%'style='width:99.88%;mso-cellspacing:Oin;mso-yfti-tbllook:1184;mso-padding-alt: Oin Oin Oin Oin'> <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes; height:91.75pt'> <td style='padding:5.0pt 5.Opt 5.0pt 5.0pt;height:91.75pt'> <p class='MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt: auto'> <span style='font-size:10.0pt;font-family:Book Antiqua;color:black'>Hi All, <p> </p> </span> </p> <p><span style='font-size:10.0pt;font-family:Book Antiqua;color:black'>Please find below results of automated {RFB} execution</span> <span style='color:black'> </span> <span style='font-size:10.0pt; font-family:Arial;color:blue'> </span> <span style='color:black'> </span> <p> </p> <table class='msoNormalTable'border='O'cellspacing='O'cellpadding='0'width='311'style='width:233.05pt;border-collapse:collapse;mso-yfti-tbllook: 1184;mso-paddin9-alt:Oin Oin Oin Oin'> <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'> <td style='padding:Oin Oin Oin Oin'> <div> <p> </p> <table class.'msoNormalTable'border='0'cellspacing='0'cellpadding='0'width='996'style='width:747.0pt;border-collapse:collapse;mso-yfti-tbllook: 1184;mso-padding-alt:Oin Oin Oin Oin'> <tr style='mso-yfti-irow:0;mso-yfti-fi:848589yes;height:13.5pt'> <td width='202'valign= bottom'style='width:31.8pt;border:solid windowtext 1.0pt; background:#848589;padding:Oin 5.4pt Oin 5.4pt;height:13.5pt'> <p class='MsoNormal'align='center'style='mso-margin-top-alt:auto; mso-margin-bottom-alt:auto;text-align:center'> <b> <span style='font-size:10.0pt;font-family:Book Antiqua;color:black'>s. No. </span> </b> </p> </td> <td width='217'valign.'bottom'style=\"width:253.05pt;border:solid windowtext 1.0pt; border-left:none;background:#848589;padding:Oin 5.4pt Oin 5.4pt; height:13.5pt'> <p class='msoNormal'align=.center'style='mso-margin-top-alt:auto; mso-margin-bottom-alt:auto;text-align:center'> <b> <span style='font-size:10.0pt;font-family:Book Antiqua;color:black >verification Name </span> </b> </p> </td> <td width='192'valign='bottom'style='width:44.3pt;border:solid windowtext 1.0pt; border-left:none;background:#848589;padding:Oin 5.4pt Oin 5.4pt; height:13.5pt'> <p class='msoNormal'align='center'style='mso-margin-top-alt:auto; mso-margin-bottom-alt:auto;text-align:center'> <b> <span style='font-size:10.0pt;font-family:Book Antiqua;color:black >status </span> </b> </p> </td> <td width='184'valign='bottom'style='width:248.3pt;border:solid windowtext 1.0pt;border-left:none;background:#848589;padding:Oin 5.4pt Oin 5.4pt;height:13.5pt'> <p class='msoNormal'align=.center'style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;text-align:center'> <b> <span style.'font-size:10.0pt;font-family:Book Antiqua;color:black'>Duration(min)</span> </b> </p> </td><td width=.184'valign=\"bottom'style='width:248.3pt;border:solid windowtext 1.0pt;border-left:none;background:#848589;padding:Oin 5.4pt Oin 5.4pt;height:13.5pt'> <p class.'msoNormal'align.'center'style.'mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;text-align:center'> <b> <span style.'font-size:10.0pt;font-family:Book Antiqua;color:black'>comments </span> </b> </p> </td> </tr>";
	public static String htmlStepsPart = "<!-- Test STEPS START --> <tr style='mso-yfti-irow:1;height:13.5pt.> <td width='202'style='width:31.8pt;border:solid windowtext 1.0pt;border-top:none;padding:Oin 5.4pt Oin 5.4pt;height:13.5pt'> <p class='MsoNormal'align='center'style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;text-align:center'><span style='font-size:8.0pt;font-family:Tahoma'><span style='font-family:Tahoma'> {STEP_NUM}</span> </span> </p> </td> <td width='217'valign='bottom'style='width:253.05pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.Opt;padding:Oin 5.4pt Oin 5.4pt;height:13.5pt'> <p class='MsoNormal'style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto'><span style.'font-size:8.0pt;font-family:Tahome> {STEP_NAME}</span> </p> </td> <td width='192'style.'width:44.3pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.Opt;padding:Oin 5.4pt Oin 5.4pt;height:13.5pt'> <p class='MsoNormal'align='center'style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;text-align:center'><span style.'font-size:8.0pt;font-family:Tahoma'><b>fsTEP_REsoLT1</b></span> </p> </td> <td width=p184'style='width:248.3pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.Opt;padding:Oin 5.4pt Oin 5.4pt;height:13.5pt'> <p class='msoNormal'align='center'style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;text-align:left'><span style='font-size:8.0pt;font-family:Tahoma.> ISTEP_DURATION1</span> </p> </td><td width=.184'style='width:248.3pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.Opt;padding:Oin 5.4pt Oin 5.4pt;height:13.5pt'> <p class='MsoNormal'align='center'style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;text-align:left'><span style='font-size:8.0pt;font-family:Tahoma'> {STEP_COMMENTS}/span> </p> </td> </tr>";
	public static String htmlfeatureFileTitle = "<!-- Test STEPS START --> <tr style='mso-yfti-irow:1;height:13.5pt.> <td width='202'style='width:31.8pt;border:solid windowtext 1.0pt;border-top:none;padding:Oin 5.4pt Oin 5.4pt;height:13.5pt'> <p class='MsoNormal'align='center'style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;text-align:center'><span style='font-size:8.0pt;font-family:Tahoma'><span color = blue style.'font-family:Tahome> {STEP_NUM}</span> </span> </p> </td> <td width=.217'valign='bottom'style.'width:253.05pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:Oin 5.4pt Oin 5.4pt;height:13.5pt'> <p class='MsoNormal'style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto'><span style='font-size:8.0pt;font-family:Tahoma'> <b><font color=blue> {STEP_NAME}</font></b></span> </p> </td> <td width='192'style='width:44.3pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:Oin 5.4pt Oin 5.4pt;height:13.5pt'> <p class='msoNormal'align='center'style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;text-align:center'><span style='font-size:8.0pt;font-family:Tahoma'><b><font color=blue>lsTEP_REsuLT1</font></b></span> </p> </td> <td width='184'style='width:248.3pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:Oin 5.4pt Oin 5.4pt;height:13.5pt'> <p class='MsoNormal'align=*center'style='mso-margin-top-alt:automso-margin-bottom-alt:auto;text-align:left'><span style='font-size:8.0pt;font-family:Tahome> ISTEP_DURATIoNl</span> </p> </td><td width='184'style='width:248.3pt;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;padding:Oin 5.4pt Oin 5.4pt;height:13.5pt'> <p class='MsoNormal'align='center'style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;text-align:left'><span style='font-size:8.0pt;font-family:Tahoma'> {STEP_COMMENTS}</span> </p> </td> </tr>";
	public static String htmlLastPart = "<!-- Test STEPS END --> </table> <table class='MsoNormalTable'border='0'cellspacing='0'cellpadding='0'width='996'style='width:747 .Opt;border-collapse:collapse;mso-yfti-tbllook:1184;mso-padding-alt:Oin Oin Oin Oin'> <tr> <br> <br><span style.'font-size:10.0pt;font-family:Book Antiqua'><b>Application Name:</b> {APPL_NAME}<br><b>Environment:</b> {ENV}<br><b>Time Zone:</b> {TZ}<br><b>Run Started:</b> {EXEC_START}<br><b>Run Ended:</b> {EXEC_END}<br><b>Complete report can be found - </b> <i> <a href=file:////pcg/root/Group/automation_repository/Coverage_Central_reports/{RPRT_PATH} ><i>Here</i></a></span></tr> </table> </div> <tr> <td> <div><br><p class='MsoNormal'style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto'><span style='font-size:10.0pt'>Regards,<a href='mailto:coveragecentral-automation'><br><b> <font color.'black.>Coverage Central Automation Team</font></b><br> </a></span> </p> </td> </div></tr> </table> <p class='MsoNormal'> <p> </p> <p> </p> </td> </tr> </table> <p class='MsoNormal'> <p> </p> <p> </p> </td></tr></table><p class='MsoNormal'><p></p><p></p></td></tr></table><p class='MsoNormal' style='mso-margin-top-alt:automso-margin-bottom-alt:auto'><p></p></div></body></html>";

	public static String readFile(String filename) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

//public static void main(String[] args) throws I0Exception, InterruptedException, ConfigurationException { 

	public static void generateReport() throws Exception {

		scenarioDuartion = new ArrayList<Double>();
		executionStartTime = MSRunner.executionStartTime;
		executionStartDate = MSRunner.executionStarDate;
		DateFormat dateFormat = new SimpleDateFormat(getStarTimeFormat);
		DateFormat dateFormatl = new SimpleDateFormat(date_Format);

		
		// executionStartTime = dateFormat.format(dateTime);
		 // System.out.println(dateFormat.format(dateTime)); executionStartDate =
		 //dateFormatl.format(dateTime).toString();
		 //System.out.println("executionStartTime : "+ executionStartTime);
		 

	

		Report_Helper.moveReportToSharePath();

		String htmlFirstPart_temp = new String(htmlFirstPart);
		htmlFirstPart_temp = htmlFirstPart_temp.replace("{EXEC_DATE}", executionStartDate);

		System.out.println("Testing......... ");
		String jsonData = readFile(jsonReport);

		String featureName;
		String scenarioName;
		String stepName;
		String stepstatus = "Passed";
		String stepDuration;

		LinkedHashMap<String, String> stepNameAndStatus;
		LinkedHashMap<String, LinkedHashMap<String, String>> scenarioAndSteps;
		Map<String, LinkedHashMap<String, LinkedHashMap<String, String>>> featureScenarioSteps = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>>();

		List<Boolean> list_Status = new ArrayList<Boolean>();

		Properties prop = new Properties();

		try {

			prop.load(new FileInputStream(config_Properties));
			appLicationName = prop.getProperty("APPLICATION_NAME");
			executionEnvironment = prop.getProperty("TEST_ENVIRONMENT");
			Execution_Type = prop.getProperty("Execution_Type");
			Report_Type = prop.getProperty("Report_Type");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		htmlFirstPart_temp = htmlFirstPart_temp.replace("{FACT RFB Alert}", Report_Type);
		htmlFirstPart_temp = htmlFirstPart_temp.replace("{RFB}", Execution_Type);

		finaLizedHIML = htmlFirstPart_temp;

		scenarioDuartion = ReadJSONFile.getScenarioduration();

		JSONArray featureFileList = new JSONArray(jsonData);
		for (int i = 0; i < featureFileList.length(); i++) {
			System.out.println(" i featureFileList loop >> " + i);
			featureName = "";
			
			// String htmlfeature = new String(htmlfeatureFileTitle);
			scenarioAndSteps = new LinkedHashMap<String, LinkedHashMap<String, String>>();

			JSONObject featurefile = featureFileList.getJSONObject(i);
			featureName = featurefile.get("name").toString();
			JSONArray scenarioList = featurefile.getJSONArray("elements");

			
			/*
			  htmlfeature = htmlfeature.replace("fSTEP_NUM1", "");
			  htmlfeature = htmlfeature.replace("fSTEP_UAME}", featureName); 
			  htmlfeature = htmlfeature.replace("fSTEP_RESULTI", ""); 
			  htmlfeature = htmlfeature.replace("{STEP_COMMENTS}", ""); 
			  htmlStepsLists = htmlStepsLists + htmlfeature
			 */

	

			for (int j = 0; j < scenarioList.length();  j++) {

				scenarioName = "";
				scenarioStatus_FinaL = "Pass";
				htmlSteps = new String(htmlStepsPart);

				stepNameAndStatus = new LinkedHashMap<String, String>();

				JSONObject scenarioDescription = scenarioList.getJSONObject(j);
				scenarioName = scenarioDescription.get("name").toString();

				JSONArray stepList = scenarioDescription.getJSONArray("steps");
				for (int x = 0; x < stepList.length(); x++) {
					htmlStepserrorMessage = "";

					stepName = "";
					stepstatus = "Passed";

					JSONObject stepDescription = stepList.getJSONObject(x);
					stepName = stepDescription.get("name").toString();

					JSONObject statusOfStep = stepDescription.getJSONObject("result");

					stepstatus = statusOfStep.get("status").toString();

					if (stepstatus.equalsIgnoreCase("failed")) {

						htmlStepserrorMessage = stepName + " - step failed";
						stepDuration = statusOfStep.get("duration").toString();
						mailSubject_Fail = true;
						scenarioStatus_FinaL = "Fail";
						break;

					} else if (stepstatus.equalsIgnoreCase("Passed")) {
						mailSubject_Pass = true;
					}

					stepNameAndStatus.put(stepName, stepstatus);
				}

				htmlSteps = htmlSteps.replace("{STEP_NUM}", String.valueOf(++stepNum));
				htmlSteps = htmlSteps.replace("{STEP_NAME}", scenarioName);
				if (stepstatus.equalsIgnoreCase("Passed")) {
					htmlSteps = htmlSteps.replace("<b>{STEP_RESULT}</b>",
							"<b><font color=green>fSTEP_RESULT1</font></b>");
					htmlSteps = htmlSteps.replace("{STEP_RESULT}", "PASS");
					System.out.println(" i j Passed loop >> " + i + " - " + j);
					htmlSteps = htmlSteps.replace("{STEP_DURATION}", scenarioDuartion.get(j).toString());
					htmlSteps = htmlSteps.replace("{STEP_COMMENTS}", htmlStepserrorMessage);
				} else {
					htmlSteps = htmlSteps.replace("<b>{STEP_RESULT}</b>",
							"<b><font color=red>{STEP_RESULT}</font></b>");
					htmlSteps = htmlSteps.replace("{STEP_RESULT}", "FAIL");
					System.out.println(" i j failed loop >> " + i + " - " + j);
					htmlSteps = htmlSteps.replace("{STEP_DURATION}", scenarioDuartion.get(j).toString());
					htmlSteps = htmlSteps.replace("{STEP_COMMENTS}", htmlStepserrorMessage);
				}

				// System.out.println("stepNameAndStatus: " + stepNameAndStatus);
				scenarioAndSteps.put(scenarioName, stepNameAndStatus);
				scenarioStatus.put(scenarioName, scenarioStatus_FinaL);
				htmlStepsLists = htmlStepsLists + htmlSteps;

			}
			// System.out.println("scenarioAndSteps: " + scenarioAndSteps);
			featureScenarioSteps.put(featureName, scenarioAndSteps);

		}
		scenarioDuartion.clear();
		executionEndTime = dateFormat.format(dateTime);
		// System.out.println("featureScenarioSteps: "+featureScenarioSteps);
		// System.out.println("scenarioStatus : "+scenarioStatus);

		finaLizedHIML = finaLizedHIML + htmlStepsLists + htmlLastPart;

		finaLizedHIML = finaLizedHIML.replace("{APPL_NAME}", appLicationName);
		finaLizedHIML = finaLizedHIML.replace("{ENV}", executionEnvironment);
		finaLizedHIML = finaLizedHIML.replace("{EXEC_START}", executionStartTime);
		finaLizedHIML = finaLizedHIML.replace("{EXEC_END}", executionEndTime);
		finaLizedHIML = finaLizedHIML.replace("{TZ}", "India Standard Time");

		String Cucumber_Report_Folder = "";
		File directory = new File(parentDir);
		File[] subdirs = directory.listFiles((FilenameFilter) DirectoryFileFilter.DIRECTORY);
		for (File dir : subdirs) {
			System.out.println("Directory: " + dir.getName());
			if (dir.getName().startsWith("cucumber-html-reports") || dir.getName().startsWith("RerunReports")) {
				Cucumber_Report_Folder = dir.getName();
				break;
			}
		}

		sharedPath = Cucumber_Report_Folder + "/cucumber-html-reports/overview-features.html";

		finaLizedHIML = finaLizedHIML.replace("{RPRT_PATH}", sharedPath);

		File newHtmlFile = new File(htmLReportName);
		if (newHtmlFile.exists()) {
			newHtmlFile.delete();
		}
		OutputStream outputStream = new FileOutputStream(newHtmlFile);
		Writer writer = new OutputStreamWriter(outputStream);
		writer.write(finaLizedHIML);
		writer.close();
		Thread.sleep(2000);
		GenerateEmail.generate_Email();
		// CreateAndPostJSON.createlSONFile();    
	}

}
