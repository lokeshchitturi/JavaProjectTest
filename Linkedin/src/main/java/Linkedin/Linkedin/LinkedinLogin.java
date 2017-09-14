package Linkedin.Linkedin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LinkedinLogin {

	WebDriver driver=null;
	ExtentReports extent=null;
	ExtentTest logger=null;
	Properties linkedinProperties=null;
	
	@BeforeTest

	public void initializeVariables() {
		
		// Extent Report intialization
		System.out.println("Second Comment Added");
		
		extent=new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReport.html", true);
		extent.addSystemInfo("HostName","LinkedinAutomation")
			.addSystemInfo("Author","Lokesh")
			.addSystemInfo("Environment","AutomationTesting");
		
		extent.loadConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
	
		
		// driver Initialization
		
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();		
	
		// Linkedin Config properties object initialization
		
		linkedinProperties=new Properties();
		
	}
	
	@Test
	
	public void loginValidation() throws FileNotFoundException, IOException
	{
		
		linkedinProperties.load(new FileInputStream(System.getProperty("user.dir")+"/LinkedinData.properties"));
		logger=extent.startTest("LinkedinLogin validation");
		driver.get("https://www.linkedin.com/");
		logger.log(LogStatus.INFO, "Linkedin webpage opened");
		
		driver.findElement(By.name("session_key")).sendKeys(linkedinProperties.getProperty("linkedinUsername"));
		
		logger.log(LogStatus.INFO, "Username entered");
		
		driver.findElement(By.name("session_password")).sendKeys(linkedinProperties.getProperty("linkedinPassword"));
		logger.log(LogStatus.INFO, "Password entered");
		driver.findElement(By.id("login-submit")).click();
		logger.log(LogStatus.INFO, "Login button clicked");
		
		Assert.assertEquals(driver.findElement(By.linkText(linkedinProperties.getProperty("linkedinProfilename"))).getText(), "Lokesh Chitturi");
		
		
		driver.findElement(By.xpath("//*[@id='nav-settings__dropdown-trigger']/img")).click();
		logger.log(LogStatus.INFO, "CLiked on dropdown");
		driver.findElement(By.linkText("Sign out")).click();
		logger.log(LogStatus.INFO, "CLiked on Signout link");
		
		
		logger.log(LogStatus.PASS, "Login Logout validation sucessful");
		
		System.out.println("last statement");
		
	}
	
	
	@AfterTest
	
	public void close()
	{
		driver.close();
		extent.flush();
	}

}
