/*I would make this the feed - EC*/

package edu.Drake.doppelganger;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;



public class MainActivity extends Activity {

	public static Context appContext;
	
	protected class MyTabsListener implements ActionBar.TabListener {

	    private Fragment fragment;

	    public MyTabsListener(Fragment fragment) {
	        this.fragment = fragment;
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	    }

	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        ft.add(R.id.fragment_container, fragment, null);
	    }

	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        // some people needed this line as well to make it work: 
	        ft.remove(fragment);
	    }
	}
	
	@Override
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
