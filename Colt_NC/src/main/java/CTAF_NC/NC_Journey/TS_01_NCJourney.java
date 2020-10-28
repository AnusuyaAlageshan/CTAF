package CTAF_NC.NC_Journey;

import org.testng.annotations.Test;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;
import com.coltnc.common.extentreport.ExtentManager;
import com.coltnc.common.extentreport.ExtentTestManager;
//import com.qa.colt.extentreport.ExtentManager;
//import com.qa.colt.extentreport.ExtentTestManager;
import com.coltnc.common.utils.ConfigRead;
import com.coltnc.common.utils.ReadData;
import com.coltnc.common.utils.NC_Library;
//import com.colt.common.utils.Siebel_Library;
import com.coltnc.common.utils.DriverManager;
//import com.colt.common.utils.Explore_Library;
import com.coltnc.common.utils.PerformAction;
import com.coltnc.test.pageobjects.NC_Objects;

public class TS_01_NCJourney extends com.coltnc.common.utils.DriverManager{

	protected com.coltnc.common.utils.NC_Library NC_Library;	
	
	@BeforeMethod
	public void methodsetup() {		
		NC_Library = new com.coltnc.common.utils.NC_Library();		
	}
	
	public void TearDown() {
        //Extentreports log and screenshot operations for failed tests.
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();
        DriverManager.WEB_DRIVER_THREAD_LOCAL.get().close();
        throw new SkipException("Skipping this test");
	}

	@Parameters({ "iScript","iSubScript" })
	@Test
	public void FirstTestCase (String iScript, String iSubScript) throws Exception {
		
		//declaring the source values		
		String file_name =  com.coltnc.common.utils.ConfigRead.getProperty("testdata_filename");
		String Sheet_Name = "TS_01_NCJourney",sResult;	
		String Product_Name = com.coltnc.common.utils.ReadData.fngetcolvalue(file_name, Sheet_Name, iScript, iSubScript,"Product_Name");
		String Flow_Type = com.coltnc.common.utils.ReadData.fngetcolvalue(file_name, "Product_Configuration", iScript, iSubScript,"Flow_Type");
		String Environment = com.coltnc.common.utils.ConfigRead.getProperty("env");
				
//		Calling the Below method to perform C4C Login	
		NC_Library.NCLogin();
		//if (sResult.equalsIgnoreCase("False")){ TearDown(); }
		
		//Click on Navigation and navigate to Documents page
		NC_Library.GotoDocument();
		
		//Navigate to Accounts Page
		NC_Library.GotoAccount();
		
		//Selecting the Sieble reference ID 
		NC_Library.NcIntegrationCompositeOrder("inputdata");
		
		
				
}
}
	
	