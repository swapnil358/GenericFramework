package com.ms.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;

import java.nio.file.Files;

public class Report_Helper {

	public void onFinish() {
		moveOldReportToArchive();
		generateReport();
	}

	public void onFinish_rerun() {
		generateReport();
	}

	private void generateReport() {
		try {
			try {
				new UpdateJson().updateJsonFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String[] command = { "cmd.exe", "/C", "Start", "generateReport.bat" };

			try {
				Runtime.getRuntime().exec(command).waitFor();
				Thread.sleep(5000);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			System.out.println("Cucumber report creation is done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void moveFolder(String folderName) {
		File newFolderInArchive = new File("Report\\Archive\\" + folderName);
		try {
			newFolderInArchive.mkdir();
			Files.move(new File("Report\\" + folderName).toPath(), new File("Report\\Archive\\" + folderName).toPath(),
					StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void moveOldReportToArchive() {
		File folder = new File("Report");
		File[] listOfFiles = folder.listFiles();
		new File("Report\\Archive").mkdir();

		for (File file : listOfFiles) {
			if (file.isDirectory() && (file.getName().startsWith("cucumber-html-reports")
					|| file.getName().startsWith("RerunReports"))) {
				System.out.println(file.getName());
			}
		}
	}

	private static void copyFolder(String folderName) {
		File newFolderInArchive = new File(GenerateEmailReportUpdated.sharedFoLder + "/" + folderName);
		try {
			newFolderInArchive.mkdir();
			FileUtils.copyDirectory(new File("./Report/" + folderName),
					new File(GenerateEmailReportUpdated.sharedFoLder + "/" + folderName));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void moveReportToSharePath() {
		File folder = new File("./Report");
		File[] listOfFiles = folder.listFiles();
		new File(GenerateEmailReportUpdated.sharedFoLder).mkdir();

		for (File file : listOfFiles) {
			if (file.isDirectory() && (file.getName().startsWith("cucumber-html-reports")
					|| file.getName().startsWith("RerunReports"))) {
				System.out.println(file.getName());
				copyFolder(file.getName());
			}
		}

	}

}
