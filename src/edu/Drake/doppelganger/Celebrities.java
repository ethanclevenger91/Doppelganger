package edu.Drake.doppelganger;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Celebrities extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//disables the up button
		 getActionBar().setDisplayHomeAsUpEnabled(true);
		 
		 ArrayAdapter<CelebrityEntry> adapter = new CelebrityEntryAdapter(this, getModel());
		 setListAdapter(adapter);
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_celebrities, menu);
		return true;
	}
	
	@Override
	//this is used on the up button press
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            
	        	onBackPressed();
	        		 
	        	//disables the up button
	        	getActionBar().setDisplayHomeAsUpEnabled(false);
	        		 
	        	return true;
	        case R.id.menu_cancel:
	        	cancelMenuItem();
	        	break;
	    }
	    //returns the item selected, in this case the up button
	    return super.onOptionsItemSelected(item);
	}
	
	public void useAsDoppelganger(View v){
		onBackPressed();
	}
	
	private void cancelMenuItem() {
		onBackPressed();
	}
	
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		
		CelebrityEntry celebrity = (CelebrityEntry) getListView().getItemAtPosition(position);
		Intent makeAPost = new Intent(v.getContext(), Post.class);
		makeAPost.putExtra("image", celebrity.getPic());
		
		setResult(RESULT_OK,makeAPost);
    	finish();
		
		
	}
	
	private List<CelebrityEntry> getModel() {
	    List<CelebrityEntry> list = new ArrayList<CelebrityEntry>();
	    list.add(get("Kirsten Dunst",R.drawable.dunst));
	    list.add(get("Adam Savage",R.drawable.savage));
	    list.add(get("Pierce Brosnan",R.drawable.brosnan));
	    list.add(get("Zooey Deschanel",R.drawable.zooey));
	    list.add(get("Anne Hathaway",R.drawable.hathaway));
	    return list;
	  }
	
	private CelebrityEntry get(String name, int pic) {
    return new CelebrityEntry(name, pic);
  }
	
}
