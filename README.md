# AppiumAutomation

This project contains Appium Automation of BaseApp

BaseApp has simple user list flow with add, edit and delete actions.

Properties file contains values for capabilities and file data file details.
Data file totlay contains 6 input columns 1 output column.

BaseClass contains beforetest, data provider and aftertest methods

@Beforetest - read capabilities details from properties file and pass the information to appiam server.

@dataprovider - it will call the excelfilereader class with parameter filepath and sheet name. 
In the excelclass it will read a data at end of last record and store as object and return it again into data provider.

@Test - Test written in seperate class appteset.java. we pass the data from dataprovider to this class's openapp method parameter 
should contains alls columns[7] which are mentioned in excel. Test will start based on action's value
**Create**
if action contains create value then we collect only firstname, lastname, mailid and click on create button 

**Edit or delete**
else if action contains values (delete or edit) it will get total count of user list and find the exact record by matching full name and mailid with excel data.
then check the action 
**Edit**
if action is edit then click on record proceed with edit flow.
update values which are get from excel only update the values which are (up_firstname, up_lastname or up_mail) not equals null or empty string
other wise skips that empty 

**Delete**
if action is delete then click on record and proceed with click on delete button.

**This functions result are again stored in datafile "Result" column again.** It helps to finds result to unknown/untechnique person who run this automation.



About appium and setup are noted into below linked document please refer: 

https://docs.google.com/document/d/150bFXPeq5T7uuvSjLjrmQ0lbgSW0M77Ax0SXT-soRTw/edit



