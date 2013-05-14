package edu.Drake.doppelganger;

import java.util.List;

import android.app.ActionBar;
import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NotificationsFragment extends ListFragment {

	public View ColoredView;
	
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	            
	    final ActionBar actionBar = getActivity().getActionBar();
		actionBar.setCustomView(R.layout.custom_actionbar);
		actionBar.setDisplayShowCustomEnabled(false);
	      
	    filterMostRecent();
	}
	
	public void filterMostRecent() {
		FeedSQLiteHelper db = new FeedSQLiteHelper(this.getActivity().getBaseContext());
		List<FeedsModel> contacts = db.getMostRecent(); //change to find posts user is tagged in
		
		ArrayAdapter<FeedsModel> adapter = new NotificationsArrayAdapter(this, contacts);
	    setListAdapter(adapter);
	    adapter.notifyDataSetChanged();
	}
	
	public void refresh(){
		FeedSQLiteHelper db = new FeedSQLiteHelper(this.getActivity().getBaseContext());
		List<FeedsModel> contacts = db.getMostRecent(); //change to find posts user is tagged in
		
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
