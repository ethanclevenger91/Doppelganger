package edu.Drake.doppelganger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class Post extends Activity {
	
	ImageView theImage;
	String TAG = "path is: ";
	public String returnString;
	public int celebPath;
	public int width;
	public int height;
	
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
		 
		 Intent intent = getIntent();
	    	
	    setResult(RESULT_CANCELED,intent);
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
	
	public void postToFeed(View v) {
		Intent intent = getIntent();
		
		EditText myCaption = (EditText) findViewById(R.id.edit_text_caption);
		
		ImageView myImage = (ImageView) findViewById(R.id.select_pic);
		Bitmap bitmap = BitmapHelper.decodeFile(new File(returnString), myImage.getWidth(), myImage.getHeight(), false);
		
		try {
            ExifInterface exif = new ExifInterface(returnString);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            }
            else if (orientation == 3) {
                matrix.postRotate(180);
            }
            else if (orientation == 8) {
                matrix.postRotate(270);
            }
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true); // rotating bitmap
            Log.v("Post", String.valueOf(bitmap.getWidth()));
            Log.v("Post", String.valueOf(bitmap.getHeight()));
		}
        catch (Exception e) {

        }
		//
		Drawable myDrawable = getResources().getDrawable(celebPath);
		Bitmap myCeleb = ((BitmapDrawable) myDrawable).getBitmap();
		
		myCeleb = Bitmap.createScaledBitmap(myCeleb, bitmap.getWidth(), bitmap.getHeight(), false);
		
		String combined = combineImages(bitmap,myCeleb,returnString);
		Log.v("Post", returnString);
		
    	intent.putExtra("caption", myCaption.getText().toString());
    	intent.putExtra("photo", combined);
    	intent.putExtra("celeb", String.valueOf(celebPath));
    	
    	Log.v("Post", combined);
    	
	    setResult(RESULT_OK,intent);
	    finish();
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
	
	public String combineImages(Bitmap c, Bitmap s, String loc) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom 
	    Bitmap cs = null; 

	    int width, height = 0; 

	    Log.v("Post", String.valueOf(s.getWidth()));
	    Log.v("Post", String.valueOf(c.getWidth()));
	    
	    if(c.getWidth() > s.getWidth()) { 
	      width = c.getWidth() + c.getWidth(); 
	      height = c.getHeight(); 
	    } else { 
	      width = c.getWidth() + c.getWidth(); 
	      height = c.getHeight(); 
	    } 

	    cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); 
	    Log.v("Post", "here!");
	    
	    Canvas comboImage = new Canvas(cs); 
	    Bitmap myMap = Bitmap.createScaledBitmap(c, c.getWidth()-50, c.getHeight(), false);

	    comboImage.drawBitmap(myMap, 0f, 0f, null); 
	    comboImage.drawBitmap(s, c.getWidth()+50, 0f, null);
	    
	    Log.v("Post", "here!!");

	    // this is an extra bit I added, just in case you want to save the new image somewhere and then return the location 
	    String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png"; 

	    OutputStream os = null; 
	    try { 
	      os = new FileOutputStream(loc + tmpImg); 
	      cs.compress(CompressFormat.PNG, 100, os); 
	      Log.v("Post", "here!!!");
	    } catch(IOException e) { 
	      Log.e("combineImages", "problem combining images", e); 
	      Log.v("Post", "here!!!!");
	    }

	    Log.v("Post", "it works");
	    return (loc+tmpImg); 
	  } 
	
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
		            theImage.setScaleType(ScaleType.FIT_XY);
		            celebPath = i;
		        }
			}
		}
		if(requestCode==SELECT_FOR_PIC)
		{
			if(resultCode==RESULT_OK)
			{
				
				if(data.getExtras()!=null) {
					
					ImageView myImage = (ImageView) findViewById(R.id.select_pic);
					myImage.setScaleType(ScaleType.FIT_XY);
					
					returnString = data.getStringExtra("return");
					Bitmap bitmap = BitmapHelper.decodeFile(new File(returnString), myImage.getWidth(), myImage.getHeight(), false);
					
		            try {
		                ExifInterface exif = new ExifInterface(returnString);
		                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
		                Log.d("EXIF", "Exif: " + orientation);
		                Matrix matrix = new Matrix();
		                if (orientation == 6) {
		                    matrix.postRotate(90);
		                }
		                else if (orientation == 3) {
		                    matrix.postRotate(180);
		                }
		                else if (orientation == 8) {
		                    matrix.postRotate(270);
		                }
		                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true); // rotating bitmap
		            }
		            catch (Exception e) {

		            }
		            myImage.setImageBitmap(bitmap);
				}
			}
		}
    }
	
}

