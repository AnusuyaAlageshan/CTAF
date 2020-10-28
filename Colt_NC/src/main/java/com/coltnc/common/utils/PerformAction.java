package com.coltnc.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import com.relevantcodes.extentreports.LogStatus;
import com.coltnc.common.extentreport.ExtentManager;
import com.coltnc.common.extentreport.ExtentTestManager;
import com.coltnc.common.utils.DriverManager;


/**
 * Class having web web application specific reusable methods
 * @author Savita Tambe
 *
 */
public class PerformAction extends DriverManager {	    
	public static void launchWebApp(String url) {		
		DriverManager.WEB_DRIVER_THREAD_LOCAL.get().navigate().to(url);
    	try {
    			DriverManager.WEB_DRIVER_THREAD_LOCAL.get().manage().window().maximize();
    	} catch(Exception e) {
            ExtentTestManager.getTest().log(LogStatus.ERROR, " Could not launch application: " + url+ "due to " +e.toString());
            System.out.println("Could not launch application: " + url+ "due to " +e.toString());
            TearDown();
    	}
    	
        ExtentTestManager.getTest().log(LogStatus.PASS, "Launched application: " + url);
    }
	    
	/**********************************************************************************************
	 * Waits for web element and clicks on it
	 * 
	 * @param webElement {@link WebElement} - WebElement to click
	 * @return status {@link boolean} - true/false
	 * @author Savita Tambe created May 31, 2018
	 * @version 1.0 May 31, 2018
	 * @throws InterruptedException 
	 ***********************************************************************************************/
	static String sException = null; static int j = 0;
    public static boolean click(WebElement webElement) {
    	boolean status = false;
    	
    	try {
    			webElement.click();
    			status = true;
    			
		} catch (Exception e1) {
			for (int i = 0; i <= 10; ++i) {
				try {
						Thread.sleep(3000);
						waitForElementToBeVisible(webElement, 5);
						scrollIntoView(webElement);
						Thread.sleep(250);
		    			webElement.click();
		    			status = true;
						break;
				} catch (Exception e) {
					sException = e.toString();
					j = i+1;
					continue;
				}	
			}
		}
		if (j > 10) {
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to click webelement: " + webElement.toString()+ "due to " +sException);
			System.out.println("Unable to click webelement: " + webElement.toString()+ "due to " +sException);
			TearDown();
		}
		
		return status;
	}
    
    /**********************************************************************************************
     * Clicks the identified web element by javascript.
     * 
     * @param webElement {@link WebElement} - WebElement to click
     * @return status {@link boolean} - true/false
     * @author Savita Tambe created May 11, 2018
     * @version 1.0 May 11, 2018
     ***********************************************************************************************/
     public static boolean clickByJS(WebElement webElement) {
    	 boolean status = false;     
    	 JavascriptExecutor js = null;
    	 
    	 try {
    		 	waitForElementToBeVisible(webElement, 60);
    		 	js = (JavascriptExecutor) DriverManager.WEB_DRIVER_THREAD_LOCAL.get();
    		 	js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid blue;');", webElement);
    		 	js.executeScript("arguments[0].removeAttribute('style', 'border: 2px solid blue;');", webElement);
     			js.executeScript("arguments[0].click();", webElement);
     			status = true;
     			
	     } catch (Exception e) {
	    	ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to clickByJS webelement: " + webElement.toString()+ "due to " +e.toString());
	    	System.out.println("Unable to click webelement: " + webElement.toString()+ "due to " +e.toString());
	    	TearDown();
	     }
		 
		 return status;
     }
     
