package edu.Drake.doppelganger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class AddComment extends Activity {
	
	private EditText textEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		
		//disables the up button
    	getActionBar().setDisplayHomeAsUpEnabled(true);
		
		textEdit = (EditText) findViewById(R.id.edit_text1);
		
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
		
	}
	
	@Override
	public void onBackPressed() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(textEdit.getWindowToken(),0); 
    	
    	//disables the up button
    	getActionBar().setDisplayHomeAsUpEnabled(false);
    	
    	Intent intent = new Intent();
    	setResult(RESULT_OK,intent);
    	
    	finish();
    	
    	overridePendingTransition( R.anim.slide_in_down, R.anim.slide_out_down);
    	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_comment, menu);
		
		return true;
	}

	@Override
	//this is used on the up button press
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        		 
	        	//disables the up button
	        	getActionBar().setDisplayHomeAsUpEnabled(false);
	        	
	        	onBackPressed();
	        		 
	        	return true;
	        case R.id.menu_cancel:
	        	cancelMenuItem();
	        	break;
	        	
	        case R.id.menu_post:
	        	postMenuItem();
	        	break;
	    }
	    //returns the item selected, in this case the up button
	    return super.onOptionsItemSelected(item);
	}
	
	private void cancelMenuItem() {
		//disables the up button
    	getActionBar().setDisplayHomeAsUpEnabled(false);
    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(textEdit.getWindowToken(),0); 
		onBackPressed();

 		overridePendingTransition( R.anim.slide_in_down, R.anim.slide_out_down);
	}
	
	private void postMenuItem() {
		//disables the up button
    	getActionBar().setDisplayHomeAsUpEnabled(false);
    	
    	EditText edit = (EditText) findViewById(R.id.edit_text1);
    	final String myReturnString = edit.getText().toString();
    	final Intent intent = getIntent();
    	
    	Session.openActiveSession(this, true, new Session.StatusCallback() {
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
    		                	  
    							String finalString = user.getName() + ": " + myReturnString;
    							intent.putExtra("post", finalString);
    		                	  
    							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    							imm.hideSoftInputFromWindow(textEdit.getWindowToken(),0);
    							
    							setResult(RESULT_OK,intent);
    							finish();
    		                	  
    							overridePendingTransition( R.anim.slide_in_down, R.anim.slide_out_down);
    		                   
    						} 
    					}
    				});    
    			}
    		}
    	});
	}
}