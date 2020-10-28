package com.coltnc.test.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.coltnc.common.utils.ReadData;

public class NC_Objects {
	
	String file_name =  System.getProperty("user.dir")+"\\TestData\\CPQ_testdata.xlsx";	
	String CustomerName = ReadData.getconfigvalue(file_name, "CustomerName");
	String AccountName = ReadData.getconfigvalue(file_name, "AccountName");
	
		//NC Login Page
	
		@FindBy(xpath = "//*[@id=\"theform\"]/table/tbody/tr[2]/td[1]/img") public WebElement ncimg;	//Login page Image
		@FindBy(xpath = "//input[@id='user']") public WebElement userNameTxb;	//Username
		@FindBy(xpath = "//input[@id='pass']") public WebElement passWordTxb;	//Password
		@FindBy(xpath = "//button[@id='loginbutton']") public WebElement loginBtn;	//Login Button
				
		//NC HomePage
		@FindBy(xpath = "//*[@id=\'gen_menu_6\']") public WebElement usernameNCHome;	//Login Button
		@FindBy(xpath = "//input[@id='fast_search_val']") public WebElement searchTxb;	//Home page Search text box
		@FindBy(xpath = "//span[text()='COLT Resource Inventory Search Folder']/parent::*") public WebElement resourceInventorySrch;	//Password
		@FindBy(xpath = "//span[text()='COLT Circuit Search Profile']/parent::a)[2]") public WebElement circuitSrch;
		@FindBy(xpath = "//*[@id='gen_menu_3']") public WebElement navigationLink;
		@FindBy(xpath = "//span[text()='Documents']/parent::*") public WebElement documents;
		
		//Documents Page
		@FindBy(xpath = "//span[text()='COLT OM Customers']/parent::*") public WebElement customerName;
		@FindBy(xpath = "//*[@id=\"t91000_94060_t\"]/tbody/tr[17]/td[2]/a/span") public WebElement docLink;
		
		//CustomerAccounts Page
		@FindBy(xpath = "//a[text()='Name'") public WebElement accNameSorting;
		@FindBy(xpath = "/html/body/div[4]/div[1]/div[1]/div[2]/h1/a") public WebElement pageTitle;
		@FindBy(xpath = "//*[@id=\"id_tab_0\"]/a") public WebElement customerAccTab;
		@FindBy(xpath = "//span[text()='ZZZZ ES TEST']/parent::*") public WebElement accName;
		
		//Account Information
		@FindBy(xpath = "//a[contains(text(),'Name')]/parent::*/following-sibling::*") public WebElement nameFiltering;
		@FindBy(xpath = "//*[@id=\"id_tab_6\"]/a") public WebElement accpageTab;		
		@FindBy(xpath = "//*[@class='inputs']") public WebElement filterSelectName;
		@FindBy(xpath = "//*[@name='inp']") public WebElement filterInputVal;
		@FindBy(xpath = "//*[@class='FilterButtons'] //*[text()='Apply']") public WebElement applyBtn;
		@FindBy(xpath = "//*[text()='Accounts']/parent::*") public WebElement siebelAcc;
		@FindBy(xpath = "//*[text()='Account Number(OCN)']/parent::*/following-sibling::*//input") public WebElement siebelOCN;
		@FindBy(xpath = "//*[text()='Go']/parent::*") public WebElement siebelAccGo;
		@FindBy(xpath = "//*[contains(@title,'ZZZZ ES TEST')]") public WebElement siebelCstName;
		
		//Siebel -> NC
		@FindBy(xpath = "//a[contains(text(),'Product ID')]/parent::*/following-sibling::*") public WebElement ProdIdFiltering;
		@FindBy(xpath = "//a[contains(text(),'Order Number')]/parent::*/following-sibling::*") public WebElement OrderNumFiltering;
		@FindBy(xpath = "//td/select[@class='inputs']") public WebElement FilterSelect;
		@FindBy(xpath = "//div/input[@class='inputs']") public WebElement IntegFilterInputValue;
		@FindBy(xpath = "//div/input[@class='inputs']/following-sibling::*/child::*") public WebElement SelectLink;
		@FindBy(xpath = "//a/span[contains(text(),'Order #')]/parent::*") public WebElement OrderLink;
				
