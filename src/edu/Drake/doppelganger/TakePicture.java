package edu.Drake.doppelganger;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class TakePicture extends Activity {

	Camera mCamera;
	ImageButton galleryButton;
	ImageView theImage;
	String picturePath = null;
	Bitmap bitmap;
	String TAG = "path is: ";
	
	private static int RESULT_LOAD_IMAGE = 1;
	private static int TAKE_CUSTOM_PIC = 38124;
	
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
				 Intent i = new Intent(
						 Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				 			startActivityForResult(i, RESULT_LOAD_IMAGE);
			 }
		 });
		 
		 
	}
	
	/*public void onBackPressed() {

	    }*/
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
        
		
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            
            //Drawable image = Drawable.createFromPath(picturePath);
            //theImage.setImageDrawable(image);
            bitmap = BitmapHelper.decodeFile(new File(picturePath), theImage.getWidth(), theImage.getHeight(), false);
            
            theImage.setImageBitmap(bitmap);
            
            
            
        }
        if (requestCode == TAKE_CUSTOM_PIC && resultCode == RESULT_OK && null != data) {
        	
        	picturePath = data.getStringExtra("imageUri");
        	Log.v(TAG, picturePath);
        	bitmap = BitmapHelper.decodeFile(new File(picturePath), theImage.getWidth(), theImage.getHeight(), false);
            theImage.setImageBitmap(bitmap);
        }
        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_take_picture, menu);
		
		return true;
	}
	
	public void cameraClick(View v){    
		Intent intent = new Intent(v.getContext(), Custom_CameraActivity.class);
		startActivityForResult(intent, TAKE_CUSTOM_PIC);
	}
	
	public void returnSelection(View v) {
		Intent intent = getIntent();
    	
		if(picturePath!=null)
		{
			intent.putExtra("return",picturePath);
		}
    	setResult(RESULT_OK,intent);
    	finish();
	}
	
	
	
	@Override
	//this is used on the up button press
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            
	        	onBackPressed();
	        		 //
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
		//disables the up button
    	getActionBar().setDisplayHomeAsUpEnabled(false);
		onBackPressed();
	}
	
	@Override
	public void onDestroy()
	{   
	    Cleanup();
	    super.onDestroy();
	}

	private void Cleanup()
	{    
		if(bitmap != null)
		{
			bitmap.recycle(); 
		}
	}
}
