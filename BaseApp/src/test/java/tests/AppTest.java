package tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class AppTest extends BaseClass {

	public AppTest() throws FileNotFoundException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String var_firstname, var_lastname, var_email, var_action = null,var_res="";
	public static Row row;

	@Test(dataProvider = "getData")
	public void OpenBaseApp(String action,String firstname,String lastname,String email,String upfname,String uplname,String upemail, String result) throws Exception
	{
		Dataset++;
		String var_result="";
		//excel sheet contains action column with values [create, edit and delete]
		
		//if action field contains value as create it will enter into this loop
		if(action.trim().equalsIgnoreCase("create"))
		{		
			//home page - create button
			MobileElement ele_createbutton=driver.findElement(By.id("com.vthink.baseapp:id/btCreateUser"));
			ele_createbutton.click();
			//To enter first name
			MobileElement ele_firstname=driver.findElement(By.id("com.vthink.baseapp:id/etFirstName" ));	
			ele_firstname.setValue(firstname);
			//To enter last name
			MobileElement ele_lastname=driver.findElement(By.id("com.vthink.baseapp:id/etLastName" ));	
			ele_lastname.setValue(lastname);
			//To enter email
			MobileElement ele_email=driver.findElement(By.id("com.vthink.baseapp:id/etEmail" ));	
			ele_email.setValue(email);
			//after enter those values click on create button
			MobileElement ele_create=driver.findElement(By.id("com.vthink.baseapp:id/etCreate" ));	
			ele_create.click();
			//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			//Write "created" in result field on data file
			var_res="Created";
				
		}		
		//If record have delete or edit, we can entered into this else part
		else if((action.trim().equalsIgnoreCase("delete"))||(action.trim().equalsIgnoreCase("edit")))
		{
			MobileElement ele_listuser,ele_view,ele_deletebtn;
			String ele_fullname,ele_mail = null,var_fullname=firstname + " "+lastname;
			String endloop = "", endloop1 = "t1",endloop2="t";
			int j=0,i=0,flag=0;
			
			//from home page click on listuser
			ele_listuser= driver.findElement(By.id("com.vthink.baseapp:id/btListUser"));
			ele_listuser.click();
			
			//Storing RecyclerView
			ele_view= driver.findElement(By.className("androidx.recyclerview.widget.RecyclerView"));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			//by comparing linear layout size with 0 get a boolean values 
			boolean isFoundTheElement = ele_view.findElementsByClassName("android.widget.LinearLayout").size() > 0;
			System.out.println(isFoundTheElement);
			try 
			{
				//Loop will execute untill linearlayout size come as 0. 
		        while (isFoundTheElement == true)
		        {
		        	//store linear layout data on list
		            List<MobileElement> ele_linearlist=ele_view.findElementsByClassName("android.widget.LinearLayout");
		            int ele_linearlistCount=ele_linearlist.size();
		            System.out.println(ele_linearlistCount);
		            
					//for loop will executed from being to untill found the record, if record found based on the action comment it will run
					while((ele_linearlist.listIterator(i)!=null)&&(i<ele_linearlistCount))
					{
						System.out.println("Loop inside :" + i);
						ele_fullname=ele_linearlist.get(i).findElementById("com.vthink.baseapp:id/tvFullName").getText();
						System.out.println(ele_fullname);
						try
		            	{
			            	if(ele_linearlist.get(i).findElementById("com.vthink.baseapp:id/tvEmail").isDisplayed()==true)
			            	{
				                ele_mail = ele_linearlist.get(i).findElementById("com.vthink.baseapp:id/tvEmail").getText();
								System.out.println(ele_mail );
			            	}
		            	}
		            	catch(NoSuchElementException e)
		            	{
		            		ele_mail="";
		            	}
						//compare given mail id and user mail id.
						if((ele_mail.equalsIgnoreCase(email))&&(ele_fullname.equalsIgnoreCase(var_fullname)))
						{
							
							//compare the name of user with data file name column if its equal then we took mail id from user list.
								ele_linearlist.get(i).click();
								if(action.trim().equalsIgnoreCase("edit"))
								{
									EditFunc(upfname,uplname,upemail);
									
								}
								else if(action.trim().equalsIgnoreCase("delete"))
								{
									System.out.println("Data Found");
									ele_deletebtn=driver.findElement(By.id("com.vthink.baseapp:id/btDelete"));
									ele_deletebtn.click();
									var_res="Deleted";
								}
								flag=1;
								break;
							
						}	
						
						if(i==ele_linearlistCount-1)
		                {
		                	endloop=ele_fullname;
		                	System.out.println("****************************" + endloop);
		                }
						i++;
					}
					if(flag==0)
					{
						System.out.println("next round");
						swipeVertical((AppiumDriver) driver,0.9,0.1,0.5,2);
						System.out.println("Swipe called");
			            isFoundTheElement  = ele_view.findElementsByClassName("android.widget.LinearLayout").size() > 0;
			            System.out.println(isFoundTheElement);
						ele_linearlist.clear();
			            i=1;
			            endloop2=endloop1;
			            endloop1=endloop;
			            if(endloop1.equalsIgnoreCase(endloop2))
			            {
			            	var_res="Data Not Found/Incorrect";
			            	System.out.println("data not found");
			            	break;	
			            }
					}
					else if(flag==1)
					{
						break;
					}
				}
						
		    }
			catch(Exception e)
			{
				e.getStackTrace();
				System.out.println(e.getStackTrace());
			}	
			if((isFoundTheElement == false)&&j==0)
			{
				var_res="List is Empty";
				System.out.println("List is Empty");
			}

		}
		else 
		{
			var_res = "Action missing/incorrect";
		}
		
		//Get error alert from screen	
		try
		{
		if(driver.findElement(By.id("com.vthink.baseapp:id/snackbar_text")).isDisplayed()==true)
		{
			
			
			var_result=driver.findElement(By.id("com.vthink.baseapp:id/snackbar_text")).getText();
			
		}
		}
		catch(Exception e)
		{
			var_result=var_res;
		}
		System.out.println("result" + var_result);
		
		//after created a user or edit or delete screen have to wait until listpage load.
		@SuppressWarnings("unchecked")
		Wait wait = new FluentWait(driver)
		        .ignoring(NoSuchElementException.class)
		        .ignoring(TimeoutException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.LinearLayout")));
		MobileElement ele_view= driver.findElement(By.className("android.widget.LinearLayout"));
		System.out.println(ele_view.getTagName());
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
		driver.runAppInBackground(Duration.ofSeconds(-1));
		driver.activateApp ( "com.vthink.baseapp", null );	
		obj1.WriteResult(var_result, Dataset+1);
		
	
	}
		

	public static void EditFunc(String upfname,String uplname,String upemail)
	{
		
		MobileElement ele_editbtn = driver.findElement(By.id("com.vthink.baseapp:id/btEdit"));
		ele_editbtn.click();
		
		if(upfname!=null && !upfname.isEmpty())
		{
			MobileElement ele_firstname=driver.findElement(By.id("com.vthink.baseapp:id/etFirstName" ));	
			ele_firstname.clear();
			ele_firstname.setValue(upfname);
		}
		if(!uplname.equalsIgnoreCase(null) && !uplname.isEmpty())
		{
			MobileElement ele_lastname=driver.findElement(By.id("com.vthink.baseapp:id/etLastName" ));	
			ele_lastname.clear();
			ele_lastname.setValue(uplname);
		}
		if(!upemail.equalsIgnoreCase(null) && !upemail.isEmpty())
		{
			MobileElement ele_email=driver.findElement(By.id("com.vthink.baseapp:id/etEmail" ));	
			ele_email.clear();
			ele_email.setValue(upemail);
			
		}
		MobileElement ele_create=driver.findElement(By.id("com.vthink.baseapp:id/etCreate" ));	
		ele_create.click();
		var_res="Edited";	
	}

	//to swipe the screen
	public static void swipeVertical(AppiumDriver driver, double startPercentage, double finalPercentage,
			double anchorPercentage, int duration) throws Exception 
	{
		Dimension size = driver.manage().window().getSize();
		int anchor = (int) (size.width * anchorPercentage);
		int startPoint = (int) (size.height * startPercentage);
		int endPoint = (int) (size.height * finalPercentage);
		new TouchAction(driver).press(PointOption.point(anchor, startPoint))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(duration)))
				.moveTo(PointOption.point(anchor, endPoint)).release().perform();
	}

}
