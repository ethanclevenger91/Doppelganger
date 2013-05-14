package edu.Drake.doppelganger;

import java.util.List;

import android.app.ActionBar;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class NotificationsFragment extends ListFragment {

	public View ColoredView;
	public String faceBookId;
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	            
	    final ActionBar actionBar = getActivity().getActionBar();
		actionBar.setCustomView(R.layout.custom_actionbar);
		actionBar.setDisplayShowCustomEnabled(false);
		
		// start Facebook Login
				Session.openActiveSession(getActivity(), true, new Session.StatusCallback() {
					////
					// callback when session changes state
					@Override
					public void call(Session session, SessionState state, Exception exception) {
						if (session.isOpened()) {
							// make request to the /me API
							Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

								// callback after Graph API response with user object
								@Override
								public void onCompleted(GraphUser user, Response response) {
									if (user != null) {
										filterMostRecent(user.getId());
										faceBookId = user.getId();
									} 
								}
							});
						}
					}
				});
	}
	
	public void filterMostRecent(String thisId) {
		FeedSQLiteHelper db = new FeedSQLiteHelper(this.getActivity().getBaseContext());
		List<FeedsModel> contacts = db.getNotifications(thisId); //change to find posts user is tagged in
		ArrayAdapter<FeedsModel> adapter = new NotificationsArrayAdapter(this, contacts);
	    setListAdapter(adapter);
	    adapter.notifyDataSetChanged();
	}
	
	public void refresh(){
		FeedSQLiteHelper db = new FeedSQLiteHelper(this.getActivity().getBaseContext());
		List<FeedsModel> contacts = db.getNotifications(faceBookId);
		ArrayAdapter<FeedsModel> adapter = new NotificationsArrayAdapter(this, contacts);
	    setListAdapter(adapter);
	    adapter.notifyDataSetChanged();
	  }
	
	@Override
	public void onResume() {
	    super.onResume();
	    refresh();
	  }

	  @Override
	public void onPause() {
	    super.onPause();
	  }
	
	
/*
	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    
		  
		  v.setBackgroundColor(Color.BLUE); //selected color
          ColoredView = v;
	  }
*/

}
//
