package edu.Drake.doppelganger;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Post extends Activity {
	
	ImageView theImage;
	String TAG = "post.java";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		
	    Bundle extras = getIntent().getExtras();
        if (extras == null) {
          return;
        }
        Log.v(TAG, "got this far");
        theImage = (ImageView) findViewById(R.id.select_celeb);
        theImage.setImageResource(extras.getInt("image"));

        //shows the back button
        getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_take_picture, menu);
		
		return true;
	}
	
	@Override
	public void onBackPressed() {
		//disables the up button
		 getActionBar().setDisplayHomeAsUpEnabled(false);
		finish();
	}
	
	public void selectCeleb(View v) {
		Intent intent = new Intent(v.getContext(), Celebrities.class);
		startActivityForResult(intent,1);
	}
	
	public void selectPic(View v) {
		Intent intent = new Intent(v.getContext(), TakePicture.class);
		startActivity(intent);
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
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
		
        super.onActivityResult(requestCode, resultCode, data);
        
        if(requestCode==1)
		{
			if(resultCode==RESULT_OK)
			{
		        if(data.getExtras()!=null){
		        	theImage = (ImageView) findViewById(R.id.select_celeb);
		        	int i = data.getIntExtra("image", 1);
		            theImage.setImageResource(i);
		        }
			}
		}
    }
	
}

