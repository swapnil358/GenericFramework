package com.ms.utilities;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UpdateJson {

	public void updateJsonFile() throws Exception {
		// File file = new File("Report//cucumberl.isoa");
		File folder = new File("Report");

		for (File file : folder.listFiles()) {
			if (file.getName().endsWith(".json")) {
				List<String> allLines = Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8);
				List<String> newLines = new ArrayList<>();

				for (int i = 0; i < allLines.size(); i++) {
					String line = allLines.get(i);
					if (line.contains("java.lang.Exception: This scenario is failed")) {
						newLines.add(
								"\"error_message\": \"Click on below link to open Screenshot for failed test case\",");

					} else if (line.contains("error_message\": \"java.lang")) {
						newLines.add(line.split("tat")[0].replaceFirst(".$", "\","));
					} else {
						newLines.add(line);
					}
				}
				Files.write(Paths.get(file.getPath()), newLines, StandardCharsets.UTF_8);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new UpdateJson().updateJsonFile();
	}

}
