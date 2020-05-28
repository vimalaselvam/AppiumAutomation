package tests;


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
	Write obj1=new Write();
	public int Dataset=-1;
	static Properties prop=new Properties();
	public BaseClass() throws FileNotFoundException {
	
	FileInputStream ip=new FileInputStream("Files\\Input\\Config.properties");

	
	try
	{
		
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
		  DesiredCapabilities cap = new DesiredCapabilities();
		  
		  cap.setCapability("deviceName",prop.getProperty("devicename"));
		  cap.setCapability("udid", prop.getProperty("udid"));
		  cap.setCapability("platformName", prop.getProperty("platformName"));
		  cap.setCapability("platfromVersion", prop.getProperty("platfromVersion"));
		  cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,prop.getProperty("NEW_COMMAND_TIMEOUT"));
		//  cap.setCapability(MobileCapabilityType.APP, "F:\\Vimala\\Appian_Automation\\BaseApp\\src\\test\\resources\\Apps\\app-debug.apk");
		  cap.setCapability("appPackage",prop.getProperty("appPackage"));
		  cap.setCapability("appActivity",prop.getProperty("appActivity"));	 
		  cap.setCapability("–session-override",true);
		  //Appiumserver 
		  
		  URL url= new URL(prop.getProperty("url"));
	
		  
		  //Assign details to appiumdriver 
		 
		
		  driver = new AppiumDriver<MobileElement>(url,cap);
	  
	  
	  } 
	  catch (MalformedURLException e)  { 
		  e.getMessage(); 
		  e.getCause(); 
		  e.printStackTrace();
	  } 
	  
		  
	}
		  
		 
	@DataProvider
	public Object[][] getData()
	{
		Object[][] data=TestUtil.getExcelData(prop.getProperty("sheetname"),prop.getProperty("filepath"));
		return data;
	
	}
	
	
	@AfterTest
	public void tearDown() 
	{	
		
		if (driver != null) {
			driver.quit();
		}
		 
	}

}