     /**********************************************************************************************
      * Clicks the identified web element by Action.
      * 
      * @param webElement {@link WebElement} - WebElement to click
      * @return status {@link boolean} - true/false
      * @author Savita Tambe created May 11, 2018
      * @version 1.0 May 11, 2018
      ***********************************************************************************************/
      public static boolean clickByAction(WebElement webElement) {
     	 boolean status = false;     
     	 
     	 try {
     		 	waitForElementToBeVisible(webElement, 60);
     		 	Actions build = new Actions(DriverManager.WEB_DRIVER_THREAD_LOCAL.get());
        		build.moveToElement(webElement).click().build().perform();
      			status = true;
      			
 	     } catch (Exception e) {
 	    	ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to ActionClick the webelement: " + webElement.toString()+ "due to " +e.toString());
 	    	System.out.println("Unable to ActionClick webelement: " + webElement.toString()+ "due to " +e.toString());
 	    	TearDown();
 	     }
 		 
 		 return status;
      }
     
    /**********************************************************************************************
     * Waits for web element and set text in it
     * 
     * @param webElement {@link WebElement} - WebElement to enter text
     * @param text {@link String} - Text to enter
     * @return status {@link boolean} - true/false
     * @author Savita Tambe created May 31, 2018
     * @version 1.0 May 31, 2018
     ***********************************************************************************************/
    public static boolean sendKeys(WebElement webElement, String text) {
    	boolean status = false;
      	
    	try {
    			waitForElementToBeVisible(webElement, 60);
				webElement.clear();
				webElement.sendKeys(text);
				status = true;
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to set text in webelement: " + webElement.toString()+ "due to " +e.toString());
	    	System.out.println("Unable to set text in webelement: " + webElement.toString()+ "due to " +e.toString());
	    	TearDown();
		}
		
		return status;
	}
    
    /**********************************************************************************************
     * Waits for web element and set text in it and hit the sendkeys to proceed further
     * 
     * @param webElement {@link WebElement} - WebElement to enter text
     * @param text {@link String} - Text to enter
     * @return status {@link boolean} - true/false
     * @author Savita Tambe created May 31, 2018
     * @version 1.0 May 31, 2018
     ***********************************************************************************************/
    public static boolean sendKeysWithKeys(WebElement webElement, String text, String sKeys) {
    	boolean status = false;
      	
    	try {
    			waitForElementToBeVisible(webElement, 60);
				webElement.clear();
				webElement.sendKeys(text);
				pause(1500);
				switch (sKeys.toUpperCase()) {
					case "ENTER":
						webElement.sendKeys(Keys.ENTER);
						break;
					case "TAB":
						webElement.sendKeys(Keys.TAB);
						break;
				}
				status = true;
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to set text in webelement: " + webElement.toString()+ "due to " +e.toString());
	    	System.out.println("Unable to set text in webelement: " + webElement.toString()+ "due to " +e.toString());
	    	TearDown();
		}
		
		return status;
	}
    
    /**********************************************************************************************
     * Sends text in element with javascript.
     * 
     * @param webElement {@link WebElement} - WebElement to click
     * @param value {@link String} - Value to be entered
     * @author Savita Tambe created May 11, 2018
     * @version 1.0 May 11, 2018
     * @throws InterruptedException 
     * @throws SocketTimeoutException 
     ***********************************************************************************************/
    public static void sendKeysByJS(WebElement webElement, String value) throws InterruptedException, SocketTimeoutException {
    	JavascriptExecutor js = null;
   		
    	js = (JavascriptExecutor) DriverManager.WEB_DRIVER_THREAD_LOCAL.get();
    	js.executeScript("arguments[0].value='"+ value +"';", webElement);
    }
    
    
    /**********************************************************************************************
     * Waits for web element and selects value from drop down
     * 
     * @param webElement {@link WebElement} - WebElement to select value
     * @param value {@link String} - Value to select
     * @return status {@link boolean} - true/false
     * @author Savita Tambe created May 31, 2018
     * @version 1.0 May 31, 2018
     ***********************************************************************************************/
    public static boolean selectByValue(WebElement webElement, String value) {
    	boolean status = false;
      	
    	try {
    			waitForElementToBeVisible(webElement, 60);	
				Select listBox = new Select(webElement);
				listBox.selectByVisibleText(value);			
				status = true;
				
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to select the value from listbox: " + webElement.toString()+ "due to " +e.toString());
	    	System.out.println("Unable to select the value from listbox: " + webElement.toString()+ "due to " +e.toString());
	    	TearDown();
		}
		
		return status;
	}
    
