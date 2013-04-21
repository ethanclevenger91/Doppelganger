package edu.Drake.doppelganger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


public class Custom_CameraActivity extends Activity {
    private Camera mCamera;
    private CameraPreview mCameraPreview;
    boolean isPicTaken = false;
    Bitmap bitmap;
    Bitmap bmap;
    int orientation;
    String myPath;
    Uri myUri;

    /** Called when the activity is first created. */
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

        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(!isPicTaken){
            		mCamera.takePicture(shutterCallback, rawCallback, mPicture);
            	}
            	else {
            		//mCamera.startPreview();
            		//isPicTaken=false;
            	}
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
               bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
               
                
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
        String path = myUri.getEncodedPath();
        intent.putExtra("imageUri", path);
        setResult(RESULT_OK, intent); 
        
        super.finish();
	}
	
	public void retakePic(View v) {
		mCamera.startPreview();
		isPicTaken=false;
	}
	
	@Override
	public void onDestroy()
	{   
	    Cleanup();
	    super.onDestroy();
	}

	private void Cleanup()
	{    
	    bitmap.recycle(); 
	}
    
}