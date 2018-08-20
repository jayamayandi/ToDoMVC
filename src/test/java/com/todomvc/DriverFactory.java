package com.todomvc;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
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
		String path = System.getProperty("user.dir");
		
		switch(browser) {
			case IBrowserName.Ie:
				path = path +"/src/test/resources/MicrosoftWebDriver.exe";
				System.setProperty("webdriver.edge.driver",path); //put actual location
				driver = new EdgeDriver();
	        	break;
			case IBrowserName.Firefox:
				System.setProperty("webdriver.firefox.marionette", "C:/Drivers/geckodriver-v0.9.0-win64/geckodriver.exe");
				FirefoxProfile fp = new FirefoxProfile();
				fp.setPreference("network.proxy.type", 0);
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(FirefoxDriver.PROFILE, fp);
				capabilities.setCapability("marionette", true);
				driver = new FirefoxDriver(capabilities);
				break;
			default:
		        path = path +"/src/test/resources/chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", path);
				DesiredCapabilities cap = DesiredCapabilities.chrome();
		        cap.setCapability("applicationCacheEnabled", false);
		        driver = new ChromeDriver(cap);
		        break;
		}
    			
		driver.manage().window().maximize();
		return driver;
	}
	
	private WebDriver getRemoteDriver() throws MalformedURLException {

    	String browser = props.getProperty("browser");
    	
    	DesiredCapabilities capabilities;
    	
    	switch(browser) {
	    	case IBrowserName.Ie:
	    		capabilities = DesiredCapabilities.internetExplorer();
	    		capabilities.setCapability("version", "10");
	    		capabilities.setCapability("platform", "Windows 10");
	    		break;
			case IBrowserName.Firefox:
				capabilities = DesiredCapabilities.firefox();
	    		capabilities.setCapability("version", "28");
	    		capabilities.setCapability("platform", "Windows 10");
	    		break;
			default:
				capabilities = DesiredCapabilities.chrome();
	    		capabilities.setCapability("version", "67");
	    		capabilities.setCapability("platform", "Windows 10");
    	}
 
        return new RemoteWebDriver(
                new URL("http://ipaddress/hub"), capabilities);
	}
	
	public WebDriver getDriver() throws MalformedURLException {
		
        String env = props.getProperty("env").toLowerCase();
        WebDriver driver;   
        
        	//if browser is not set via a maven profile, check for a command line arg
      		if(env.equals("${env}")) {
      			env = System.getProperty("env");
      		
      		}
      		
      		if(env == null) {
      			env = "";
      		}

        switch(env) {
       		case "remote":
        		driver = getRemoteDriver();
        		break;
        	default:
        		driver = getLocalDriver();
        }       
        
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
		return driver;
	}
}