    /**********************************************************************************************
     * Waits for web element and selects value from drop down based on div class
     * 
     * @param webElement {@link WebElement} - WebElement to select value
     * @param value {@link String} - Value to select
     * @return status {@link boolean} - true/false
     * @author Savita Tambe created May 31, 2018
     * @version 1.0 May 31, 2018
     ***********************************************************************************************/
    public static boolean selectByValueDIV(WebElement mainElement, WebElement listElement, String value) {
    	boolean status = false; boolean sFlag = false;
      	
    	try {
    		scrollIntoView(mainElement);
    		Actions build = new Actions(DriverManager.WEB_DRIVER_THREAD_LOCAL.get());
    		build.moveToElement(mainElement).click().build().perform();
    		isPresent(listElement,15);
    		List<WebElement> options = listElement.findElements(By.tagName("li"));
			for (WebElement option : options)
			{
			    if (option.getText().equals(value))
			    {
			    	scrollIntoView(option);
			    	click(option);
//			        option.click();
			        Thread.sleep(1250);
			        sFlag = true;
			        break;
			    }
			}	
			if (sFlag = false) {
				System.out.println("Unable to select the value "+value+" from listbox: " + listElement.toString());
				ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to select the value "+value+" from listbox: " + listElement.toString());
				TearDown();
			}
				status = true;
				
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to select the value from listbox: " + listElement.toString()+ "due to " +e.toString());
	    	System.out.println("Unable to select the value from listbox: " + listElement.toString()+ "due to " +e.toString());
	    	TearDown();
		}
		
		return status;
	}
    
    /**********************************************************************************************
     * Waits for web element and selects value from drop down based on text
     * 
     * @param webElement {@link WebElement} - WebElement to select value
     * @param value {@link String} - Value to select
     * @return status {@link boolean} - true/false
     * @author Savita Tambe created May 31, 2018
     * @version 1.0 May 31, 2018
     * @throws InterruptedException 
     ***********************************************************************************************/
    public static boolean selectByText(WebElement listElement, String value) throws InterruptedException {
    	boolean status = false;
      	
		scrollIntoView(listElement);
		List<WebElement> options = listElement.findElements(By.tagName("option"));
		for (WebElement option : options)
		{
		    if (option.getText().equals(value)) {
		    	scrollIntoView(option);
		    	click(option);
		        Thread.sleep(1250);
		        status = true;
		        break;
		    }
		}	
		if (status = false) {
			System.out.println("Unable to select the value "+value+" from listbox: " + listElement.toString());
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to select the value "+value+" from listbox: " + listElement.toString());
			TearDown();
		}
		
		return status;
	}
    
    /**********************************************************************************************
     * Waits for web element and selects value from drop down
     * 
     * @param webElement {@link WebElement} - WebElement to select value
     * @param value {@link String} - Value to select
     * @return status {@link boolean} - true/false
     * @author Savita Tambe created May 31, 2018
     * @version 1.0 May 31, 2018
     ***********************************************************************************************/
    public static boolean selectByVisibleText(WebElement webElement, String value) {
    	boolean status = false;
      	
    	try {
    			waitForElementToBeVisible(webElement, 60);	
				Select listBox = new Select(webElement);
				listBox.selectByVisibleText(value);			
				status = true;
				
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to select the value from listbox: " + webElement.toString()+ "due to " +e.toString());
	    	System.out.println("Unable to select the value from listbox: " + webElement.toString()+ "due to " +e.toString());
	    	TearDown();
		}
		
		return status;
	}
    
