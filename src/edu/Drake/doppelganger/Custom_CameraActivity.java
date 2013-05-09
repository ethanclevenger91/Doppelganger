package edu.Drake.doppelganger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;


public class Custom_CameraActivity extends Activity {
    private Camera mCamera;
    private CameraPreview mCameraPreview;
    boolean isPicTaken = false;
    Bitmap bitmap;
    Bitmap bmap;
    int orientation;
    String myPath;
    Uri myUri;
    Button useButton;
    Button retakeButton;
    ImageButton gallery;
    ImageButton captureButton;
    private static int RESULT_LOAD_IMAGE = 1;
    String picturePath = null;

    /** Called when the activity is first created. */
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        mCamera = getCameraInstance();
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        
        //mCamera.setDisplayOrientation(90);
        Camera.Parameters p = mCamera.getParameters();
        
        p.set("jpeg-quality", 100);
        p.set("orientation", "portrait");
        p.set("rotation", 90);
        p.setPictureFormat(PixelFormat.JPEG);
       // p.setPreviewSize(preview.getHeight(), preview.getWidth());// here w h are reversed
        mCamera.setParameters(p);
        setDisplayOrientation(mCamera,90);
        
        mCameraPreview = new CameraPreview(this, mCamera);
        
        preview.addView(mCameraPreview);
<<<<<<< HEAD
        final Button useButton = (Button) findViewById(R.id.button_use);
        final Button captureButton = (Button) findViewById(R.id.button_capture);
=======
        useButton = (Button) findViewById(R.id.button_use);
        retakeButton = (Button) findViewById(R.id.button_retake);
        captureButton = (ImageButton) findViewById(R.id.button_capture);
        gallery = (ImageButton) findViewById(R.id.gallery);
>>>>>>> d67c77a8c1d16fc283334ff77cdf412a4afa3370
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(!isPicTaken){
            		mCamera.takePicture(shutterCallback, rawCallback, mPicture);
            		useButton.setVisibility(View.VISIBLE);
<<<<<<< HEAD
            		captureButton.setText("Retake");
=======
            		retakeButton.setVisibility(View.VISIBLE);
            		captureButton.setVisibility(View.INVISIBLE);
            		gallery.setVisibility(View.INVISIBLE);
>>>>>>> d67c77a8c1d16fc283334ff77cdf412a4afa3370
            	}
            	else {
            		mCamera.startPreview();
            		isPicTaken=false;
            		useButton.setVisibility(View.INVISIBLE);
            		captureButton.setText("Capture");
            	}
            }
        });
        ImageButton galleryButton = (ImageButton) findViewById(R.id.gallery);
		galleryButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 Intent i = new Intent(
						 Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				 			startActivityForResult(i, RESULT_LOAD_IMAGE);
			 }
		 });
        
    }

    /**
     * Helper method to access the camera returns null if it cannot get the
     * camera or does not exist
     * 
     * @return
     */
    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            // cannot get camera or does not exist
        }
        return camera;
    }
    
 // Called when shutter is opened
    ShutterCallback shutterCallback = new ShutterCallback() { 
        @Override
		public void onShutter() {
            isPicTaken = true;  //set flag true only when shutter is called.
            // 
        }
    };

    // Handles data for raw picture
    PictureCallback rawCallback = new PictureCallback() {
        @Override
		public void onPictureTaken(byte[] data, Camera camera) {
            //
        }
    };

    PictureCallback mPicture = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            myPath = pictureFile.getPath();
            myUri = Uri.fromFile(new File(myPath));
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                
                sendBroadcast(new Intent(
            		    Intent.ACTION_MEDIA_MOUNTED,
            		    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
               bitmap = BitmapHelper.decodeFile(new File(myPath), 0, 10, false);
               
                
            } catch (FileNotFoundException e) {

            } catch (IOException e) {
            } 
        }
    };
    
    protected void setDisplayOrientation(Camera camera, int angle){
	    Method downPolymorphic;
	    try
	    {
	        downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[] { int.class });
	        if (downPolymorphic != null)
	            downPolymorphic.invoke(camera, new Object[] { angle });
	    }
	    catch (Exception e1)
	    {
	    }
	}
    
    @SuppressLint("SimpleDateFormat")
	private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
        
        return mediaFile;
    }
    
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
            //bitmap = BitmapHelper.decodeFile(new File(picturePath), theImage.getWidth(), theImage.getHeight(), false);
            
            Intent intent = getIntent();
    		if(picturePath!=null)
    		{
    			intent.putExtra("return",picturePath);
    		}
        	setResult(RESULT_OK,intent);
        	finish();
        }
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
	
	public void usePic(View v){
        Intent intent = new Intent();
        intent.putExtra("return", myPath);
        setResult(RESULT_OK, intent); 
        
        super.finish();
	}
	
<<<<<<< HEAD
=======
	public void retakePic(View v) {
		mCamera.startPreview();
		isPicTaken=false;
		useButton.setVisibility(View.INVISIBLE);
		retakeButton.setVisibility(View.INVISIBLE);
		captureButton.setVisibility(View.VISIBLE);
		gallery.setVisibility(View.VISIBLE);
	}
	
>>>>>>> d67c77a8c1d16fc283334ff77cdf412a4afa3370
	@Override
	public void onDestroy()
	{   
	    Cleanup();
	    super.onDestroy();
	}

	private void Cleanup()
	{    
		if(bitmap!=null)
		{
			bitmap.recycle();
		}
	}
    
}