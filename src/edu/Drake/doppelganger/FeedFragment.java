package edu.Drake.doppelganger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class FeedFragment extends ListFragment{
	public List<String> comment;
	public List<FeedsModel> feeds;
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    FeedSQLiteHelper db = new FeedSQLiteHelper(this.getActivity().getBaseContext());
	    
	    comment = comment();
	    feeds = new ArrayList<FeedsModel>();
	    
	    List<FeedsModel> contacts = db.getAllContacts();
	    if(contacts.size()==0)
	    {
	    	
	    	/*Drawable myDrawable = getResources().getDrawable(R.drawable.inman);
	    	Drawable celebDrawable = getResources().getDrawable(R.drawable.savage);
	    	
			Bitmap myCeleb = ((BitmapDrawable) myDrawable).getBitmap();
			Bitmap myCelebPic = ((BitmapDrawable) celebDrawable).getBitmap();
			
			myCelebPic = Bitmap.createScaledBitmap(myCelebPic, myCeleb.getWidth(), myCeleb.getHeight(), false);
			
			String combined = combineImages(myCeleb,myCelebPic,"/storage/sdcard0/tmp_recipes.jpg1366681084097.png");
	    	
	    	//adds Inman
	    	db.addContact(new FeedsModel("That is him", "Clayton Brady", 3, 0, 2, null,combined));
	    	
	    	myDrawable = getResources().getDrawable(R.drawable.urness);
	    	celebDrawable = getResources().getDrawable(R.drawable.brosnan);
	    	
			myCeleb = ((BitmapDrawable) myDrawable).getBitmap();
			myCelebPic = ((BitmapDrawable) celebDrawable).getBitmap();
			
			myCelebPic = Bitmap.createScaledBitmap(myCelebPic, myCeleb.getWidth(), myCeleb.getHeight(), false);
			
			combined = combineImages(myCeleb,myCelebPic,"/storage/sdcard0/tmp_recipes.jpg1366681084097.png");
	    	
	    	//adds Urness
	    	db.addContact(new FeedsModel("That is him", "Clayton Brady", 3, 0, 2, comment,combined));
	    	
	    	myDrawable = getResources().getDrawable(R.drawable.morrow);
	    	celebDrawable = getResources().getDrawable(R.drawable.dunst);
	    	
			myCeleb = ((BitmapDrawable) myDrawable).getBitmap();
			myCelebPic = ((BitmapDrawable) celebDrawable).getBitmap();
			
			myCelebPic = Bitmap.createScaledBitmap(myCelebPic, myCeleb.getWidth(), myCeleb.getHeight(), false);
			
			combined = combineImages(myCeleb,myCelebPic,"/storage/sdcard0/tmp_recipes.jpg1366681084097.png");
	    	
	    	//adds Amanda
	    	db.addContact(new FeedsModel("That is her", "Clayton Brady", 3, 0, 2, null,combined));*/
	    }
	    
	    for (FeedsModel cn : contacts) {
	    	
	    	/*	uncomment below to delete all posts	*/
	    	//db.deleteContact(cn);
            
	    	String log = "Id: "+cn.getId()+" ,Name: " + cn.getName() + " ,Caption: " + cn.getDesc() +
            		" ,Likes: " + cn.getUps() + ", Dislikes: " + cn.getDowns() + ", Comments: " + cn.getComments();
                // Writing Contacts to log 
          Log.d("Name: ", log);
	    }
	    
	    
	    
	    //
	    ArrayAdapter<FeedsModel> adapter = new FeedInteractiveArrayAdapter(this, contacts);
	    
	    setListAdapter(adapter);
	    
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
	    Bitmap myMap = Bitmap.createScaledBitmap(c, c.getWidth()-30, c.getHeight(), false);

	    comboImage.drawBitmap(myMap, 0f, 0f, null); 
	    comboImage.drawBitmap(s, c.getWidth()+30, 0f, null);
	    
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
	  
	public static final Integer[] images = { R.raw.urness,
        R.raw.inman, R.raw.morrow};
	
	
	public List<String> comment() {
		List<String> list = new ArrayList<String>();
		list.add("You are so right");
		list.add("Like looking into a mirror");
		list.add("They must be related");
		list.add("they are so similar that it is scary");
		return list;
	}
	
	public void refresh(){
		FeedSQLiteHelper db = new FeedSQLiteHelper(this.getActivity().getBaseContext());
		List<FeedsModel> contacts = db.getAllContacts();
		
		Log.v("FeedFrag", String.valueOf(contacts.size()));
		
		ArrayAdapter<FeedsModel> adapter = new FeedInteractiveArrayAdapter(this, contacts);
	    setListAdapter(adapter);
	  }

	  public FeedsModel get(String s,int ups, int downs, int comments, String image, String desc, List<String> commentList) {
	    return new FeedsModel(s,ups,downs,comments, image,desc,commentList);
	  }
	  
	  @Override
	public void onResume() {
	    super.onResume();
	  }

	  @Override
	public void onPause() {
	    super.onPause();
	  }

	
}