    /**********************************************************************************************
     * Waits for web element and selects value from drop down
     * 
     * @param webElement {@link WebElement} - WebElement to select value
     * @param index {@link int} - Value index to select
     * @return status {@link boolean} - true/false
     * @author Savita Tambe created May 31, 2018
     * @version 1.0 May 31, 2018
     ***********************************************************************************************/
    public static boolean selectByIndex(WebElement webElement, int index) {
    	boolean status = false;
		try {
				waitForElementToBeVisible(webElement, 60);		
				Select listBox = new Select(webElement);
				listBox.selectByIndex(index);
				status = true;
				
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to select the value from listbox: " + webElement.toString()+ "due to " +e.toString());
	    	System.out.println("Unable to select the value from listbox: " + webElement.toString()+ "due to " +e.toString());
	    	TearDown();
		}
		
		return status;
	}
    
    /**********************************************************************************************
     * Waits for web element and clears text in it
     * 
     * @param webElement {@link WebElement} - WebElement to clear text
     * @return status {@link boolean} - true/false
     * @author Savita Tambe created May 31, 2018
     * @version 1.0 May 31, 2018
     ***********************************************************************************************/
    public static boolean clear(WebElement webElement) {
    	boolean status = false;
		try {
				waitForElementToBeVisible(webElement, 60);
				webElement.clear();
				status = true;
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to clear text in webelement: " + webElement.toString()+ "due to " +e.toString());
	    	System.out.println("Unable to clear text in webelement: " + webElement.toString()+ "due to " +e.toString());
	    	TearDown();
		}
		
		return status;
	}     
    
     
    /**********************************************************************************************
     * Waits for web element visibility
     * 
     * @param webElement {@link WebElement} - WebElement to wait for visibility
     * @param timeOut {@link int} - The amount of time in milliseconds to pause
     * @return status {@link boolean} - true/false
     * @author Savita Tambe created May 31, 2018
     * @version 1.0 May 31, 2018
     ***********************************************************************************************/
    public static boolean waitForElementToBeVisible(WebElement webElement, int timeOut) {
    	boolean status = false;
    	
    	try {							
				FluentWait<WebDriver> fluentWait = new FluentWait<>(DriverManager.WEB_DRIVER_THREAD_LOCAL.get())    		     
    				.withTimeout(timeOut, TimeUnit.SECONDS)
    		        .pollingEvery(1000, TimeUnit.MILLISECONDS)
    		        .ignoring(NoSuchElementException.class);  
    				fluentWait.until(ExpectedConditions.elementToBeClickable(webElement));
    				status = true;
		} catch (Exception e) {
//			ExtentTestManager.getTest().log(LogStatus.ERROR, "Webelement is not present: " + webElement.toString()+ "due to " +e.toString());
//			System.out.println("Webelement is not Visible: " + webElement.toString()+ "due to " +e.toString());
		}
		
		return status;
	}
    
    /**********************************************************************************************
     * Waits for web element to be non visible
     * 
     * @param webElement {@link WebElement} - WebElement to wait for non visibility
     * @param timeOut {@link int} - The amount of time in milliseconds to pause
     * @return status {@link boolean} - true/false
     * @author Savita Tambe created May 31, 2018
     * @version 1.0 May 31, 2018
     ***********************************************************************************************/
 
	public static boolean waitForInvisibilityOfElement(WebElement webElement, int timeOut) {
    	boolean status = false;
    	
    	try {	  	
    			FluentWait<WebDriver> fluentWait = new FluentWait<>(DriverManager.WEB_DRIVER_THREAD_LOCAL.get()) 
					.withTimeout(timeOut, TimeUnit.SECONDS)
    		        .pollingEvery(1000, TimeUnit.MILLISECONDS)
    		        .ignoring(NoSuchElementException.class);
    				fluentWait.until(ExpectedConditions.invisibilityOf(webElement));
    				status = true;
							
		} catch (Exception e) {
//			ExtentTestManager.getTest().log(LogStatus.ERROR, "Webelement was still Visible: " + webElement.toString()+ "due to " +e.toString());
			System.out.println("Webelement was still visible: " + webElement.toString()+ "due to " +e.toString());
		}
		
		return status;
	}
	
