package com.todomvc;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class SharedDriver extends EventFiringWebDriver {

    private static WebDriver REAL_DRIVER;

    private static final Thread CLOSE_THREAD = new Thread() {
      @Override
      public void run() {
          REAL_DRIVER.quit();
      }
    };

    /*
     * static initializer
     */
    static {	
    	Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    	try {
    		DriverFactory factory = new DriverFactory();
			REAL_DRIVER = factory.getDriver();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

    public SharedDriver() {
        super(REAL_DRIVER);
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
        	throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }

    @Override
    public void quit() {
    	if (Thread.currentThread() != CLOSE_THREAD) {
        	throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }
}

