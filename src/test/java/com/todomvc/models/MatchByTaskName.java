package com.todomvc.models;

public class MatchByTaskName implements IMatchCategory {
	
	String taskName;
	
	public MatchByTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public boolean MatchTask(Tasks task) {
		if(task.getTaskName().equals(taskName)){
			return true;
		}else {
			return false;
		}			
	}

}
