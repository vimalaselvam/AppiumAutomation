package tests;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import Utility.TestUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;


public class BaseClass {
	
	public static AppiumDriver<MobileElement> driver;
	
	//Write class will written the result in DataFile, so create a object for the class
	Write obj1=new Write();
	public int Dataset=-1;
	
	//config file contains inputs, created object for properties 
	static Properties prop=new Properties();	
	
	//constructor 
	public BaseClass() throws FileNotFoundException
	{
	
		FileInputStream ip=new FileInputStream("Files\\Input\\Config.properties");
		try
		{
			//Calling properties filw
			prop.load(ip);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@BeforeTest
	public static void setUp()
	{
		
		
	  try 
	  {
		  //getting app path from config file
		  String apkpath=prop.getProperty("apppath");
		  File app=new File(apkpath);
		  
		  //capabilites declaration 
		  DesiredCapabilities cap = new DesiredCapabilities();		
		  cap.setCapability("deviceName",prop.getProperty("devicename"));
		  cap.setCapability("udid", prop.getProperty("udid"));
		  cap.setCapability("platformName", prop.getProperty("platformName"));
		  cap.setCapability("platfromVersion", prop.getProperty("platfromVersion"));
		  cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,prop.getProperty("NEW_COMMAND_TIMEOUT"));
		  cap.setCapability("appPackage",prop.getProperty("appPackage"));
		  cap.setCapability("appActivity",prop.getProperty("appActivity"));	 
		  cap.setCapability("app", app.getAbsolutePath());
		  cap.setCapability("–session-override",true);
		  
		  //Appiumserver url		  
		  URL url= new URL(prop.getProperty("url"));
	
		  
		  //Assign details to appiumdriver 		
		  driver = new AppiumDriver<MobileElement>(url,cap);
		 
	  
	  } 
	  catch (MalformedURLException e)  
	  { 
		  e.getMessage(); 
		  e.getCause(); 
		  e.printStackTrace();
	  } 		  
	}
		  
	  @DataProvider public Object[][] getData() 
	  { 
		  //calling exceldata class to get data from excel and store into data object
		  Object[][] data=TestUtil.getExcelData(prop.getProperty("sheetname"),prop.getProperty("filepath")); 
		  return data;
	  }
	 
	 
	
	@AfterTest
	public void tearDown() 
	{	
		
		if (driver != null) {
			driver.removeApp(prop.getProperty("appPackage"));
			driver.quit();
		}
		 
	}

}
