package com.coltnc.common.utils;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import com.coltnc.test.pageobjects.NC_Objects;
import com.relevantcodes.extentreports.LogStatus;
import com.coltnc.common.utils.DriverManager;
import com.coltnc.common.utils.ReadData;
import com.coltnc.common.utils.PerformAction;
import com.coltnc.common.extentreport.*;
import com.coltnc.common.utils.ConfigRead;

public class NC_Library {
		

public WebDriver driver = DriverManager.WEB_DRIVER_THREAD_LOCAL.get();
	
	public NC_Objects NC_Objects;			

	public NC_Library() {
		NC_Objects = new NC_Objects();		
		//Explore_Objects = new Explore_Objects();

	}
	public String NCLogin() throws IOException, InterruptedException {
	/*----------------------------------------------------------------------
	Method Name : CPQ_Login
	Purpose     : This method will progress the login of NC application
	Designer    : Anusuya A
	Created on  : 28 Sep 2020 
	Input       : String file_name, String UserType
	Output      : True/False
	 ----------------------------------------------------------------------*/ 
//	Initialize the Variable	
	new ConfigRead("Config.properties");
	String Environment = ConfigRead.getProperty("env");
	String NCURL = ConfigRead.getProperty("nc_url");
	System.out.println("URL"+NCURL);
	String NCUsername = ConfigRead.getProperty("nc_user");
	System.out.println("NCUsername"+NCUsername);
	String NCPassword = ConfigRead.getProperty("nc_password");
	
//	Launching URL
	PerformAction.launchWebApp(NCURL);	
	Waittilljquesryupdated();
	WaitforNCloader();
	
//	Check NC Login page is launched
	PerformAction.isPresent(NC_Objects.userNameTxb,90);
	NC_Objects.userNameTxb.sendKeys(keysToSend);
	ExtentTestManager.getTest().log(LogStatus.PASS, "NC URL "+NCURL+" launch is successfull");
	System.out.println("NC URL "+NCURL+" launch is successfull");
	
//	Performing the Login Operations
	PerformAction.sendKeys(NC_Objects.userNameTxb, NCUsername);
	PerformAction.sendKeys(NC_Objects.passWordTxb, NCPassword);
	PerformAction.click(NC_Objects.loginBtn);
	Waittilljquesryupdated();
	
//	Verify Login Successsfull or not
	if ((PerformAction.waitForElementToBeVisible(NC_Objects.searchTxb, 75))&&(PerformAction.waitForElementToBeVisible(NC_Objects.usernameNCHome, 75))) {
		Waittilljquesryupdated();
		ExtentTestManager.getTest().log(LogStatus.PASS, "NC URL application Login with "+NCURL+" is successfull");
		System.out.println("NC URL application Login with "+NCURL+" is successfull");
	}
	else {
		ExtentTestManager.getTest().log(LogStatus.FAIL, "Login to NC application "+NCURL+" Failed");
		System.out.println("Login to NC application "+NCURL+" Failed");
		ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addBase64ScreenShot(DriverManager.Capturefullscreenshot()));
		return "False";
	}
	
