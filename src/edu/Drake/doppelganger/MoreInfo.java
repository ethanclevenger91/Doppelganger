package edu.Drake.doppelganger;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoreInfo extends Activity {
	
	private String upCount;
	private String downCount;
	private String desc;
	private String image;
	private String[] commentList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_info);
		
		
		TextView descView = (TextView) findViewById(R.id.description);
		TextView upView = (TextView) findViewById(R.id.text_up);
		TextView downView = (TextView) findViewById(R.id.text_down);
		ImageButton photoButton = (ImageButton) findViewById(R.id.image_button1);
		Log.v("Here", "got views");
		
		upCount = getIntent().getStringExtra("ups");
		downCount = getIntent().getStringExtra("downs");
		desc = getIntent().getStringExtra("desc");
		image = getIntent().getStringExtra("image");
		commentList = getIntent().getStringArrayExtra("commentList");
		
		String uri = "raw/" + image;
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        photoButton.setImageResource(imageResource);
        
       // ScrollView scroll = (ScrollView) findViewById(R.id.scroll_view1);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_view1);
        
        //loop through array of comments and add to top of scroll view here
        for(int i=0; i<commentList.length;i++){
        	TextView tv1 = new TextView(this);
        	tv1.setText(commentList[i]);
        	tv1.setTextSize(20);
        	tv1.setBackgroundResource(R.drawable.back);
        	tv1.setPadding(20,20,20,20);
        	
        	LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, 10, 0, 0); // llp.setMargins(left, top, right, bottom);
        	tv1.setLayoutParams(llp);
        	
        	layout.addView(tv1,i);
        }
		
		descView.setText(desc);
		upView.setText(upCount);
		downView.setText(downCount);
		Log.v("Here", "textSet");

        //shows the back button
        getActionBar().setDisplayHomeAsUpEnabled(true);
	
	}
	
	@Override
	public void onBackPressed(){
		//disables the up button
		 getActionBar().setDisplayHomeAsUpEnabled(false);
		 finish();
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
		getMenuInflater().inflate(R.menu.activity_take_picture, menu);
		
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
	    }
	    //returns the item selected, in this case the up button
	    return super.onOptionsItemSelected(item);
	}
	
	private void cancelMenuItem() {
		//disables the up button
    	getActionBar().setDisplayHomeAsUpEnabled(false);
		onBackPressed();
	}
	
	
}