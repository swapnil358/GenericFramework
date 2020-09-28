package com.ms.utilities;

import org.openqa.selenium.WebDriver;

import com.ms.config.XmlRunner;
import com.ms.pages.CommonMethods;

/*
	This is common step defination class step defination can be used 
	any application	
		
	*/	
public class Common_Steps extends CommonMethods{
	
	CommonMethods cm = new CommonMethods();
	public WebDriver driver = XmlRunner.driverMap.get(Thread.currentThread().getId());

}