	public static boolean ClickonElementByString(String xPath, int timeOut) throws IOException, InterruptedException {
		/*----------------------------------------------------------------------
		Method Name : WaitforC4Cloader
		Purpose     : This method will wait C4C application while loading page occurs
		Designer    : Vasantharaja C
		Created on  : 1st April 2020 
		Input       : None
		Output      : None
		 ----------------------------------------------------------------------*/ 
		
		boolean status = false; boolean sFlag = false;
				
		int i = 1;
		Thread.sleep(2500);   
		WebDriver driver = DriverManager.WEB_DRIVER_THREAD_LOCAL.get();
          try {
        	  while (driver.findElement(By.xpath(xPath)).isDisplayed()) {   
        		  if (i > timeOut) { status = false; break; }
                      Thread.sleep(2000);
                      driver.findElement(By.xpath(xPath)).isEnabled();
                      scrollIntoView(driver.findElement(By.xpath(xPath)));
                      driver.findElement(By.xpath(xPath)).click();
//                      System.out.println("Waiting for an element "+xPath+" to get clicked");
                      sFlag = true;
                      i = i+1;
                } 
          } catch(Exception e) {
              System.out.println("Element "+xPath+" Click is done");
              status = true;
          }
          
          if(sFlag = false) {
        	  ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to Click on the webelement: " + driver.findElement(By.xpath(xPath)).toString());
        	  System.out.println("Unable to Click on the webelement: " + driver.findElement(By.xpath(xPath)).toString());
        	  TearDown();
        	  
          }
          
          return status;
	}
    
    /**********************************************************************************************
     * Verifies element is present
     * 
     * @param webElement {@link WebElement} - WebElement to wait for visibility
     * @param timeOut {@link Integer} - The amount of time in milliseconds to pause.
     * @return status {@link boolean} - true/false
     * @author Savita Tambe created May 30, 2018 
     * @version 1.0 May 30, 2018   
     ***********************************************************************************************/    
   	public static boolean isPresent(WebElement webElement, int timeOut) {
	   	boolean status = false;
	   	
	   	waitForElementToBeVisible(webElement, timeOut);   
	   	try {
   			status = webElement.isDisplayed();
   			status = true;
	   	} catch(Exception e) {
	   		ExtentTestManager.getTest().log(LogStatus.ERROR, "Webelement is not present: " + webElement.toString()+ "due to " +e.toString());
	   		System.out.println("Webelement is not present: " + webElement.toString()+ "due to " +e.toString());
	   		TearDown();
	   	}
			  
	    return status;
   	} 
   	 
   	/**********************************************************************************************
     * Verifies element is enabled
     * 
     * @param webElement {@link WebElement} - WebElement to verify if enabled
     * @return status {@link boolean} - true/false
     * @author Savita Tambe created May 30, 2018 
     * @version 1.0 May 30, 2018
     ***********************************************************************************************/    
   	public static boolean isEnabled(WebElement webElement) {
	   	boolean status = false;
	   	waitForElementToBeVisible(webElement, 60);   
	   	try {
   			status = webElement.isEnabled();	
	   	} catch(Exception e) {
	   		ExtentTestManager.getTest().log(LogStatus.ERROR, "Webelement is disabled: " + webElement.toString()+ "due to " +e.toString());
	   		System.out.println("Webelement is disabled: " + webElement.toString()+ "due to " +e.toString());
	   		TearDown();
	   	}
		  
       return status;
   	} 
    
