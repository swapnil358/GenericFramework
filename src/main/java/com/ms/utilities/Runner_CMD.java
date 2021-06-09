/**
 * 
 */
package com.ms.utilities;

/**
 * @author n
 *
 */
public class Runner_CMD {

	public static void main(String[] args) throws Exception {
		System.out.println("Execution started");
		String[] command = { "cmd.exe", "/C", "start", "cmd.exe", "/k", IvyXMLReader.readDataFromXMLForCMD() };
		Runtime.getRuntime().exec(command);
		System.out.println("Close window");
		
		
	}

}
