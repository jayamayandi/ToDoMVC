package com.todomvc.tests;


import java.io.IOException;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.todomvc.SharedDriver;


public class BaseTest {

	protected static WebDriver driver;
    protected final String START_PAGE;
    
    protected Properties envProps;
	
	public BaseTest() throws IOException {
		envProps = new Properties();
		envProps.load(ClassLoader.getSystemResourceAsStream("environment.properties"));
		START_PAGE = envProps.getProperty("URL");
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {		
		driver = new SharedDriver();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		//ignore issues when deleting cookies on IE
		try{
			driver.manage().getCookies().clear();
			driver.manage().deleteAllCookies();
		}catch(Exception e){}
		
		driver.get(START_PAGE);	
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.localStorage.clear()");
	}

	@After
	public void tearDown() throws Exception {
		driver.manage().deleteAllCookies();
		
	}
}