    /**********************************************************************************************
    * Determines if an element has a specific text value or not.
    * 
    * @param webElement {@link WebElement} - WebElement to verify text 
    * @param text {@link String} - Text to evaluate
    * @return status {@link boolean} - true/false
    * @author Savita Tambe created Maay 08, 2018 
    * @version 1.0 May 08, 2018
    ***********************************************************************************************/
    public static boolean verifyText(WebElement webElement, String text) {
	   	boolean status = false;
	   	
	   	waitForElementToBeVisible(webElement, 60);
	   	try { 	   		
	   			status = webElement.getText().equalsIgnoreCase(text);  
        } catch (Exception e) {
        	ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to verify text for webelement: " + webElement.toString()+ "due to " +e.toString());
        	System.out.println("Unable to Verify text for the webelement: " + webElement.toString()+ "due to " +e.toString());
        	TearDown();
        }
	   	
        return status;
    }
    
    /**********************************************************************************************
     * Fetches elements specific attribute value
     * 
     * @param webElement {@link WebElement} - WebElement to get attribute
     * @param attribute {@link String} - The specific attribute type to fetch value   
     * @return attributeValue {@link String} - The specific attribute value   
     * @author Savita Tambe created May 30, 2018 
     * @version 1.0 May 30, 2018     
     ***********************************************************************************************/
     public static String getAttribute(WebElement webElement, String attribute) {
    	String attributeValue = "";   
    	
    	waitForElementToBeVisible(webElement, 60);
 	   	try {
 	   			attributeValue = webElement.getAttribute(attribute);
         } catch (Exception e) {
        	ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to get attribute for webelement: " + webElement.toString()+ "due to " +e.toString());
        	System.out.println("Unable to get attribute for the webelement: " + webElement.toString()+ "due to " +e.toString());
        	TearDown();
         }
 	   	
         return attributeValue;
     }
        
     /**********************************************************************************************
	 * Determines if an element has a specific attribute value or not.
	 * 
	 * @param webElement {@link WebElement} - WebElement to verify attribute
	 * @param attribute {@link String} - The specific attribute type to evaluate
	 * @param attributeValue {@link String} - The value of the attribute to evaluate
	 * @return status {@link boolean} - true/false
	 * @author Savita Tambe created May 30, 2018 
	 * @version 1.0 May 30, 2018
	 ***********************************************************************************************/
     public static boolean verifyAttributeValue(WebElement webElement, String attribute, String attributeValue) {
      	boolean status = false;
      	
      	waitForElementToBeVisible(webElement, 60);
   	   	try { 	   		
   	   			status = webElement.getAttribute(attribute).equalsIgnoreCase(attributeValue);  
           } catch (Exception e) {     
           	    ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to get attribute for webelement: " + webElement.toString()+ "due to " +e.toString());
        	    System.out.println("Unable to get attribute for the webelement: " + webElement.toString()+ "due to " +e.toString());
        	    TearDown();
           }
   	   	
           return status;
      }
       
     /**********************************************************************************************
      * Determines if an element has a specific attribute value or not.
      * 
      * @param webElement {@link WebElement} - WebElement to verify attribute
      * @param attribute {@link String} - The specific attribute type to evaluate
      * @param attributeValue {@link String} - The value of the attribute to evaluate
      * @return status {@link boolean} - true/false
      * @author Savita Tambe created May 30, 2018 
      * @version 1.0 May 30, 2018
      ***********************************************************************************************/
     public static boolean verifyAttributeContains(WebElement webElement, String attribute, String attributeValue) {
    	 boolean status = false;   

    	 waitForElementToBeVisible(webElement, 60);    	
    	 try {
		  		status = webElement.getAttribute(attribute).toUpperCase().contains(attributeValue.toUpperCase());  	               	   	 	
	   	 } catch (Exception e) {  	 
	   		ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to verify attribute contains for webelement: " + webElement.toString()+ "due to " +e.toString());
    	    System.out.println("Unable to verify attribute contains for webelement: " + webElement.toString()+ "due to " +e.toString());
    	    TearDown();
	   	 }
        return status;
    }
     
