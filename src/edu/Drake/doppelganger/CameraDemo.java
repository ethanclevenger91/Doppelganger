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
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class CameraDemo extends Activity {
	private static final String TAG = "CameraDemo";
	public static final int MEDIA_TYPE_IMAGE = 1;
   private Camera mCamera;
   private Preview mCameraPreview;
    
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
       setContentView(R.layout.main);
        
       mCamera = getCameraInstance();
       Camera.Parameters p = mCamera.getParameters();
       
       if (Integer.parseInt(Build.VERSION.SDK) >= 8)
           setDisplayOrientation(mCamera, 90);
       else
       {
           if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
           {
               p.set("orientation", "portrait");
               p.set("rotation", 90);
           }
           if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
           {
               p.set("orientation", "landscape");
               p.set("rotation", 90);
           }
       }   
       
       
       mCameraPreview = new Preview(this, mCamera);
        
       FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
       preview.addView(mCameraPreview);
       
       
       final PictureCallback mPicture = new PictureCallback() {

           public void onPictureTaken(byte[] data, Camera camera) {

               File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);

               if (pictureFile == null){
                   return;
               }

               try {
                   FileOutputStream fos = new FileOutputStream(pictureFile);
                   fos.write(data);
                   fos.close();
                   MediaStore.Images.Media.insertImage(getContentResolver(), pictureFile.getAbsolutePath(), pictureFile.getName(), pictureFile.getName());
                   
                   sendBroadcast(new Intent(
                		    Intent.ACTION_MEDIA_MOUNTED,
                		    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                   
                   Log.v("MyCameraApp", "file wrote");
               } catch (FileNotFoundException e) {
            	   Log.v("MyCameraApp", "filenotfoundexecption");
               } catch (IOException e) {
            	   Log.v("MyCameraApp", "ioexception");
               }
             }
           };
       
       
     //Adding listener
       Button captureButton = (Button) findViewById(R.id.button_capture);
       captureButton.setOnClickListener(
               new View.OnClickListener() {
                    
                   @Override
                   public void onClick(View v) {
                       mCamera.takePicture(null, null, mPicture);
                        
                   }
               });
        
   }
   
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
   
   /** Create a File for saving the image */
   private static File getOutputMediaFile(int type){

       File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                 Environment.DIRECTORY_PICTURES), "Camera");

       if (! mediaStorageDir.exists()){
           if (! mediaStorageDir.mkdirs()){
               Log.v("MyCameraApp", "failed to create directory");
               return null;
           }
       }
       
       Log.v(TAG, "got directory");
       
       // Create a media file name
       String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
       File mediaFile;
       if (type == MEDIA_TYPE_IMAGE){
           mediaFile = new File(mediaStorageDir.getPath() + File.separator +
           "IMG_"+ timeStamp + ".jpg");
       } else {
           return null;
       }

       return mediaFile;
   }

   /**
    * Helper method to access the camera returns null if
    * it cannot get the camera or does not exist
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
   
   @Override
   protected void onPause() {
       super.onPause();
       releaseCamera();              // release the camera immediately on pause event
   }



   private void releaseCamera(){
       if (mCamera != null){
           mCamera.release();        // release the camera for other applications
           mCamera = null;
       }
   }
   
   
   
}

