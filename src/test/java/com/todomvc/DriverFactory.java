package com.todomvc;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class DriverFactory {
	
	private Properties props;
	
	public DriverFactory() throws IOException {
		props = new Properties();
		try {
		InputStream stream = ClassLoader.getSystemResourceAsStream("environment.properties");	
		props.load(stream);
		}catch(Exception e) {
			System.out.println("Excpetion e"+e.getMessage());
		}
	}
	
	/**
	 * get a WebDriver to be executed on the local machine
	 * @return WebDriver
	 */
	private WebDriver getLocalDriver() {
		String browser = props.getProperty("browser").toLowerCase();
		
		//if browser is not set via a maven profile, check for a command line arg
		if(browser.equals("${browser}")) {
			browser = System.getProperty("browser");
		}
		
		WebDriver driver;
		
		if(browser == null) {
			browser = "";
		}
			
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("window-size=1200x600");
		
		driver = new ChromeDriver(options);
    	driver.manage().window().maximize();
    	return driver;
	}
	 		
	private WebDriver getRemoteDriver() throws MalformedURLException {

    	String browser = props.getProperty("browser");
    	
    	DesiredCapabilities capabilities;
    	
    	switch(browser) {
			case IBrowserName.Firefox:
				capabilities = DesiredCapabilities.firefox();
	    		break;
			default:
				capabilities = DesiredCapabilities.chrome();
    	}
 
        return new RemoteWebDriver(
                new URL("http://10.5.0.2:4444/wd/hub"), capabilities);
	}
	
	public WebDriver getDriver() throws MalformedURLException {
		
        String env = props.getProperty("env").toLowerCase();
        WebDriver driver;       

        switch(env) {
       		case "remote":
        		driver = getRemoteDriver();
        		break;
        	default:
        		driver = getLocalDriver();
        }       
        
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45,TimeUnit.SECONDS);
		return driver;
	}
}

