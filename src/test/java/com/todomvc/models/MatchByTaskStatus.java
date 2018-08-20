package com.todomvc.models;

public class MatchByTaskStatus implements IMatchCategory {
	String status;
	String category;
	
	public MatchByTaskStatus(String status) {
		this.status = status;
	}
	
	@Override
	public boolean MatchTask(Tasks task) {
		if(status.equals("All")) {
			return true;
		}else if(task.getStatus().equals(status)){
			return true;
		}else { 
			return false;
		}			
	}

}
