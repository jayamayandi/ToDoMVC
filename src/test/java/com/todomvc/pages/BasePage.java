package com.todomvc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	protected WebDriver driver;
	 

	public BasePage(WebDriver driver) {
	 	this.driver = driver;
    }	
	
	public WebDriver clickEmbedJSLink(){
		driver.findElement(By.cssSelector("a[href*='examples/emberjs']")).click();
		WebDriverWait wait = new WebDriverWait(driver,30);
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#new-todo")));
	    return driver;
	}
}
