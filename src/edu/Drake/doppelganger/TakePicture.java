package edu.Drake.doppelganger;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class TakePicture extends Activity {

	Camera mCamera;
	Preview mPreview;
	ImageButton galleryButton;
	
	private static final String TAG = "TakePicture";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_picture);
		
		
		//safeCameraOpen(0);
		
		//disables the up button
		 getActionBar().setDisplayHomeAsUpEnabled(true);
		 
		 galleryButton = (ImageButton) findViewById(R.id.gallery);
		 galleryButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 Intent intent = new Intent(Intent.ACTION_PICK,
					     android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				 startActivityForResult(intent, 0);
			 }
		 });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.v(TAG, "fucked up here");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_take_picture, menu);
		
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
	
	public void selectPic(View v) {
		Intent intent = new Intent(v.getContext(), Custom_CameraActivity.class);
		startActivityForResult(intent,1);
	}
	
}