	 /**********************************************************************************************
	  * Waits for element to have specific attribute value.
	  * 
	  * @param webElement {@link WebElement} - WebElement to verify attribute
	  * @param attribute {@link String} - The specific attribute type to evaluate
	  * @param attributeValue {@link String} - The value of the attribute to evaluate
	  * @param timeOut {@link int} - The value of the timeout
	  * @return status {@link boolean} - true/false
	  * @author Savita Tambe created May 30, 2018 
	  * @version 1.0 May 30, 2018
	  ***********************************************************************************************/
     public static boolean waitForAttributeContains(WebElement webElement, String attribute, String attributeValue, int timeOut) {
	    boolean status = false;   
	          
	    try {
	          WebDriverWait wait = new WebDriverWait(DriverManager.WEB_DRIVER_THREAD_LOCAL.get(), timeOut);
	          wait.until(ExpectedConditions.attributeContains(webElement, attribute, attributeValue));                  
	          status = true;
              
       } catch (Exception e) {
       	    ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to verify attribute contains for webelement: " + webElement.toString()+ "due to " +e.toString());
	   	    System.out.println("Unable to verify attribute contains for webelement: " + webElement.toString()+ "due to " +e.toString());
	   	    TearDown();
       }
	    return status;
	 } 
	 
	 /**********************************************************************************************
	  * Scroll to element
	  * 
	  * @param webElement {@link WebElement} - WebElement to verify attribute
	  * @return status {@link boolean} - true/false
	  * @author Savita Tambe created May 30, 2018 
	  * @version 1.0 May 30, 2018
	  ***********************************************************************************************/
	 public static boolean scrollIntoView(WebElement webElement) {
		 boolean status = false;
	    	
		 try {
			 	waitForElementToBeVisible(webElement, 60);
				String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                        + "var elementTop = arguments[0].getBoundingClientRect().top;"
                        + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
			 	((JavascriptExecutor) DriverManager.WEB_DRIVER_THREAD_LOCAL.get()).executeScript(scrollElementIntoMiddle, webElement);
			 	isEnabled(webElement);
			 	Thread.sleep(250);
			 	status = true;
			 } catch (Exception e) {
				ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to scroll to webelement: " + webElement.toString()+ "due to " +e.toString());
				System.out.println("Unable to scroll to webelement: " + webElement.toString()+ "due to " +e.toString());
				TearDown();
			 }
			
		 return status;
	 }
	 
	 /**********************************************************************************************
	  * Scroll to top of the page
	  * 
	  * @param webElement {@link WebElement} - WebElement to verify attribute
	  * @return status {@link boolean} - true/false
	  * @author Savita Tambe created May 30, 2018 
	  * @version 1.0 May 30, 2018
	  ***********************************************************************************************/
	 public static boolean scrollIntoTop() {
		 boolean status = false;
	    	
		 try {
			 	((JavascriptExecutor) DriverManager.WEB_DRIVER_THREAD_LOCAL.get()).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
			 } catch (Exception e) {
				ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to scroll to top of the page, please verify");
				System.out.println("Unable to scroll to top of the page, please verify");
				TearDown();
			 }
			
		 return status;
	 }
		    
	 /**********************************************************************************************
	  * Switch to frame
	  * 
	  * @param idNameIndex {@link String} - Id, Name or Index of the frame
	  * @return status {@link boolean} - true/false
	  * @author Savita Tambe created May 30, 2018 
	  * @version 1.0 May 30, 2018
	  ***********************************************************************************************/
	 public static boolean switchToFrame(String idNameIndex) {
		 boolean status = false;
		 try {
              DriverManager.WEB_DRIVER_THREAD_LOCAL.get().switchTo().frame(idNameIndex);
              status = true;
		 } catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to switch to frame: " + idNameIndex+ "due to " +e.toString());
			System.out.println("Unable to switch to frame: " + idNameIndex + "due to " +e.toString());
			TearDown();
		 }

