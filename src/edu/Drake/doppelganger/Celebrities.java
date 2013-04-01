package edu.Drake.doppelganger;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class Celebrities extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_celebrities);
		
		//disables the up button
		 getActionBar().setDisplayHomeAsUpEnabled(true);
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
	
	private void cancelMenuItem() {
		onBackPressed();
	}
	
}
