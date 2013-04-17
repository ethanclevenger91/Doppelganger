

package edu.Drake.doppelganger;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;



public class MainActivity extends Activity {
	
	
	private static final String TAG = "MainActivity";
	public static Context appContext;
	public static String myName;

	private static int GET_POST = 124654;
	
	protected class MyTabsListener implements ActionBar.TabListener {

		//used for the constructor
	    private Fragment fragment;

	    //constructor for tab listener
	    public MyTabsListener(Fragment fragment) {
	        this.fragment = fragment;
	    }

	    //used when a tab is selected again when the user is still on the tab
	    @Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
	    	
	    	//gets the fragment manager
	    	FragmentManager fm= getFragmentManager();
	    	
	    	//if there is a stack of fragments
	    	if(fm.getBackStackEntryCount()>0) {
	    		
	    			
	    			//pop the current fragment off the stack
	    			fm.popBackStack();
	    			
	    			//remove the up button
	    			getActionBar().setDisplayHomeAsUpEnabled(false);
	    	}
	    }

	    //used when a new tab is selected
	    @Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        
	    	//get the current fragment manager
	    	FragmentManager fm= getFragmentManager();
	    	
	    	//if there is a stack of fragments
	    	if(fm.getBackStackEntryCount()>0) {
	    		
	    			
	    			Log.v(TAG, "before popback");
	    			
	    			//pop back to previous fragment
	    			fm.popBackStack();
	    			
	    			Log.v(TAG, "after popback");
	    			
	    			//disable up button
	    			getActionBar().setDisplayHomeAsUpEnabled(false);
	    			
	    		
	    	}
	    	
	    	String myTag = null;
	    	
	    	switch(tab.getPosition()) {
	    	
	    	case 0:
	    		myTag = "CELEBS";
	    		break;
	    
	    	case 1:
	    		myTag = "FEED";
	    		break;
	    	
	    	case 2:
	    		myTag = "NOTIFICATIONS";
	    		break;
	    		
	    	}
	    		
	    	//adds the selected fragment to the fragment container
	        ft.add(R.id.fragment_container, fragment, myTag);
	    }

	    //used when a tab is unselected
	    @Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        // some people needed this line as well to make it work: 
	        ft.remove(fragment);
	    }
	}//
	
	static class ViewHolder {
		 String myName = null;
	  }
	
	@Override
	//on create method
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//disables the up button
		 getActionBar().setDisplayHomeAsUpEnabled(false);
		
		// setup action bar for tabs
	    final ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	  //initiating both tabs and set text to it.
	    ActionBar.Tab FeedTab = actionBar.newTab().setText("News Feed");
        ActionBar.Tab CelebritiesTab = actionBar.newTab().setText("Celebrities");
        ActionBar.Tab NoteTab = actionBar.newTab().setText("Notifications");
        
      //create the two fragments we want to use for display content
        Fragment CelebritiesFragment = new CelebritiesFragment();
        Fragment NoteFragment = new NotificationsFragment();
        Fragment FeedFragment = new FeedFragment();
        
      //set the Tab listener. Now we can listen for clicks.
        CelebritiesTab.setTabListener(new MyTabsListener(CelebritiesFragment));
        NoteTab.setTabListener(new MyTabsListener(NoteFragment));
        FeedTab.setTabListener(new MyTabsListener(FeedFragment));
        
      //add the two tabs to the actionbar
        actionBar.addTab(CelebritiesTab);
        actionBar.addTab(FeedTab);
        actionBar.addTab(NoteTab);
        
        actionBar.setSelectedNavigationItem(1);
        
        
             // start Facebook Login
       Session.openActiveSession(this, true, new Session.StatusCallback() {
////
          // callback when session changes state
          @Override
          public void call(Session session, SessionState state, Exception exception) {
        	  Log.v("main", "is not opened");
            if (session.isOpened()) {
            	Log.v("main", "is opened");
              // make request to the /me API
              Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
            	  
                // callback after Graph API response with user object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                  if (user != null) {
                    TextView welcome = (TextView) findViewById(R.id.welcome);
                    welcome.setText(user.getName());
                    ActionBar actionBar = getActionBar();
                    
                    //sets the actionbar title to user name, this verifies if Facebook is working
                    //if you see your name from facebook at the top of the screen, it is working
                    actionBar.setTitle(user.getName());
                  } 
                }
              });
              
              
            }
            //
          }
        });
	}
	
	public void useAsDoppelganger(View v) {
		
	}
	
	public void onActivityResult(int requestcode, int resultcode, Intent data) {
		super.onActivityResult(requestcode, resultcode, data);
		
		Session.getActiveSession().onActivityResult(this, requestcode, resultcode, data);
		
		if(requestcode==1)
		{
			if(resultcode==RESULT_OK && null!=data)
			{
		        if(data.getExtras()!=null){
		        	Log.v("hi", "got it from more info");
		        }
			}
		}
		if(requestcode==GET_POST)
		{
			if(resultcode==RESULT_OK && null!=data)
			{
				String myCaption = data.getStringExtra("caption");
				Log.v("hi", "ready for post");
				FeedFragment feedFragment = (FeedFragment) getFragmentManager().findFragmentByTag("FEED");
				feedFragment.feeds.add(feedFragment.get("Hi",0,0,0,"urness",myCaption,feedFragment.comment));
				int count = feedFragment.feeds.size();
				Log.v("hi", String.valueOf(count));
				feedFragment.refresh();
			}
		}
	}
	
	public void upVote(View v) {
		
		//on up pressed
		
	}
	
	public void downVote(View v) {
		//on down pressed
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		return true;
	}
	
	@Override
	//this is used on the up button press
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
	    
	        case android.R.id.home:
	            
	        	//gets the current manager
	        	FragmentManager fm= getFragmentManager();
	        	
	        	//if there are fragments in the stack
	        	 if(fm.getBackStackEntryCount()>0){
	        		 //pops back to a fragment at the top of stack
	        		 
	        		 fm.popBackStack();
	        		 Log.v(TAG, "up pressed");
	        		 
	        		 
	        		 //disables the up button
	        		 getActionBar().setDisplayHomeAsUpEnabled(false);
	        		 
	        		 return true;
	        		 
	        	 }
	        	 break;
	        	 
	        case R.id.menu_compose:
	        	
	        	composeMenuItem();
	        	
	        	break;
	        	
	    }
	    //returns the item selected, in this case the up button
	    return super.onOptionsItemSelected(item);
	    
	}
	    
	private void composeMenuItem() {
		
            Intent intent = new Intent(getBaseContext(), Post.class);
    		startActivityForResult(intent,GET_POST);
    		
   }
	
	@Override
	  protected void onResume() {
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	  }
	
}
