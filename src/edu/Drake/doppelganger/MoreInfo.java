package edu.Drake.doppelganger;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MoreInfo extends Activity {
	
	private String upCount;
	private String downCount;
	private String desc;
	private String image;
	private String name;
	private List<String> commentList;
	private String id;
	
	public void addComment(View v) {
		
		 Intent intent = new Intent(getBaseContext(), AddComment.class);
 		startActivityForResult(intent,1);
 		overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_info);
		
		
		TextView descView = (TextView) findViewById(R.id.description);
		TextView upView = (TextView) findViewById(R.id.text_up);
		TextView downView = (TextView) findViewById(R.id.text_down);
		ImageView photoButton = (ImageView) findViewById(R.id.image_button1);
		Log.v("Here", "got views");
		
		upCount = getIntent().getStringExtra("ups");
		downCount = getIntent().getStringExtra("downs");
		desc = getIntent().getStringExtra("desc");
		image = getIntent().getStringExtra("image");
		id = getIntent().getStringExtra("id");
		name = getIntent().getStringExtra("name");
		commentList = getIntent().getStringArrayListExtra("commentList");
		
		String uri = "raw/" + image;
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        photoButton.setImageResource(imageResource);
        
        int newHeight = (int) (getWindowManager().getDefaultDisplay().getHeight() /2.25);
		int newWidth = getWindowManager().getDefaultDisplay().getWidth();
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
			    newWidth, newHeight);
		params.setMargins(15, 0, 15, 0);
		params.addRule(RelativeLayout.BELOW, R.id.textView1);
		
		photoButton.setLayoutParams(params);
        
        
       // ScrollView scroll = (ScrollView) findViewById(R.id.scroll_view1);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_view1);
        
        //loop through array of comments and add to top of scroll view here
        if(commentList!=null){
        	
        	for(int i=0; i<commentList.size();i++){
        		TextView tv1 = new TextView(this);
        		tv1.setText(commentList.get(i));
        		tv1.setTextSize(20);
        		tv1.setBackgroundResource(R.drawable.back);
        		tv1.setPadding(20,20,20,20);
        	
        		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            	llp.setMargins(0, 10, 0, 0); // llp.setMargins(left, top, right, bottom);
            	tv1.setLayoutParams(llp);
        		layout.addView(tv1,i);
        	}
        }
		descView.setText(desc);
		upView.setText(upCount);
		downView.setText(downCount);

        //shows the back button
        getActionBar().setDisplayHomeAsUpEnabled(true);
	
	}
	
	@Override
	public void onBackPressed(){
		//disables the up button
		 getActionBar().setDisplayHomeAsUpEnabled(false);
		 

	    	Intent intent = getIntent();
	    	intent.putExtra("ups", upCount);
	    	intent.putExtra("downs", downCount);
	    	intent.putExtra("id", id);
	    	intent.putExtra("desc", desc);
	    	intent.putExtra("name", name);
	    	
	    	if(commentList!=null) {
	    		intent.putExtra("comment", commentList.size());
	    		intent.putStringArrayListExtra("commentList", (ArrayList<String>) commentList);
	    	}
	    	
	    	setResult(RESULT_OK,intent);
	    	finish();
		 
	}
	
	public void upVote(View v) {
		int ups = Integer.parseInt(upCount);
		ups++;
		upCount = String.valueOf(ups);

		TextView upView = (TextView) findViewById(R.id.text_up);
		upView.setText(upCount);
	}
	
	public void downVote(View v) {
		int downs = Integer.parseInt(downCount);
		downs++;
		downCount = String.valueOf(downs);

		TextView downView = (TextView) findViewById(R.id.text_down);
		downView.setText(downCount);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_more_info, menu);
		
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
	    }
	    //returns the item selected, in this case the up button
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
		
        super.onActivityResult(requestCode, resultCode, data);
        
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_view1);
        
        if(data.getExtras()!=null){
        	
        	TextView tv1 = new TextView(this);
        	tv1.setText(data.getStringExtra("post"));
        	
        	tv1.setTextSize(20);
        	tv1.setBackgroundResource(R.drawable.back);
        	tv1.setPadding(20,20,20,20);
        	
        	LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, 10, 0, 0); // llp.setMargins(left, top, right, bottom);
        	tv1.setLayoutParams(llp);
        	if(commentList!=null){
        		commentList.add(tv1.getText().toString());
        		layout.addView(tv1,commentList.size()-1);
        	}
        	else{
        		List<String> newList = new ArrayList<String>();
        		newList.add(tv1.getText().toString());
        		layout.addView(tv1,newList.size()-1);
        		commentList = newList;
        	}
        }
    }
	
	
}