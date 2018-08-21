package com.todomvc.tests;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.todomvc.models.MatchByTaskName;
import com.todomvc.models.MatchByTaskStatus;
import com.todomvc.models.Tasks;
import com.todomvc.pages.BasePage;
import com.todomvc.pages.ToDoPage;
import junit.framework.Assert;

public class ToDoTest extends BaseTest {
	
	public ToDoTest() throws IOException{
		super();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	/*Test case to verify adding a todo task*/
	@Test
	public void validateAddingTaskToDoList() throws Exception {
		BasePage basePage = new BasePage(driver);
		
		ToDoPage todoPage = new ToDoPage(basePage.clickEmbedJSLink());
		todoPage.enterToDoTask("First Task");
		todoPage.enterToDoTask("Second Task");
		
		List<Tasks> firstTask = todoPage.getMatchedTasks(new MatchByTaskName("First Task"));
		List<Tasks> secondTask = todoPage.getMatchedTasks(new MatchByTaskName("Second Task"));		
	
		Assert.assertEquals("Task added successfully",1, firstTask.size());
		Assert.assertEquals("Task added successfully",1, secondTask.size());
		
		Assert.assertEquals("To Do Count increased after adding task","2", todoPage.getToDoCount());
	}
	
	/*Test case to verify removing a todo task using cross button*/
	@Test
	public void validateRemovalTaskfromToDoList() throws Exception {
		BasePage basePage = new BasePage(driver);
		
		ToDoPage todoPage = new ToDoPage(basePage.clickEmbedJSLink());
		todoPage.enterToDoTask("First Task");
		todoPage.enterToDoTask("Second Task");
		
		List<Tasks> firstTask = todoPage.getMatchedTasks(new MatchByTaskName("First Task"));
		List<Tasks> secondTask = todoPage.getMatchedTasks(new MatchByTaskName("Second Task"));
		
		Assert.assertEquals("Task added successfully",1, firstTask.size());
		Assert.assertEquals("Task added successfully",1, secondTask.size());
		
		firstTask.get(0).deleteTask(driver);
		
		List<Tasks> allTasks = todoPage.getMatchedTasks(new MatchByTaskStatus("All"));
		
		Assert.assertEquals("Number of tasks match after deleting the task",1, allTasks.size());
		Assert.assertEquals("Other tasks remain after deleting","Second Task",allTasks.get(0).getTaskName());
		
	}
	
	/*Test case to verify completed checkbox in the task*/
	@Test
	public void testAddingSelectedTaskToCompletedList() throws Exception {
		BasePage basePage = new BasePage(driver);
		
		ToDoPage todoPage = new ToDoPage(basePage.clickEmbedJSLink());
		todoPage.enterToDoTask("First Task");
		todoPage.enterToDoTask("Second Task");
		todoPage.enterToDoTask("Third Task");
		
		List<Tasks> firstTask= todoPage.getMatchedTasks(new MatchByTaskName("First Task"));
		List<Tasks> secondTask = todoPage.getMatchedTasks(new MatchByTaskName("Second Task"));
		List<Tasks> thirdTask = todoPage.getMatchedTasks(new MatchByTaskName("Third Task"));
		
		Assert.assertEquals("Task added successfully",1, firstTask.size());
		Assert.assertEquals("Task added successfully",1, secondTask.size());
		Assert.assertEquals("Task added successfully",1, thirdTask.size());
		
		firstTask.get(0).markCompletedCheckbox();
		thirdTask.get(0).markCompletedCheckbox();
		
		List<Tasks> completedTasks = todoPage.getMatchedTasks(new MatchByTaskStatus("Completed"));
		
		Assert.assertEquals("Number of completed tasks match",2, completedTasks.size());
		Assert.assertEquals("Task marked as completed successfully","First Task", completedTasks.get(0).getTaskName());
		Assert.assertEquals("Task marked as completed successfully","Third Task", completedTasks.get(1).getTaskName());
		
	}
	
	/*Test case to verify aading all tasks to completed by checking the button*/
	@Test
	public void testAllTaskToCompletedList() throws Exception {
		BasePage basePage = new BasePage(driver);

		ToDoPage todoPage = new ToDoPage(basePage.clickEmbedJSLink());
		todoPage.enterToDoTask("First Task");
		todoPage.enterToDoTask("Second Task");
		todoPage.enterToDoTask("Third Task");
		
		List<Tasks> firstTask= todoPage.getMatchedTasks(new MatchByTaskName("First Task"));
		List<Tasks> secondTask = todoPage.getMatchedTasks(new MatchByTaskName("Second Task"));
		List<Tasks> thirdTask = todoPage.getMatchedTasks(new MatchByTaskName("Third Task"));
		
		Assert.assertEquals("Task added successfully",1, firstTask.size());
		Assert.assertEquals("Task added successfully",1, secondTask.size());
		Assert.assertEquals("Task added successfully",1, thirdTask.size());
		
		todoPage.markAllTasksComplete();
		
		List<Tasks> completedTasks = todoPage.getMatchedTasks(new MatchByTaskStatus("Completed"));
		
		Assert.assertEquals("All tasks marked to completed",3, completedTasks.size());
		Assert.assertEquals("First task added to completed list", "First Task",completedTasks.get(0).getTaskName());
		Assert.assertEquals("Second task added to completed list", "Second Task",completedTasks.get(1).getTaskName());
		Assert.assertEquals("Third task added to completed list", "Third Task",completedTasks.get(2).getTaskName());		
		
	}
	
	/*Test case to verify clear all completed button*/
	@Test
	public void clearAllCompletedTasks() throws Exception {
		BasePage basePage = new BasePage(driver);	
		
		ToDoPage todoPage = new ToDoPage(basePage.clickEmbedJSLink());
		todoPage.enterToDoTask("First Task");
		todoPage.enterToDoTask("Second Task");
		todoPage.enterToDoTask("Third Task");
		
		List<Tasks> firstTask= todoPage.getMatchedTasks(new MatchByTaskName("First Task"));
		List<Tasks> secondTask = todoPage.getMatchedTasks(new MatchByTaskName("Second Task"));
		List<Tasks> thirdTask = todoPage.getMatchedTasks(new MatchByTaskName("Third Task"));
		
		Assert.assertEquals("Task added successfully",1, firstTask.size());
		Assert.assertEquals("Task added successfully",1, secondTask.size());
		Assert.assertEquals("Task added successfully",1, thirdTask.size());
		
		todoPage.markAllTasksComplete();
		
		List<Tasks> completedTasks = todoPage.getMatchedTasks(new MatchByTaskStatus("Completed"));
		
		Assert.assertEquals("All tasks marked to completed",3, completedTasks.size());
		Assert.assertEquals("First task added to completed list", "First Task",completedTasks.get(0).getTaskName());
		Assert.assertEquals("Second task added to completed list", "Second Task",completedTasks.get(1).getTaskName());
		Assert.assertEquals("Third task added to completed list", "Third Task",completedTasks.get(2).getTaskName());		
		
		todoPage.clearAllCompleted();
		Assert.assertTrue("All completed tasks cleared successfully",todoPage.checkAnyTaskPresent());
	    
	}
	
	/*Test case to verfiy whether filters work fine -completed, all,active*/
	@Test
	public void testFilters() throws Exception {
		BasePage basePage = new BasePage(driver);	
		
		ToDoPage todoPage = new ToDoPage(basePage.clickEmbedJSLink());
		todoPage.enterToDoTask("First Task");
		todoPage.enterToDoTask("Second Task");
		todoPage.enterToDoTask("Third Task");
		todoPage.enterToDoTask("Fourth Task");
		
		List<Tasks> firstTask= todoPage.getMatchedTasks(new MatchByTaskName("First Task"));
		List<Tasks> secondTask = todoPage.getMatchedTasks(new MatchByTaskName("Second Task"));
		List<Tasks> thirdTask = todoPage.getMatchedTasks(new MatchByTaskName("Third Task"));
		List<Tasks> fourthTask = todoPage.getMatchedTasks(new MatchByTaskName("Fourth Task"));
		
		Assert.assertEquals("Task added successfully",1, firstTask.size());
		Assert.assertEquals("Task added successfully",1, secondTask.size());
		Assert.assertEquals("Task added successfully",1, thirdTask.size());
		Assert.assertEquals("Task added successfully",1, fourthTask.size());
		
		firstTask.get(0).markCompletedCheckbox();
		thirdTask.get(0).markCompletedCheckbox();
		
		todoPage.chooseFilter("Completed");
		List<Tasks> allTasksInCurrentView = todoPage.getMatchedTasks(new MatchByTaskStatus("All"));
		
		Assert.assertEquals("Number of tasks in completed view is as expected",2,allTasksInCurrentView.size());
		Assert.assertEquals("First Task", allTasksInCurrentView.get(0).getTaskName());
		Assert.assertEquals("Third Task", allTasksInCurrentView.get(1).getTaskName());
		
		todoPage.chooseFilter("Active");
		allTasksInCurrentView = todoPage.getMatchedTasks(new MatchByTaskStatus("All"));
		
		Assert.assertEquals("Number of tasks in completed view is as expected",2,allTasksInCurrentView.size());
		Assert.assertEquals("Second Task", allTasksInCurrentView.get(0).getTaskName());
		Assert.assertEquals("Fourth Task", allTasksInCurrentView.get(1).getTaskName());
				
	}
	
	/*Test case to validate whether the task can be edited*/
	@Test
	public void validateEditTask() throws Exception {
		BasePage basePage = new BasePage(driver);
		
		ToDoPage todoPage = new ToDoPage(basePage.clickEmbedJSLink());
		todoPage.enterToDoTask("First Task");
		todoPage.enterToDoTask("Second Task");
		
		List<Tasks> firstTask = todoPage.getMatchedTasks(new MatchByTaskName("First Task"));
		List<Tasks> secondTask = todoPage.getMatchedTasks(new MatchByTaskName("Second Task"));		
	
		Assert.assertEquals("Task added successfully",1, firstTask.size());
		Assert.assertEquals("Task added successfully",1, secondTask.size());
		
		firstTask.get(0).editTask(driver, "New Name");
		
		firstTask = todoPage.getMatchedTasks(new MatchByTaskName("New Name"));
		Assert.assertEquals("Task Name Edited",1, firstTask.size());
		Assert.assertEquals("Edited Task has new value","New Name", firstTask.get(0).getTaskName());
		
	}
	
}