		//NC COLT Circuit Search Profile
		@FindBy(xpath = "//b[contains(text(),'Circuit Name')]/following::input)[1]") public WebElement circuitName;	//circuit name
		@FindBy(xpath = "//div/a[@id='searchButton']") public WebElement searchBtn;	//circuit search button
		@FindBy(xpath = "//div[@id='w_searchButton']//a") public WebElement circuitsearchBtn;	//circuit search button
		
		//Tasks - WorkItems
		@FindBy(xpath = "//a[text()='Tasks']") public WebElement taskTab;
		@FindBy(xpath = "//a[text()='Execution Flow']/parent::*/following-sibling::*//a[2]") public WebElement ExecutionFlowlink;
		@FindBy(xpath = "//div[@id='user-task-context']//span[contains(text(),'[New]')]") public WebElement TaskTitle;
		@FindBy(xpath = "//a[text()='Work Items']") public WebElement Workitems;
		@FindBy(xpath = "//a[text()='Scheduler']") public WebElement Scheduler;
		@FindBy(xpath = "//a[text()='Errors']") public WebElement Errors;
		@FindBy(xpath = "//td[text()='Ready']/parent::*/td//a") public WebElement TaskReadytoComplete;
		@FindBy(xpath = "//td/a[text()='Access Network Element']/parent::*/following-sibling::*//input[@type='text']") public WebElement AccessNetworkElement;
		@FindBy(xpath = "//td/a[text()='Access Port']/parent::*/following-sibling::*//input[@type='text']") public WebElement AccessPort;
		@FindBy(xpath = "//*[text()='CPE NNI Port 1']/parent::*/following-sibling::*//input[@type='text']") public WebElement CPENNIPort1;
		@FindBy(xpath = "//*[text()='CPE NNI Port 2']/parent::*/following-sibling::*//input[@type='text']") public WebElement CPENNIPort2;
		@FindBy(xpath = "//*[text()='PE Network Element']/parent::*/following-sibling::*//input[@type='text']") public WebElement PENetworkElement;
		@FindBy(xpath = "//*[text()='PE Port']/parent::*/following-sibling::*//input[@type='text']") public WebElement PePort;
		@FindBy(xpath = "//*[text()='VCX Controller']/parent::*/following-sibling::*//input[@type='text']") public WebElement VCXController;
		@FindBy(xpath = "//*[text()='Beacon']/parent::*/following-sibling::*//input[@type='text']") public WebElement Beacon;
		@FindBy(xpath = "(//span[text()='Ethernet Link Transport RFS']/parent::*)[1]") public WebElement EthernetLinkTransportRFS_1;
		@FindBy(xpath = "(//span[text()='Ethernet Link Transport RFS']/parent::*)[2]") public WebElement EthernetLinkTransportRFS_2;
		@FindBy(xpath = "//td/a[text()='L3 CPE']/parent::*/following-sibling::*//input[@type='text']") public WebElement L3cpeName;
		@FindBy(xpath = "//td/a[text()='L3 CPE UNI Port']/parent::*/following-sibling::*//input[@type='text']") public WebElement L3cpeUNIPort;
		@FindBy(xpath = "//td/a[text()='Egress Policy Map']/parent::*/following-sibling::*//input[@type='text']") public WebElement EgressPolicyMap;
		@FindBy(xpath = "//td/a[text()='ASR']/parent::*/following-sibling::*//input[@type='text']") public WebElement Asr;
		@FindBy(xpath = "//td/a[text()='ASR GIL']/parent::*/following-sibling::*//input[@type='text']") public WebElement AsrGil;
		@FindBy(xpath = "//a[text()='Service Information']") public WebElement ServiceInfoTab;
		@FindBy(xpath = "//a[text()='Manage IP Range in RI']/parent::*/following-sibling::*//select") public WebElement ManageIpRangeinRi;
		@FindBy(xpath = "//a[@tooltip='Return to the Task']") public WebElement ReturntoTask;
		@FindBy(xpath = "//*[text()='OAM Profile']/parent::*/following-sibling::*//input[@type='text']") public WebElement OAMProfile;
		@FindBy(xpath = "//td/a[text()='Name']/parent::*/following-sibling::*[1]") public WebElement SerialTaskName;
		@FindBy(xpath = "//a[text()='Update ANT Serial Number']") public WebElement UpdateAntSerialNumber;
		@FindBy(xpath = "//a[text()='Update Serial Number']") public WebElement UpdateSerialNumber; 
		@FindBy(xpath = "//td/a[text()='LAN IP Address']/parent::*/following-sibling::*//input[@type='text']") public WebElement LanIpAddress;
		@FindBy(xpath = "//a[text()='Request New IP Range in EIP']") public WebElement RequestNewIp;
		@FindBy(xpath = "//*[text()='Release LAN Range']/parent::*/following-sibling::*//select") public WebElement ReleaseLanRange;
		@FindBy(xpath = "//a[contains(text(),'Order #')]") public WebElement linkforOrder;
		@FindBy(xpath = "//td/a[text()='Order Number']/parent::*/following-sibling::*//input") public WebElement OrderNumber;
		
