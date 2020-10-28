package com.coltnc.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import com.coltnc.common.utils.ConfigRead;
//import org.testng.log4testng.Logger;

import com.coltnc.common.extentreport.ExtentManager;
import com.coltnc.common.extentreport.ExtentTestManager;
//import com.qa.colt.listeners.TestListener;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class DriverManager {
	
	
	public static final ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new InheritableThreadLocal<>();
	public static ChromeDriver driver;
	public static int  itr;
	public static int i=0;
	
    /**********************************************************************************************
     * Initialize the webdriver
     * @return status {@link boolean} - true/false
     * @author Anusuya A created Sep 28, 2020
     * @version 1.0 Sep 28, 2020
     ***********************************************************************************************/
	
	@Parameters({ "SheetName","iScript","iSubScript" })
	@BeforeMethod
	public void setup(String SheetName,String iScript,String iSubScript) throws IOException, InterruptedException
	{
		new ConfigRead("Config.properties");
		String file_name =  System.getProperty("user.dir")+"\\TestData\\CPQ_testdata.xlsx";	
		String Execution_Required = ReadData.fngetcolvalue(file_name, SheetName, iScript, iSubScript,"Execution_Required");
		String Scenario_Name = ReadData.fngetcolvalue(file_name, SheetName, iScript, iSubScript,"Scenario_Name");
		if(Execution_Required.equalsIgnoreCase("Yes")) {
			//Open Browser
			WebDriver driver = null;
			String targatedbrowser = ConfigRead.getProperty("browser");
			if(targatedbrowser.equalsIgnoreCase("chrome")) { 
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				Map<String, Object> prefs = new HashMap<String, Object>();
				// Set the notification setting it will override the default setting
				prefs.put("profile.default_content_setting_values.notifications", 2);
				prefs.put("profile.default_content_setting_values.popups", 1);
				prefs.put("download.default_directory", System.getProperty("user.dir")+"\\src\\Data\\Downloads");
				
	            // Create object of ChromeOption class
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				options.addArguments("--start-maximized");
				options.addArguments("disable-infobars");
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--force-device-scale-factor=1");
				
				//options.setExperimentalOption("excludeSwitches", "disable-popup-blocking");
				capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
				//capabilities.setCapability(CapabilityType.l, "none");
				LoggingPreferences logs = new LoggingPreferences(); 
			    logs.enable(LogType.DRIVER, Level.ALL); 
				capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability("applicationCacheEnabled", false);
				String path = System.getProperty("user.dir")+ConfigRead.getProperty("chrome_path");
				System.setProperty("webdriver.chrome.driver",path);
				driver = new ChromeDriver(capabilities);	
				
				//Removing third party cookies
				HashMap<String, Object> chromeLocalStatePrefs = new HashMap<String, Object>();

				List<String> experimentalFlags = new ArrayList<String>();                       

				experimentalFlags.add("same-site-by-default-cookies@2");

				experimentalFlags.add("cookies-without-same-site-must-be-secure@1");

				experimentalFlags.add("enable-removing-all-third-party-cookies@2");                       

				chromeLocalStatePrefs.put("browser.enabled_labs_experiments", experimentalFlags);

				options.setExperimentalOption("localState", chromeLocalStatePrefs);
			}
			WEB_DRIVER_THREAD_LOCAL.set(driver);
			Thread.sleep(60000);
			waitfordriverload();
		} else {
			System.out.println("Scenario "+Scenario_Name+ " got skipped based on user selection");
			//ExtentTestManager.endTest(); ExtentManager.getReporter().flush(); throw new SkipException("Skipping this test");
		}
	}
	
    /**********************************************************************************************
     * return the webdriver
     * @return status {@link boolean} - true/false
     * @author Anusuya A created Sep 28, 2020
     * @version 1.0 Sep 28, 2020
     ***********************************************************************************************/
	public WebDriver getwebdriver() {
		//Launching the driver
		WebDriver driver = WEB_DRIVER_THREAD_LOCAL.get();
		return driver;
	}
	
    /**********************************************************************************************
     * wait for driver to load
     * @return status {@link boolean} - true/false
     * @author Anusuya A created Sep 28, 2020
     * @version 1.0 Sep 28, 2020
     ***********************************************************************************************/
	public void waitfordriverload() throws InterruptedException {
		boolean Status = false;
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
	
	   /**********************************************************************************************
     * to capture screenshot which to be attached in the extent report
     * @return status {@link boolean} - true/false
     * @author Anusuya A created Sep 28, 2020
     * @version 1.0 Sep 28, 2020
     ***********************************************************************************************/
	
	public static String Capturefullscreenshot() throws IOException {
		String screenshot2;
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(DriverManager.WEB_DRIVER_THREAD_LOCAL.get());
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(screenshot.getImage(), "jpg", bos);
		byte[] imageBytes = bos.toByteArray();
		screenshot2 = "data:image/png;base64," + Base64.getMimeEncoder().encodeToString(imageBytes);
		bos.close();
		return screenshot2;
	}
	
	  /**********************************************************************************************
     * to clear chromedriver cache
     * @return status {@link boolean} - true/false
     * @author Anusuya A created 
     * @version 1.0 Sep 28, 2020
	 * @throws InterruptedExceJune 18, 2020ption 
     ***********************************************************************************************/
	
	public static void clearChromeCache() throws IOException, InterruptedException {
		
		DriverManager.WEB_DRIVER_THREAD_LOCAL.get().manage().deleteAllCookies();

	}
	
}