	return "True";
	
}

	public void Waittilljquesryupdated() throws InterruptedException, SocketTimeoutException {
		/*----------------------------------------------------------------------
		Method Name : Waittilljquesryupdated
		Purpose     : This method will return whether the driver steady state is completed or not
		Designer    : Anusuya A
		Created on  : 28 Sep 2020 
		Input       : None
		Output      : None
		 ----------------------------------------------------------------------*/ 
//		JavascriptExecutor js = null;
		boolean Status = false;
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) DriverManager.WEB_DRIVER_THREAD_LOCAL.get();
		for (int i=1; i<10; i++) {
			if (js == null) {
				Thread.sleep(250);
				js = (JavascriptExecutor) DriverManager.WEB_DRIVER_THREAD_LOCAL.get();
				continue;
			} else {
				try {
					while(!(js.executeScript("return document.readyState").equals("complete")))
					{
						Thread.sleep(500);
					}
					Status = true;
					if (Status = true) { Thread.sleep(250); break; }
				} catch (Exception e) {
					continue;
				}
			}
		}	
	}


	public void WaitforNCloader() throws IOException, InterruptedException {
		/*----------------------------------------------------------------------
		Method Name : WaitforC4Cloader
		Purpose     : This method will wait C4C application while loading page occurs
		Designer    : Anusuya A
		Created on  : 28 Sep 2020 
		Input       : None
		Output      : None
		 ----------------------------------------------------------------------*/ 
		int i = 1;
		Thread.sleep(1000);   
		WebDriver driver = DriverManager.WEB_DRIVER_THREAD_LOCAL.get();
	      try {
	    	  while ((NC_Objects.userNameTxb.isDisplayed())&&(NC_Objects.ncimg.isDisplayed())) {   
	    		  if (i > 60) { break; }    		  
	    		  Thread.sleep(1000);    		  	
	                 i = i+1;
	            } 
	      } catch(Exception e) {
	      }
	}
	
	//Navigate to Composite Order
	public void NcIntegrationCompositeOrder(Object[] Inputdata) throws Exception
	{
		//Clickon(getwebelement(xml.getlocator("//locators/AccountNameSorting")));
		PerformAction.click(NC_Objects.accNameSorting);
		Thread.sleep(10000);
		//Clickon(getwebelement(xml.getlocator("//locators/ProdIdFiltering")));
		PerformAction.click(NC_Objects.ProdIdFiltering);
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Clicked on Product ID Filter");
		Thread.sleep(40000);
		//Select(getwebelement(xml.getlocator("//locators/FilterSelect")),"Product ID");
		PerformAction.selectByValue(NC_Objects.FilterSelect, "Product ID");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Selected Product ID in Filter");
		Thread.sleep(5000);
//		SendKeys(getwebelement(xml.getlocator("//locators/FilterInputValue")),CircuitRefnumber.get());
		//SendKeys(getwebelement(xml.getlocator("//locators/FilterInputValue")),"LON/FRA/LE-501960");
		PerformAction.sendKeys(NC_Objects.filterInputVal, "LON/FRA/LE-501960");
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Siebel Circuit ID provided for further processing");
		Thread.sleep(40000);
		//Clickon(getwebelement(xml.getlocator("//locators/SelectLink")));
		PerformAction.click(NC_Objects.SelectLink);
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Clicked on the Product ID link");
		//Clickon(getwebelement(xml.getlocator("//locators/OrderLink")));
		PerformAction.click(NC_Objects.OrderLink);
		Thread.sleep(10000);
		//Ordernumber.set(GetText(getwebelement(xml.getlocator("//locators/LinkforOrder"))));
		PerformAction.sendKeys(NC_Objects.OrderNumber, NC_Objects.LinkOrder.getText());
		Log.info(Ordernumber.get());
		String[] arrOfStr = Ordernumber.get().split("#", 0); 
		Log.info(arrOfStr[1]);
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Order Number Generated : "+ Ordernumber.get());
		OrderscreenURL.set(Getattribute(getwebelement(xml.getlocator("//locators/LinkforOrder")),"href"));
		OrderscreenURL.set(NC_Objects.linkforOrder.getAttribute("href"));
		Log.info(OrderscreenURL.get());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Order URL Generated : "+ OrderscreenURL.get());
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Navigate to Composite Order");
		Clickon(getwebelement(xml.getlocator("//locators/Accountbredcrumb")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Navigated to Accounts Composite Orders Tab");
		Clickon(getwebelement(xml.getlocator("//locators/AccountNameSorting")));
		waitandclickForOrderStarted("//a/span[contains(text(),'"+arrOfStr[1]+"')]/parent::*/parent::*/following-sibling::td[contains(text(),'Process Started')]",60);
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Waiting for the Order to be Started");
//		Clickon(getwebelement(xml.getlocator("//locators/AccountNameSorting")));
	}
	
	//Complete the workitems of the product 
	
	public void CompleteIpAccessOrder(Object[] Inputdata) throws Exception
	{
		String[] arrOfStr = Ordernumber.get().split("#", 0);
		Thread.sleep(50000);
//		Clickon(getwebelement(xml.getlocator("//locators/AccountNameSorting")));
		Clickon(getwebelement("//a/span[contains(text(),'"+arrOfStr[1]+"')]/parent::*"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Click on the Composite Order");
		OrderscreenURL.set(Getattribute(getwebelement(xml.getlocator("//locators/LinkforOrder")),"href"));
		Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTab")));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Click on Tasks Tab");
		Clickon(getwebelement(xml.getlocator("//locators/Tasks/ExecutionFlowlink")));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Click on the Execution Flow Link");
		Thread.sleep(10000);
		Clickon(getwebelement(xml.getlocator("//locators/Tasks/Workitems")));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Click on Workitems Tab");
		Thread.sleep(5000);
		for (int k=1; k<=Integer.parseInt(Inputdata[136].toString());k++)
		{
			waitandclickForworkitemsPresent(xml.getlocator("//locators/Tasks/TaskReadytoComplete"),90);
			ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Waiting for Manual Workitems to be displayed");
			Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskReadytoComplete")));
			ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Click on the Workitem in  Ready status");
			Completworkitem(GetText2(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle"))),Inputdata);
		}
		Geturl(OrderscreenURL.get());
		ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Navigate to Composite Order");
		Clickon(getwebelement(xml.getlocator("//locators/Accountbredcrumb")));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Navigate to Accounts Composite Orders Tab");
		Clickon(getwebelement(xml.getlocator("//locators/AccountNameSorting")));
		waitandclickForOrderCompleted("//a/span[contains(text(),'"+arrOfStr[1]+"')]/parent::*/parent::*/following-sibling::td[contains(text(),'Process Completed')]",120);
		//Clickon(getwebelement("//a/span[contains(text(),'"+arrOfStr[1]+"')]/parent::*"));
	}
	
	// Complete the work item information
	public void Completworkitem(String[] taskname,Object[] Inputdata) throws Exception, DocumentException
	{ 
	System.out.println("In Switch case with TaskName :"+taskname);
	ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Start completion of Task: "+taskname[0]);
	switch(taskname[0])
	{
		case "Reserve Access Resources":
		if(taskname[1].contains(Inputdata[15].toString()))	
		{
			if(Inputdata[34].toString().equalsIgnoreCase("Protected"))
			{
				//Code for Protected
				PerformAction.clear(NC_Objects.AccessPort);				
				Thread.sleep(1000);
				PerformAction.clear(NC_Objects.AccessNetworkElement);	
				PerformAction.sendKeysWithKeys(NC_Objects.AccessNetworkElement, Inputdata[117].toString(),"ENTER");
				Thread.sleep(20000);
				PerformAction.sendKeysWithKeys(NC_Objects.AccessPort, Inputdata[118].toString(),"ENTER");
				Thread.sleep(20000);
				PerformAction.clear(NC_Objects.CPENNIPort1);	
				PerformAction.sendKeysWithKeys(NC_Objects.CPENNIPort1, Inputdata[122].toString(),"ENTER");
				Thread.sleep(20000);
				PerformAction.clear(NC_Objects.CPENNIPort2);	
				PerformAction.sendKeysWithKeys(NC_Objects.CPENNIPort2, Inputdata[123].toString(),"ENTER");
				Thread.sleep(20000);
				PerformAction.click(NC_Objects.Complete);
				ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Required details are filled for Reserve Access Task");
			}
			else 
			{
				//Code for Unprotected
				PerformAction.clear(NC_Objects.AccessPort);				
				Thread.sleep(1000);
				PerformAction.clear(NC_Objects.AccessNetworkElement);	
				PerformAction.sendKeysWithKeys(NC_Objects.AccessNetworkElement, Inputdata[117].toString(),"ENTER");
				Thread.sleep(20000);
				PerformAction.sendKeysWithKeys(NC_Objects.AccessPort, Inputdata[118].toString(),"ENTER");
				Thread.sleep(20000);
				PerformAction.clear(NC_Objects.CPENNIPort1);	
				PerformAction.sendKeysWithKeys(NC_Objects.CPENNIPort1, Inputdata[122].toString(),"ENTER");
				Thread.sleep(20000);
				PerformAction.click(NC_Objects.Complete);
				ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Required details are filled for Reserve Access Task");
			}
		}
			break;

			case "Transport Circuit Design":
			if(taskname[1].contains(Inputdata[15].toString()))
	        {
				if(Inputdata[34].toString().equalsIgnoreCase("Protected"))
	        	{ 
					//Code for Protected
					Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
					getwebelement(xml.getlocator("//locators/Tasks/PENetworkElement")).clear();
					Clickon(getwebelement(xml.getlocator("//locators/Tasks/PENetworkElement")));
					SendKeys(getwebelement(xml.getlocator("//locators/Tasks/PENetworkElement")),Inputdata[119].toString());
					Thread.sleep(60000);
					SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/PENetworkElement")),Keys.ENTER);
					Thread.sleep(60000);
//					getwebelement(xml.getlocator("//locators/Tasks/VCXController")).clear();
//					SendKeys(getwebelement(xml.getlocator("//locators/Tasks/VCXController")),Inputdata[124].toString());
//					Thread.sleep(10000);
//					SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/VCXController")),Keys.ENTER);
//					getwebelement(xml.getlocator("//locators/Tasks/Beacon")).clear();
//					Thread.sleep(5000);
//					SendKeys(getwebelement(xml.getlocator("//locators/Tasks/Beacon")),Inputdata[125].toString());
//					Thread.sleep(5000);
//					SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/Beacon")),Keys.ENTER);
//					Thread.sleep(10000);
					Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
					Clickon(getwebelement(xml.getlocator("//locators/GeneralInformation/GeneralInformationTab")));
					Clickon(getwebelement(xml.getlocator("//locators/Tasks/EthernetLinkTransportRFS_1")));
					Clickon(getwebelement(xml.getlocator("//locators/Edit")));
					getwebelement(xml.getlocator("//locators/Tasks/PePort")).clear();
					SendKeys(getwebelement(xml.getlocator("//locators/Tasks/PePort")),Inputdata[120].toString());
					Thread.sleep(60000);
					SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/PePort")),Keys.ENTER);
					Thread.sleep(30000);
					Clickon(getwebelement(xml.getlocator("//locators/Update")));
					Clickon(getwebelement(xml.getlocator("//locators/TransportCfs")));
					Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
					Clickon(getwebelement(xml.getlocator("//locators/GeneralInformation/GeneralInformationTab")));
					Clickon(getwebelement(xml.getlocator("//locators/Tasks/EthernetLinkTransportRFS_2")));
					Clickon(getwebelement(xml.getlocator("//locators/Edit")));
					getwebelement(xml.getlocator("//locators/Tasks/PePort")).clear();
					SendKeys(getwebelement(xml.getlocator("//locators/Tasks/PePort")),Inputdata[121].toString());
					Thread.sleep(60000);
					SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/PePort")),Keys.ENTER);
					Thread.sleep(30000);
					Clickon(getwebelement(xml.getlocator("//locators/Update")));
					Clickon(getwebelement(xml.getlocator("//locators/TransportCfs")));
					Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
					Clickon(getwebelement(xml.getlocator("//locators/Edit")));
					Clickon(getwebelement(xml.getlocator("//locators/Tasks/TransComplete")));
					Thread.sleep(2000);
					ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Required details are filled for Transport Circuit Task");
					//workitemcounter=workitemcounter+1;
	         	}
	            else
	            {
	            	//Code for Unprotected
					Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
					getwebelement(xml.getlocator("//locators/Tasks/PENetworkElement")).clear();
					Clickon(getwebelement(xml.getlocator("//locators/Tasks/PENetworkElement")));
					SendKeys(getwebelement(xml.getlocator("//locators/Tasks/PENetworkElement")),Inputdata[119].toString());
					Thread.sleep(60000);
					SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/PENetworkElement")),Keys.ENTER);
					Thread.sleep(30000);
					getwebelement(xml.getlocator("//locators/Tasks/PePort")).clear();
					SendKeys(getwebelement(xml.getlocator("//locators/Tasks/PePort")),Inputdata[120].toString());
					Thread.sleep(60000);
					SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/PePort")),Keys.ENTER);
					Thread.sleep(30000);
//					getwebelement(xml.getlocator("//locators/Tasks/VCXController")).clear();
//					SendKeys(getwebelement(xml.getlocator("//locators/Tasks/VCXController")),Inputdata[124].toString());
//					Thread.sleep(10000);
//					SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/VCXController")),Keys.ENTER);
//					getwebelement(xml.getlocator("//locators/Tasks/Beacon")).clear();
//					Thread.sleep(5000);
//					SendKeys(getwebelement(xml.getlocator("//locators/Tasks/Beacon")),Inputdata[125].toString());
//					Thread.sleep(5000);
//					SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/Beacon")),Keys.ENTER);
//					Thread.sleep(10000);
					Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
					Clickon(getwebelement(xml.getlocator("//locators/Tasks/TransComplete")));
					Thread.sleep(2000);
					ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Required details are filled for Transport Circuit Task");
				}
	        }
			break;

			case "Reserve MPR Resources":
			{
				getwebelement(xml.getlocator("//locators/Tasks/L3cpeName")).clear();
				Thread.sleep(1000);
				SendKeys(getwebelement(xml.getlocator("//locators/Tasks/L3cpeName")),Inputdata[126].toString());
				Thread.sleep(20000);
				SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/L3cpeName")),Keys.ENTER);
				Thread.sleep(50000);
				getwebelement(xml.getlocator("//locators/Tasks/L3cpeUNIPort")).clear();
				SendKeys(getwebelement(xml.getlocator("//locators/Tasks/L3cpeUNIPort")),Inputdata[127].toString());
				Thread.sleep(20000);
				SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/L3cpeUNIPort")),Keys.ENTER);
				Thread.sleep(20000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
				ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Required details are filled for Reserve MPR Task");
			}
			break;
			
			case "Verify SR Router and GIL":
			{
				getwebelement(xml.getlocator("//locators/Tasks/Asr")).clear();
				Thread.sleep(1000);
				getwebelement(xml.getlocator("//locators/Tasks/Asr")).clear();
				SendKeys(getwebelement(xml.getlocator("//locators/Tasks/Asr")),Inputdata[129].toString());
				Thread.sleep(20000);
				SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/Asr")),Keys.ENTER);
				Thread.sleep(20000);
				getwebelement(xml.getlocator("//locators/Tasks/AsrGil")).clear();
				SendKeys(getwebelement(xml.getlocator("//locators/Tasks/AsrGil")),Inputdata[130].toString());
				Thread.sleep(20000);
				SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/AsrGil")),Keys.ENTER);
				Thread.sleep(20000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
				ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Required details are filled for Verify SR Router Task");
			}
			break;

			case "Set Bespoke Parameters":
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				Thread.sleep(1000);
				Select(getwebelement(xml.getlocator("//locators/Tasks/WanIpSubnetSize")),Inputdata[131].toString());
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Required WAN Subnet size provided");
				Select(getwebelement(xml.getlocator("//locators/Tasks/WanIpSubnetSizeFlag")),"Yes");
				Thread.sleep(20000);
//				Select(getwebelement(xml.getlocator("//locators/Tasks/OtherWanIpSubnetSize")),Inputdata[132].toString());
//				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Required WAN Subnet size provided");
//				Clickon(getwebelement(xml.getlocator("//locators/Edit")));
//				Select(getwebelement(xml.getlocator("//locators/Tasks/Duplex")),Inputdata[123].toString());
//				Select(getwebelement(xml.getlocator("//locators/Tasks/DuplexYesorNo")),"Yes");
//				Select(getwebelement(xml.getlocator("//locators/Tasks/Speed")),Inputdata[124].toString());
//				Select(getwebelement(xml.getlocator("//locators/Tasks/SpeedYesorNo")),"Yes");
//				getwebelement(xml.getlocator("//locators/Tasks/BfdInterval")).clear();
//				SendKeys(getwebelement(xml.getlocator("//locators/Tasks/BfdInterval")),Inputdata[123].toString());
//				Select(getwebelement(xml.getlocator("//locators/Tasks/BfdIntervalYesorNo")),"No");
//				getwebelement(xml.getlocator("//locators/Tasks/Multiplier")).clear();
//				SendKeys(getwebelement(xml.getlocator("//locators/Tasks/Multiplier")),Inputdata[124].toString());
//				Select(getwebelement(xml.getlocator("//locators/Tasks/MultiplierYesorNo")),"No");
				Clickon(getwebelement(xml.getlocator("//locators/Update")));
				Thread.sleep(3000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				Thread.sleep(5000);
				Clickon(getwebelement(xml.getlocator("//locators/Edit")));
				Thread.sleep(10000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				Thread.sleep(1000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
			}
			break;
			
			case "Waiting for RIR approval":
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				Thread.sleep(1000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/ServiceInfoTab")));
				Thread.sleep(2000);
				Clickon(getwebelement(xml.getlocator("//locators/Edit")));
				Thread.sleep(2000);
				if (ConnectionType.get().equalsIgnoreCase("Primary"))
				{
					Select(getwebelement(xml.getlocator("//locators/Tasks/ManageIpRangeinRi")),"Yes");
					Thread.sleep(2000);
				}
				else
				{
					Select(getwebelement(xml.getlocator("//locators/Tasks/ManageIpRangeinRi")),"No");
					Thread.sleep(2000);
				}
				Clickon(getwebelement(xml.getlocator("//locators/Update")));
				Thread.sleep(5000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/ReturntoTask")));
				Thread.sleep(10000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
			}
			break; 
			
			case "Select LAN IP Range":
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				Thread.sleep(20000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/RequestNewIp")));
				Thread.sleep(10000);
//				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TransComplete")));
//				Thread.sleep(2000);
				ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Required details are filled for Select LAN IP Task");
			}
			break;

			case "Manual Design Task for MPR":
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				Thread.sleep(1000);
				getwebelement(xml.getlocator("//locators/Tasks/EgressPolicyMap")).clear();
				SendKeys(getwebelement(xml.getlocator("//locators/Tasks/EgressPolicyMap")),Inputdata[128].toString());
				Thread.sleep(20000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				Thread.sleep(1000);
				Clickon(getwebelement(xml.getlocator("//locators/Update")));
				Thread.sleep(10000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				Thread.sleep(1000);
				Clickon(getwebelement(xml.getlocator("//locators/Edit")));
				Thread.sleep(10000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				Thread.sleep(1000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TransComplete")));
				ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Required details are filled for Manual Design MPR Task");
			}
			break;
			
			case "Activation Start Confirmation":
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
				Thread.sleep(2000);
			}
			break;
			
			case "Select OVC Circuit"://only for OLO Scenario
			{
//				OVCCircuitRef.set("CEOS00000514470#871784216/191211-0051");
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				getwebelement(xml.getlocator("//locators/Tasks/OVCCircuit")).clear();
				SendKeys(getwebelement(xml.getlocator("//locators/Tasks/OVCCircuit")),OVCCircuitRef.get());
				Thread.sleep(60000);
				SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/OVCCircuit")),Keys.ENTER);
				Thread.sleep(60000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
				Thread.sleep(5000);
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Completed OVC Circuit");
			}
			break;
				
			case "Set/Validate Serial Number"://only for OLO Scenario
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				ANTCheck.set(GetText(getwebelement(xml.getlocator("//locators/Tasks/SerialTaskName"))));
				Log.info(ANTCheck.get());
				if (Inputdata[19].toString().contains("3rd Party Leased Line"))
				{
					SendKeys(getwebelement(xml.getlocator("//locators/Tasks/SerialNumbersetvalidate")),"C101-4752");//Inputdata[111].toString());
					Thread.sleep(60000);
					ExtentTestManager.getTest().log(LogStatus.PASS, " Step: update GX/LTS Serial Number");
					//Clickon(getwebelement(xml.getlocator("//locators/Update")));
					//ExtentTestManager.getTest().log(LogStatus.PASS, " Step: click on update button.");
				}
				else 
				{
					if (ANTCheck.get().contains("New ANT"))
					{
						Clickon(getwebelement(xml.getlocator("//locators/Tasks/UpdateAntSerialNumber")));
					}
					else 
					{
						Clickon(getwebelement(xml.getlocator("//locators/Tasks/UpdateSerialNumber")));
					}
				Thread.sleep(5000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TransComplete")));
				ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Completed Set/Validate Serial Number");
				}
			}
			break;
		
			case "Set Bandwidth Distribution Parameters":
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
				Thread.sleep(2000);
			}
			break;
			
			case "Bandwidth Profile Confirmation":
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
				Thread.sleep(2000);
			}
			break;
			
			case "Validate auto-assigned VRRP IP Addresses":
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Approve")));
				Thread.sleep(2000);
				ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Completed Validate auto-assigned VRRP IP Addresses");
			}
			break;
			
			case "MPR Trigger Configuration Start":
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				Thread.sleep(1000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
				ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Completed MPR Trigger Configuration Start");
			}
			break;

			case "Duplex Check":
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
				Thread.sleep(2000);
				ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Completed Duplex Check");
			}
			break;

			case "Speed Test":
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
				Thread.sleep(2000);
				ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Completed Speed Test");
			}
			break;

			case "Remote Connectivity Test":
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				Thread.sleep(1000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
				Thread.sleep(2000);
				ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Completed Remote Connectivity Test");
			}
			break;

			case "Test Resilience":
			{
				Thread.sleep(1000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
				ExtentTestManager.getTest().log(LogStatus.PASS, "Step: Completed Test Resilience");
			}
			break;

			case "Select Bandwidth profile":
			if(workitemcounter.get()% 2!= 0)	
			{
					//Write the steps for A end Refer the Reserver Resource Task
			}
				else
			{
					//Write the steps for B end Refer the Reserver Resource Task
			}
			break;
				
			case "Set Legacy CPE Capability Profile":
			{
				SendKeys(getwebelement(xml.getlocator("//locators/Tasks/LegacyCpeProfile")),Inputdata[75].toString());
				Thread.sleep(5000);
				SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/LegacyCpeProfile")),Keys.ENTER);
				Thread.sleep(5000);
				SendKeys(getwebelement(xml.getlocator("//locators/Tasks/LegacyCpeOamLevel")),Inputdata[76].toString());
				Thread.sleep(5000);
				SendkeaboardKeys(getwebelement(xml.getlocator("//locators/Tasks/LegacyCpeOamLevel")),Keys.ENTER);
				Thread.sleep(5000);
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/TaskTitle")));
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
			}
			break;
				
			case "Legacy Activation Completed":
			{
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));
				Thread.sleep(2000);
			}
			break;

			case "Waiting for Hard Cease Date":
			default:
				// Nothing Found it will try to complete the Task	
				Clickon(getwebelement(xml.getlocator("//locators/Tasks/Complete")));

	}
	}
	
	public void GotoDocument() throws Exception
	{		
		PerformAction.click(NC_Objects.navigationLink);
		//ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Navigate to Top Navigation link");		
		PerformAction.click(NC_Objects.documents);
		//ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Click on Document Link");
		PerformAction.waitForElementToBeVisible(NC_Objects.docLink, 10);
	}
	
	public void GotoAccount() throws Exception
	{	
		Clickon(getwebelement(xml.getlocator("//locators/Customertype")));
		PerformAction.click(NC_Objects.customerName);
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the customer type");
		Thread.sleep(10000);
		Clickon(getwebelement(xml.getlocator("//locators/AccountNameSorting")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Sorting the values");
		Thread.sleep(15000);
		Clickon(getwebelement(xml.getlocator("//locators/Customername")));
		ExtentTestManager.getTest().log(LogStatus.PASS, " Step: Select the Test Customer");
	}
}
