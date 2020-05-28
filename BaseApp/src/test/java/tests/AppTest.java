package tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;

public class AppTest extends BaseClass {
	
	
	public AppTest() throws FileNotFoundException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String var_firstname,var_lastname,var_email,var_action=null;
	public static Row row;
	
	@Test(dataProvider = "getData")
	public void OpenBaseApp(String action,String firstname,String lastname,String email,String upfname,String uplname,String upemail, String result) throws Exception
	{
		Dataset++;
		String var_result="";
		String var_res="";
		//excel sheet contains action column with values [create, edit and delete]
		
		//if in sheet has action as create means below if condition and statement should be executed.
		if(action.trim().equalsIgnoreCase("create"))
		{
				
		MobileElement ele_createbutton=driver.findElement(By.id("com.vthink.baseapp:id/btCreateUser"));
		ele_createbutton.click();
		

		MobileElement ele_firstname=driver.findElement(By.id("com.vthink.baseapp:id/etFirstName" ));	
		ele_firstname.setValue(firstname);
		MobileElement ele_lastname=driver.findElement(By.id("com.vthink.baseapp:id/etLastName" ));	
		ele_lastname.setValue(lastname);
		MobileElement ele_email=driver.findElement(By.id("com.vthink.baseapp:id/etEmail" ));	
		ele_email.setValue(email);
		
		MobileElement ele_create=driver.findElement(By.id("com.vthink.baseapp:id/etCreate" ));	
		ele_create.click();
		//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		var_res="Created";
			
			
			
		}		
		//If record have delete or edit, we can entered into this else part
		else if((action.trim().equalsIgnoreCase("delete"))||(action.trim().equalsIgnoreCase("edit")))
		{
			List<MobileElement> ele_viewsub=null;
			MobileElement ele_listuser,ele_view,ele_editbtn,ele_deletebtn;
			String ele_fullname,ele_mail,var_fullname=firstname + " "+lastname;
			int var_usercount;
			ele_listuser= driver.findElement(By.id("com.vthink.baseapp:id/btListUser"));
			ele_listuser.click();
			

			ele_view= driver.findElement(By.className("androidx.recyclerview.widget.RecyclerView"));
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			ele_viewsub=ele_view.findElementsByClassName("android.widget.LinearLayout");
			var_usercount=ele_viewsub.size();
			System.out.println(" F: " + upfname);
			//check if userlist has record or not. if it has record, it can entered into this if block or else block will be executed
			if(var_usercount>0)
			{
				int flag=0;
				try
				{
					
					//for loop will executed from being to untill found the record, if record found based on the action comment it will run
					for(int i=0;i<var_usercount;i++)
					{
						System.out.println("Loop inside :" + i);
						ele_mail=ele_viewsub.get(i).findElementById("com.vthink.baseapp:id/tvEmail").getText();
						System.out.println(email + var_fullname );
						//compare given mail id and user mail id.
						if(ele_mail.equalsIgnoreCase(email))
						{
							ele_fullname=ele_viewsub.get(i).findElementById("com.vthink.baseapp:id/tvFullName").getText();
							System.out.println(ele_fullname);
							System.out.println(var_fullname);
							//compare the name of user with data file name column if its equal then we took mail id from user list.
							if(ele_fullname.equalsIgnoreCase(var_fullname))
							{
								ele_viewsub.get(i).click();
								
								if(action.trim().equalsIgnoreCase("edit"))
								{
									ele_editbtn=driver.findElement(By.id("com.vthink.baseapp:id/btEdit"));
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
						}
						
					}
					
				}
				catch(NoSuchElementException e)
				{
					e.getStackTrace();
					System.out.println(var_usercount);
				}	
				if(flag==0)
				{
					var_res="Data Not Found/Incorrect";
				}
			}
			else if(var_usercount==0)
			{
				var_res="List is Empty";
				System.out.println("List is Empty");
			}
			
	
		}
		else 
		{
			var_res = "Action missing/incorrect";
		}
		
		
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
		System.out.println(var_result);
		
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
	
	

}
