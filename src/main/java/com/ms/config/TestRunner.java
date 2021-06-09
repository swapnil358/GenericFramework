package com.ms.config;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
		features="resources/Features",
		glue="com.ms.stepDefination",
		plugin= {"pretty","html:HTML-Reports"},
		monochrome=true
		)
public class TestRunner extends AbstractTestNGCucumberTests{

}