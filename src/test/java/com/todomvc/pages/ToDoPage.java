package com.todomvc.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.todomvc.models.IMatchCategory;
import com.todomvc.models.MatchByTaskName;
import com.todomvc.models.Tasks;

public class ToDoPage {

	protected WebDriver driver;

	public ToDoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void editTask(String taskName, String newTaskDetails) throws Exception {
		List<Tasks> taskTobeEdited = getMatchedTasks(new MatchByTaskName(taskName));
		
		if(taskTobeEdited.size()>0) {
			taskTobeEdited.get(0).editTask(driver, newTaskDetails);;
		}		
	}

	public void enterToDoTask(String taskName) {	
		WebElement element = driver.findElement(By.cssSelector("#new-todo"));
		element.sendKeys(Keys.CLEAR);
		element.sendKeys(taskName);
		element.sendKeys(Keys.ENTER);
	}

	public List<Tasks> getMatchedTasks(IMatchCategory matchCategory) throws Exception {
		
		List<WebElement> taskList = driver.findElements(By.cssSelector("ul#todo-list > li"));
		List<Tasks>	tasks = new ArrayList<Tasks>();
		
		for(WebElement eachTask:taskList){
			Tasks task = new Tasks(eachTask);
			if(matchCategory.MatchTask(task)) {
				tasks.add(task); 
			}
		}
		
		return tasks;
	}

	
	public String getToDoCount() {
		return driver.findElement(By.cssSelector("#todo-count > strong")).getText();
	}
	
	public void markAllTasksComplete() {
		driver.findElement(By.cssSelector("#toggle-all")).click();
	}
	
	public void clearAllCompleted() {
		WebDriverWait wait = new WebDriverWait(driver,30);
	    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#clear-completed")));
		element.click();
	}
	
	public void chooseFilter(String filterType) {
		WebDriverWait wait = new WebDriverWait(driver,30);
	    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#filters")));
		WebElement filterElement;
		
	    switch(filterType){
	    	case "All":
	    		filterElement = element.findElement(By.cssSelector("a[href='#/']"));
	    		if(!(filterElement.getAttribute("class").contains("selected"))) {
	    			filterElement.click();
	    		}
	    		break;
	    	case "Completed":
	    		filterElement = element.findElement(By.cssSelector("a[href='#/completed']"));
	    		if(!(filterElement.getAttribute("class").contains("selected"))) {
	    			filterElement.click();
	    		}
	    		break;
	    	case "Active":
	    		filterElement = element.findElement(By.cssSelector("a[href='#/active']"));
	    		if(!(filterElement.getAttribute("class").contains("selected"))) {
	    			filterElement.click();
	    		}
	    		break;
	    	default:
	    		filterElement = element.findElement(By.cssSelector("a[href='#/']"));
	    		if(!(filterElement.getAttribute("class").contains("selected"))) {
	    			filterElement.click();
	    		}
	    		break;
	    	
	    }
	    	
	}
}
		