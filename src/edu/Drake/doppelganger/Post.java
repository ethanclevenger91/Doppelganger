package edu.Drake.doppelganger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class Post extends Activity {
	
	ImageView theImage;
	String TAG = "path is: ";
	public String returnString;
	public String celebName;
	public int width;
	Bitmap bitmap;
	Bitmap myCeleb;
	Bitmap myMap;
	public int height;
	String url;
	boolean userPic = false;
	boolean celebPic = false;
	
	private static int REAUTH_ACTIVITY_CODE = 1010101;
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
        url = extras.getString("image");
        new GetBitmapFromURL().execute(url);
        celebPic = true;
        
        final EditText captionText = (EditText) findViewById(R.id.edit_text_caption);
        
        captionText.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                    KeyEvent event) {
                if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    // NOTE: In the author's example, he uses an identifier
                    // called searchBar. If setting this code on your EditText
                    // then use v.getWindowToken() as a reference to your 
                    // EditText is passed into this callback as a TextView

                    in.hideSoftInputFromWindow(captionText
                            .getApplicationWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                   //userValidateEntry();
                   // Must return true here to consume event
                   return true;

                }
                return false;
            }
        });

        //shows the back button
        getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_take_picture, menu);
		
		return true;
	}
	
	private class GetBitmapFromURL extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			
			try {
	            Log.v("src",params[0]);
	            URL url = new URL(params[0]);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoInput(true);
	            connection.connect();
	            InputStream input = connection.getInputStream();
	            Bitmap myBitmap = BitmapFactory.decodeStream(input);
	            return myBitmap;
	        } catch (IOException e) {
	            e.printStackTrace();
	            Log.e("Exception","error getting bitmap");
	            return null;
	        }
		}
		 @Override
		    protected void onPostExecute(Bitmap result) {
			 myCeleb = result;
			 theImage.setImageBitmap(result);
		 }
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
		bitmap = BitmapHelper.decodeFile(new File(returnString), myImage.getWidth(), myImage.getHeight(), false);
		
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
		
		
		myCeleb = Bitmap.createScaledBitmap(myCeleb, bitmap.getWidth(), bitmap.getHeight(), false);
		
		String combined = combineImages(bitmap,myCeleb,returnString);
		Log.v("Post", returnString);
		
    	intent.putExtra("caption", myCaption.getText().toString());
    	intent.putExtra("photo", combined);
    	intent.putExtra("celeb", celebName);
    	
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
	    myMap = Bitmap.createScaledBitmap(c, c.getWidth()-50, c.getHeight(), false);

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
	public boolean dispatchTouchEvent(MotionEvent event) {

	    View v = getCurrentFocus();
	    boolean ret = super.dispatchTouchEvent(event);

	    if (v instanceof EditText) {
	        View w = getCurrentFocus();
	        int scrcoords[] = new int[2];
	        w.getLocationOnScreen(scrcoords);
	        float x = event.getRawX() + w.getLeft() - scrcoords[0];
	        float y = event.getRawY() + w.getTop() - scrcoords[1];

	        Log.d("Activity", "Touch event "+event.getRawX()+","+event.getRawY()+" "+x+","+y+" rect "+w.getLeft()+","+w.getTop()+","+w.getRight()+","+w.getBottom()+" coords "+scrcoords[0]+","+scrcoords[1]);
	        if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) { 

	            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
	        }
	    }
	return ret;
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
		
        super.onActivityResult(requestCode, resultCode, data);
        
        Log.v("post", "returned with code: " + requestCode);
        
        if (requestCode == REAUTH_ACTIVITY_CODE) {
        	if(resultCode == RESULT_OK) {
                // Do nothing for now
            }
        }
        
        if(requestCode==1)
		{
			if(resultCode==RESULT_OK)
			{
		        if(data.getExtras()!=null){
		        	
		        	theImage = (ImageView) findViewById(R.id.select_celeb);
		        	String i = data.getStringExtra("image");
		        	new GetBitmapFromURL().execute(i);
		            
		            theImage.setScaleType(ScaleType.FIT_XY);
		            celebPic = true;
		            celebName = data.getStringExtra("celeb");
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
					bitmap = BitmapHelper.decodeFile(new File(returnString), myImage.getWidth(), myImage.getHeight(), false);
					
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
		            userPic = true;
				}
			}
		}
		if(userPic == true && celebPic == true)
		{
			ImageButton postButton = (ImageButton) findViewById(R.id.post_button);
			postButton.setVisibility(View.VISIBLE);
		}
    }
	
	private void startPickerActivity(Uri data, int requestCode) {
	     Intent intent = new Intent();
	     intent.setData(data);
	     intent.setClass(this, PickerActivity.class);
	     startActivityForResult(intent, requestCode);
	 }
	
	public void findFriends(View v)
	{
		startPickerActivity(PickerActivity.FRIEND_PICKER, 1010101);
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
		if(myMap != null)
		{
			myMap.recycle();
		}
		if(myCeleb != null)
		{
			myCeleb.recycle();
		}
	}

	
}

