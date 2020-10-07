/**
 * 
 */
package com.ms.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * @Generate report from JSON report file
 *
 */
public class GenerateEmail {

	static String status = "";

	public static void generate_Email() {
		// email ID of Recipient.

		// using host as localhost
		String host = "mta-hub.ms.com";

		// Getting system properties
		Properties properties = System.getProperties();

		// Setting up mail server
		properties.setProperty("mail.smtp.host", host);

		// creating session object to get properties
		Session session = Session.getDefaultInstance(properties);

		String filename = "emailer_Report.html";
		String filePath = "./Report/emailer_Report.html";
		String config_Properties = "./resources/config.properties";
		String date_Format = "MM/dd/yyyy hh:mm:ss a";
		String applicationName = "";
		String executionEnvironment = "";
		String Execution_Type = "";
		String executionEndTime = "";

		String recipient = "";
		String sender = "";
		String Report_Type = "";

		Date dateTime = new Date();
		DateFormat dateFormat = new SimpleDateFormat(date_Format);
		Properties prop = new Properties();

		try {

			executionEndTime = dateFormat.format(dateTime);
			prop.load(new FileInputStream(config_Properties));
			applicationName = prop.getProperty("APPLICATION_NAME");
			executionEnvironment = prop.getProperty("TEST_ENVIRONMENT");
			Execution_Type = prop.getProperty("Execution_Type");
			Report_Type = prop.getProperty("Report_Type");
			recipient = prop.getProperty("Mail_Recipient");
			sender = prop.getProperty("Mail_Sender");

		} catch (IOException e) {
			e.printStackTrace();
		}

		// boolean mailSubject_Pass = GenerateEmailReport.mailSubject_Pass;
		// boolean mailSubject_Fail = GenerateEmailReport.mailSubject_Fail;
		boolean mailSubject_Pass = false;
		boolean mailSubject_Fail = false;
		Map<String, String> mailSubject_Red = GenerateEmailReportUpdated.scenarioStatus;

		for (Entry<String, String> entry : mailSubject_Red.entrySet()) {
			String value = entry.getValue().toString();
			if (value.contains("Pass")) {
				mailSubject_Pass = true;

			} else if (value.contains("Fail")) {
				mailSubject_Fail = true;
			}
		}
		if (mailSubject_Pass == true && mailSubject_Fail == false) {
			status = "**GREEN**";
		} else if (mailSubject_Pass == true && mailSubject_Fail == true) {
		}

		String sub = status + " " + executionEnvironment + " - " + applicationName + "" + Execution_Type + " Results : "
				+ executionEndTime + "";

		String reportData = GenerateEmailReportUpdated.readFile(filePath);

		try { // MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From Field: adding senders email to from field.
			message.setFrom(new InternetAddress(sender));

			// Set To Field: adding recipient's email to from field.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

			// Set Subject: subject of the email
			message.setSubject(sub);

			BodyPart messageBodyPartl = new MimeBodyPart();

			messageBodyPartl.setContent(reportData, "text/html; charset=utf-8");

			BodyPart messageBodyPart2 = new MimeBodyPart();
			DataSource source = new FileDataSource(filePath);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(filename);

			// creating Multipart object
			Multipart multipartObject = new MimeMultipart();
			multipartObject.addBodyPart(messageBodyPartl);
			multipartObject.addBodyPart(messageBodyPart2);

			// set body of the email.
			message.setContent(multipartObject);

			// Send email.
			Transport.send(message);

			System.out.println("Mail successfully sent");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
