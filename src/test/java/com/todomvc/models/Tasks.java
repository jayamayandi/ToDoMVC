package com.todomvc.models;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Tasks {
	
	WebElement task;

	public Tasks(WebElement task) {
		this.task = task;
	}
	
	public String getTaskName() {
		return task.findElement(By.cssSelector("Label")).getText();
	}
	
	public String getStatus() {
		if(task.getAttribute("class").contains("completed")) {
			return "Completed";
		}else {
			return "Active";
		}
	}
	
	public void markCompletedCheckbox() {
		 if(!getStatus().equals("Completed")) {
			 task.findElement(By.cssSelector(".toggle")).click();
		 }
		
	}
	
	public void unmarkCompletedCheckbox() {
		 if(getStatus().equals("Completed")) {
			 task.findElement(By.cssSelector(".toggle")).click();
		 }
		
	}

	public void deleteTask() {
		task.click();
		task.findElement(By.cssSelector("button.destroy")).click();
	}
	
	public void editTask(WebDriver driver,String newValue) throws InterruptedException {
		Actions act = new Actions(driver);
		act.doubleClick(task).build().perform();
		
		WebElement editInput = task.findElement(By.cssSelector("input.edit"));
		
		String selectAll = Keys.chord(Keys.CONTROL, "a");
		editInput.sendKeys(selectAll);
		editInput.sendKeys(Keys.DELETE);
		editInput.sendKeys(newValue);
		editInput.sendKeys(Keys.ENTER);
	}
}