		//Added for Cease Work Items
		@FindBy(xpath = "//a[text()='Bespoke HQoS Entries']") public WebElement BespokeHQoSEntries;
		@FindBy(xpath = "//td/a[text()='WAN IPv4 Subnet Size']/parent::*/following-sibling::*//select") public WebElement WanIpSubnetSize;
		@FindBy(xpath = "(//td/a[text()='WAN IPv4 Subnet Size']/parent::*/following-sibling::*//select)[2]") public WebElement WanIpSubnetSizeFlag;
		@FindBy(xpath = "//td/a[text()='Other WAN IPv4 Subnet Size']/parent::*/following-sibling::*//input") public WebElement OtherWanIpSubnetSize;
		@FindBy(xpath = "//td/a[text()='duplex']/parent::*/following-sibling::*//select") public WebElement Duplex;
		@FindBy(xpath = "(//td/a[text()='duplex']/parent::*/following-sibling::*//select)[2]") public WebElement DuplexYesorNo;
		@FindBy(xpath = "//td/a[text()='speed']/parent::*/following-sibling::*//select") public WebElement Speed;
		@FindBy(xpath = "(//td/a[text()='speed']/parent::*/following-sibling::*//select)[2]") public WebElement SpeedYesorNo;
		@FindBy(xpath = "//td/a[text()='bfd interval/min_rx']/parent::*/following-sibling::*//input") public WebElement BfdInterval;
		@FindBy(xpath = "//td/a[text()='bfd interval/min_rx']/parent::*/following-sibling::*//select") public WebElement BfdIntervalYesorNo;
		@FindBy(xpath = "//td/a[text()='multiplier']/parent::*/following-sibling::*//input") public WebElement Multiplier;
		@FindBy(xpath = "//td/a[text()='multiplier']/parent::*/following-sibling::*//select") public WebElement MultiplierYesorNo;
		@FindBy(xpath = "//td/a[text()='Premium CIR %']/parent::*/following-sibling::*//input[@type='text']") public WebElement PremiumCir;
		@FindBy(xpath = "//td/a[text()='Internet CIR %']/parent::*/following-sibling::*//input[@type='text']") public WebElement InternetCir;
		@FindBy(xpath = "//*[text()='Legacy CPE Profile']/parent::*/following-sibling::*//input[@type='text']") public WebElement LegacyCpeProfile; 
		@FindBy(xpath = "//*[text()='Legacy CPE OAM Level']/parent::*/following-sibling::*//input[@type='text']") public WebElement LegacyCpeOamLevel;
		@FindBy(xpath = "//a[@tooltip='Complete']") public WebElement Complete;
		@FindBy(xpath = "//a[text()='Complete']") public WebElement TransComplete;
		@FindBy(xpath = "//a/span[contains(text(),'Reserve Access Resources')]/parent::*/parent::*/parent::*/td//input") public WebElement WorkItemSelect;
		@FindBy(xpath = "//a[text()='View']") public WebElement View;
		@FindBy(xpath = "//a/span[contains(text(),'\"+arrOfStr1[1]+\"')]/parent::*/parent::*/following-sibling::td[contains(text(),\"Process Completed\")]") public WebElement OrderCompleted;
		
}
