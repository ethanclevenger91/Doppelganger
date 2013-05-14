package edu.Drake.doppelganger;

import java.util.List;

import android.app.Application;

import com.facebook.model.GraphUser;

public class FriendApplication extends Application {

	private List<GraphUser> selectedUsers;
	
	public List<GraphUser> getSelectedUsers() {
	    return selectedUsers;
	}

	public void setSelectedUsers(List<GraphUser> users) {
	    selectedUsers = users;
	}
	
}
