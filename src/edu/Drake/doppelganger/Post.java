package edu.Drake.doppelganger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class Post extends Activity {
	
	ImageView theImage;
	String TAG = "path is: ";
	
	private static int SELECT_FOR_PIC = 5713;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		
	    Bundle extras = getIntent().getExtras();
        if (extras == null) {
          return;
        }
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
		startActivityForResult(intent,SELECT_FOR_PIC);
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
	
	/*
	private Bitmap decodeFile(File f){
	    try {
	        //Decode image size
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;
	        BitmapFactory.decodeStream(new FileInputStream(f),null,o);

	        //The new size we want to scale to
	        final int REQUIRED_SIZE=70;

	        //Find the correct scale value. It should be the power of 2.
	        int scale=1;
	        while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
	            scale*=2;

	        //Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize=scale;
	        return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	    } catch (FileNotFoundException e) {}
	    return null;
	}
	*/
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
		
        super.onActivityResult(requestCode, resultCode, data);
        
        Log.v("post", "returned with code: " + requestCode);
        
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
		if(requestCode==SELECT_FOR_PIC)
		{
			if(resultCode==RESULT_OK)
			{
				
				if(data.getExtras()!=null) {
					
					ImageView myImage = (ImageView) findViewById(R.id.select_pic);
					String returnString = data.getStringExtra("return");
					Bitmap bitmap = BitmapHelper.decodeFile(new File(returnString), myImage.getWidth(), myImage.getHeight(), false);
					Log.v(TAG, returnString);
		            myImage.setImageBitmap(bitmap);
					
					//theImage.setImageBitmap(BitmapFactory.decodeFile(returnString));
					//Drawable image = Drawable.createFromPath(returnString);
		            //theImage.setImageDrawable(image);
		            
				}
			}
		}
    }
	
}

