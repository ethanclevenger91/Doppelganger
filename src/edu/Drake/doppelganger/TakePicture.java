package edu.Drake.doppelganger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class TakePicture extends Activity {

	Camera mCamera;
	private static final String TAG = "TakePicture";
	ImageButton galleryButton;
	ImageView theImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_picture);
		
		
		//safeCameraOpen(0);
		
		//disables the up button
		 getActionBar().setDisplayHomeAsUpEnabled(true);
		 
		 theImage = (ImageView) findViewById(R.id.localpic);
		 
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
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		theImage.setImageDrawable(Drawable.createFromPath(data.getStringExtra("imagePath")));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_take_picture, menu);
		
		return true;
	}
	
	public void cameraClick(View v){
		Intent intent = new Intent(v.getContext(), Custom_CameraActivity.class);
		startActivityForResult(intent,1);
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
	/*
	private boolean safeCameraOpen(int id) {
	    boolean qOpened = false;
	  
	    try {
	        releaseCameraAndPreview();
	        mCamera = Camera.open(id);
	        qOpened = (mCamera != null);
	    } catch (Exception e) {
	        Log.e(getString(R.string.app_name), "failed to open Camera");
	        e.printStackTrace();
	    }

	    return qOpened;    
	}

	private void releaseCameraAndPreview() {
	    mPreview.setCamera(mCamera);
	    if (mCamera != null) {
	        mCamera.release();
	        mCamera = null;
	    }
	}
	*/
}