       return status;
	  }
	 
	 /**********************************************************************************************
	  * Switch to frame
	  * 
	  * @param idNameIndex {@link String} - Id, Name or Index of the frame
	  * @return status {@link boolean} - true/false
	  * @author Savita Tambe created May 30, 2018 
	  * @version 1.0 May 30, 2018
	  ***********************************************************************************************/
	 public static boolean switchToDefaultFrame() {
		 boolean status = false;
		 try {
              DriverManager.WEB_DRIVER_THREAD_LOCAL.get().switchTo().defaultContent();
              status = true;
		 } catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to switch to default frame: due to " +e.toString());
			System.out.println("Unable to switch to default frame: due to " +e.toString());
			TearDown();
		 }

       return status;
	  }
	 
	 /**********************************************************************************************
     * Pauses the test action.
     * 
     * @param waitTime {@link Integer} - The amount of time in milliseconds to pause.
     * @author Savita Tambe created March 30, 2018 
     * @version 1.0 March 30, 2018
     ***********************************************************************************************/
	 public static void pause(Integer waitTime) {
	    try {
	        	Thread.sleep(waitTime);
	    } catch (Exception e) {
	    	ExtentTestManager.getTest().log(LogStatus.ERROR, "Unable to wait the execution");
	    }
	 }	
	 
	 /**********************************************************************************************
	     * terminates the test 
	     * 
	     * @param waitTime {@link Integer} - The amount of time in milliseconds to pause.
	     * @author Kashyap created March 30, 2018 
	     * @version 1.0 March 30, 2018
	     ***********************************************************************************************/
	 public static boolean isVerify(WebElement webElement) {
		   	boolean status = false;
		   	waitForElementToBeVisible(webElement, 60);   
		   	try {
	   			status = webElement.isEnabled();	
		   	} catch(Exception e) {
//		   		ExtentTestManager.getTest().log(LogStatus.ERROR, "Webelement is disabled: " + webElement.toString()+ "due to " +e.toString());
		   		System.out.println("Webelement is disabled: " + webElement.toString()+ "due to " +e.toString());
		   	}
			  
	       return status;
	   	} 
	 
	 /**********************************************************************************************
	     * WaitElementInvisible
	     * to check if the element is invisible or not
	     * @param waitTime {@link Integer} - The amount of time in milliseconds to pause.
	     * @author Savita Tambe created March 30, 2018 
	     * @version 1.0 March 30, 2018
	     ***********************************************************************************************/
	 
 	public static boolean CheckElementInvisibility(WebElement sElement, int TimeOut) throws IOException, InterruptedException {
 		boolean status = false; int i;
		for (i = 0; i <= TimeOut; i++) {
			try {
				isPresent(sElement, 2);
				Thread.sleep(1000);
				continue;
			} catch (Exception e) {
				status = true;
				break;
			}
		}
		if (i > TimeOut) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Element "+ sElement.toString() +" was still visible, please verify");
			System.out.println("Element "+ sElement.toString() +" was still visible, please verify");
			ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addBase64ScreenShot(DriverManager.Capturefullscreenshot()));
			TearDown();
		}	
		return status;
	}
	 
	 /**********************************************************************************************
	     * terminates the test 
	     * 
	     * @param waitTime {@link Integer} - The amount of time in milliseconds to pause.
	     * @author Savita Tambe created March 30, 2018 
	     * @version 1.0 March 30, 2018
	     ***********************************************************************************************/
		public static void TearDown() {
			try {
				String screenShot =DriverManager.Capturefullscreenshot();
				ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addBase64ScreenShot(screenShot));
				ExtentTestManager.endTest();
				ExtentManager.getReporter().flush();
			} catch (IOException e) {
				ExtentTestManager.endTest();
				ExtentManager.getReporter().flush();
			}
//			DriverManager.WEB_DRIVER_THREAD_LOCAL.get().close();
			throw new SkipException("Skipping this test");
		}
}

