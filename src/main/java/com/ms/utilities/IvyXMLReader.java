/**
 * 
 */
package com.ms.utilities;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author n
 *
 */
public class IvyXMLReader {

	public static void main(String[] args) throws Exception {
		readDataFromXMLForCMD();

	}

	public static String readDataFromXMLForCMD() throws ParserConfigurationException, SAXException, IOException {
		File ivyXmlFile = new File("ivy.xml");
		String path = "\\\\ms\\dist\\ossjava\\PROJ\\";
		String runCommand = "java -cp \".;bin;\\\\ms\\dist\\ossjava\\PROJ\\commons-collections\\3.2.2\\lib\\*;";

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(ivyXmlFile);
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("dependency");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			String depName = node.getAttributes().getNamedItem("name").getTextContent();
			String depRev = node.getAttributes().getNamedItem("rev").getTextContent();

			// This code is to get child artifacts if any
			try {
				Element element = (Element) node;
				NodeList allArtifacts = element.getElementsByTagName("artifact");
				int numberOfJars = allArtifacts.getLength();
				if (numberOfJars != 0) {
					for (int j = 0; j < allArtifacts.getLength(); j++) {
						String jarName = element.getElementsByTagName("artifact").item(j).getAttributes()
								.getNamedItem("name").getTextContent() + ".jar";
						System.out.println(jarName);
						runCommand = runCommand + path + depName + "\\" + depRev + "\\" + "lib\\" + jarName + ";";
					}
				} else {
					runCommand = runCommand + path + depName + "\\" + depRev + "\\" + "lib\\*;";
				}
			} catch (Exception e) {
				runCommand = runCommand + path + depName + "\\" + depRev + "\\" + "lib\\*;";
				System.out.println("error");
			}
		}
		runCommand = runCommand + "\" com.ms.config.XmlRunner";
		System.out.println(runCommand);
		return runCommand;

	}
}