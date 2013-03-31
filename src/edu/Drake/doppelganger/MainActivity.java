/*I would make this the feed - EC*/

package edu.Drake.doppelganger;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	
	public static Context appContext;
	
	private String myTag;
	
	protected class MyTabsListener implements ActionBar.TabListener {

		//used for the constructor
	    private Fragment fragment;

	    //constructor for tab listener
	    public MyTabsListener(Fragment fragment) {
	        this.fragment = fragment;
	    }

	    //used when a tab is selected again when the user is still on the tab
	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	    	
	    	//gets the fragment manager
	    	FragmentManager fm= getFragmentManager();
	    	
	    	//if there is a stack of fragments
	    	if(fm.getBackStackEntryCount()>0) {
	    		
	    		//get the more info fragment
	    		Fragment myFragment = (Fragment)getFragmentManager().findFragmentByTag(myTag);
	    		
	    		//if more info is visible
	    		if (myFragment.isVisible()) {
	    			
	    			//pop the current fragment off the stack
	    			fm.popBackStack();
	    			
	    			//remove the up button
	    			getActionBar().setDisplayHomeAsUpEnabled(false);
	    			
	    		}
	    		
	    	}
	    }

	    //used when a new tab is selected
	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        
	    	//get the current fragment manager
	    	FragmentManager fm= getFragmentManager();
	    	
	    	//if there is a stack of fragments
	    	if(fm.getBackStackEntryCount()>0) {
	    		
	    		//get the more info fragment if it is visible
	    		
	    	    Fragment myFragment = (Fragment) getFragmentManager().findFragmentByTag(myTag);
	    		
	    		if (myFragment.isVisible()) {
	    			
	    			Log.v(TAG, "before popback");
	    			
	    			//pop back to previous fragment
	    			fm.popBackStack();
	    			
	    			Log.v(TAG, "after popback");
	    			
	    			//disable up button
	    			getActionBar().setDisplayHomeAsUpEnabled(false);
	    			
	    		}
	    	}
	    	
	    	//adds the selected fragment to the fragment container
	        ft.add(R.id.fragment_container, fragment, null);
	    }

	    //used when a tab is unselected
	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        // some people needed this line as well to make it work: 
	        ft.remove(fragment);
	    }
	}
	
	@Override
	//on create method
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// setup action bar for tabs
	    ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	  //initiating both tabs and set text to it.
        ActionBar.Tab CelebritiesTab = actionBar.newTab().setText("Celebrities");
        ActionBar.Tab NoteTab = actionBar.newTab().setText("Notifications");
        ActionBar.Tab FeedTab = actionBar.newTab().setText("News Feed");
        
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
        
	}
	
	public void moreInfo(View v) {
		
		//sets the content view to the current xml file
		setContentView(R.layout.activity_more_info);

		//gets the current fragment manager
        FragmentManager fm = getFragmentManager();
        
        //creates a fragment transaction which is used for transitions
        FragmentTransaction ft = fm.beginTransaction();

        //starts the transaction
        fm.beginTransaction();
        Fragment fragOne = new MoreInfo();
        Bundle arguments = new Bundle();
        
        //sets argument in the new fragment
        arguments.putBoolean("shouldYouCreateAChildFragment", true);
        fragOne.setArguments(arguments);
        
        //replaces the current fragment with the more info fragment
        ft.replace(R.id.fragment_container, fragOne, "MORE_INFO");
        myTag = "MORE_INFO";
        
        //adds the current fragment to the backstack in case the back button is pressed
        ft.addToBackStack(null);
        
        //commits the changes so that the more info fragment is shown
        ft.commit();
        
        //shows the back button
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
		//sets the content view to the current xml file
				setContentView(R.layout.activity_post);

				//gets the current fragment manager
		        FragmentManager fm = getFragmentManager();
		        
		        //creates a fragment transaction which is used for transitions
		        FragmentTransaction ft = fm.beginTransaction();

		      //adds the current fragment to the backstack in case the back button is pressed
		        ft.addToBackStack(null);
		        
		        //starts the transaction
		        fm.beginTransaction();
		        Fragment fragOne = new Post();
		        Bundle arguments = new Bundle();
		        
		        //sets argument in the new fragment
		        arguments.putBoolean("shouldYouCreateAChildFragment", true);
		        fragOne.setArguments(arguments);
		        
		        //replaces the current fragment with the more info fragment
		        ft.replace(R.id.fragment_container, fragOne, "POST");
		        myTag = "POST";
		        
		        //adds the current fragment to the backstack in case the back button is pressed
		        ft.addToBackStack(null);
		        
		        //commits the changes so that the more info fragment is shown
		        ft.commit();
		        
		        //shows the back button
		        getActionBar().setDisplayHomeAsUpEnabled(true);
	}
}
