package edu.Drake.doppelganger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

@SuppressLint("SdCardPath")
public class FeedFragment extends ListFragment implements OnItemSelectedListener{
	public List<String> comment;
	public List<FeedsModel> feeds;
	public String userName;
	public Spinner spinner1;
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    FeedSQLiteHelper db = new FeedSQLiteHelper(this.getActivity().getBaseContext());
	    
	    final ActionBar actionBar = getActivity().getActionBar();
	    actionBar.setCustomView(R.layout.custom_actionbar);
	    actionBar.setDisplayShowCustomEnabled(true);
	    
	    getUserName();
	    
	    addListenerOnSpinnerItemSelection();
	    
	    comment = comment();
	    feeds = new ArrayList<FeedsModel>();
	    
	    long timestamp = Calendar.getInstance().getTimeInMillis();
	    
	    //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateString= DateFormat.format("MMMM/dd/yyyy", new Date(timestamp)).toString();
	    Log.v("FeedFragment", dateString);
	    
	    List<FeedsModel> contacts = db.getAllContacts();
	    /*if(contacts.size()==0)
	    {
	    	
	    	Drawable myDrawable = getResources().getDrawable(R.drawable.inman);
	    	Drawable celebDrawable = getResources().getDrawable(R.drawable.savage);
	    	
			Bitmap myCeleb = ((BitmapDrawable) myDrawable).getBitmap();
			Bitmap myCelebPic = ((BitmapDrawable) celebDrawable).getBitmap();
			
			myCelebPic = Bitmap.createScaledBitmap(myCelebPic, myCeleb.getWidth(), myCeleb.getHeight(), false);
			
			String combined = combineImages(myCeleb,myCelebPic,"/sdcard/");
			
			timestamp = Calendar.getInstance().getTimeInMillis();
			
	    	//adds Inman
	    	db.addContact(new FeedsModel("That is him", "Clayton Brady posted:", 3, 0, 2, null,combined, "100000194227483", timestamp));
	    	
	    	myDrawable = getResources().getDrawable(R.drawable.urness);
	    	celebDrawable = getResources().getDrawable(R.drawable.brosnan);
	    	
			myCeleb = ((BitmapDrawable) myDrawable).getBitmap();
			myCelebPic = ((BitmapDrawable) celebDrawable).getBitmap();
			
			myCelebPic = Bitmap.createScaledBitmap(myCelebPic, myCeleb.getWidth(), myCeleb.getHeight(), false);
			
			combined = combineImages(myCeleb,myCelebPic,"/sdcard/");
	    	
			timestamp = Calendar.getInstance().getTimeInMillis();
			
	    	//adds Urness
	    	db.addContact(new FeedsModel("That is him", "Sara Nelson posted:", 3, 0, 2, comment,combined, "111", timestamp));
	    	
	    	myDrawable = getResources().getDrawable(R.drawable.morrow);
	    	celebDrawable = getResources().getDrawable(R.drawable.dunst);
	    	
			myCeleb = ((BitmapDrawable) myDrawable).getBitmap();
			myCelebPic = ((BitmapDrawable) celebDrawable).getBitmap();
			
			myCelebPic = Bitmap.createScaledBitmap(myCelebPic, myCeleb.getWidth(), myCeleb.getHeight(), false);
			
			combined = combineImages(myCeleb,myCelebPic,"/sdcard/");
	    	
			timestamp = Calendar.getInstance().getTimeInMillis();
			
	    	//adds Amanda
	    	db.addContact(new FeedsModel("That is her", "Ethan Clevenger posted:", 3, 0, 2, null,combined, "101", timestamp));
	    }*/

	    for (FeedsModel cn : contacts) {
	    	//uncomment below to delete all posts
	    	//db.deleteContact(cn);
	    	String log = "Id: "+cn.getId()+" ,Name: " + cn.getName() + " ,Caption: " + cn.getDesc() +
            		" ,Likes: " + cn.getUps() + ", Dislikes: " + cn.getDowns() + ", Comments: " + cn.getComments();
                // Writing Contacts to log 
          Log.d("Name: ", log);
	    }
	    
	    //
	    contacts = db.getAllContacts();
	    ArrayAdapter<FeedsModel> adapter = new FeedInteractiveArrayAdapter(this, contacts);
	    
	    setListAdapter(adapter);
	    adapter.notifyDataSetChanged();
	    
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
	
	public void filterMe() {
		String me=null;
		
		me = userName;
		Log.v("Feed", me);
		
		FeedSQLiteHelper db = new FeedSQLiteHelper(this.getActivity().getBaseContext());
		List<FeedsModel> contacts = db.getMe(me);
		
		ArrayAdapter<FeedsModel> adapter = new FeedInteractiveArrayAdapter(this, contacts);
	    setListAdapter(adapter);
	    adapter.notifyDataSetChanged();
	}
	
	public void filterMostRecent() {
		FeedSQLiteHelper db = new FeedSQLiteHelper(this.getActivity().getBaseContext());
		List<FeedsModel> contacts = db.getMostRecent();
		
		ArrayAdapter<FeedsModel> adapter = new FeedInteractiveArrayAdapter(this, contacts);
	    setListAdapter(adapter);
	    adapter.notifyDataSetChanged();
	}
	
	public void getUserName(){
		
		//Log.v("FeedFragment", userName);
		Session.openActiveSession(getActivity(), true, new Session.StatusCallback() {
			////
			          // callback when session changes state
			          @Override
			          public void call(Session session, SessionState state, Exception exception) {
			            if (session.isOpened()) {
			              // make request to the /me API
			              Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
			            	  
			                // callback after Graph API response with user object
			                @Override
			                public void onCompleted(GraphUser user, Response response) {
			                  if (user != null) {
			                    TextView welcome = (TextView) getActivity().findViewById(R.id.welcome);
			                    //userName = user.getName();
			                    welcome.setText(user.getName());
			                   
			                    setUserName(user.getId());
			                  } 
			                }
			              });
			              
			              
			            }
			            //
			          }
			        });
		
	}
	
	
	public void refresh(){
		FeedSQLiteHelper db = new FeedSQLiteHelper(this.getActivity().getBaseContext());
		List<FeedsModel> contacts = db.getAllContacts();
		
		Log.v("FeedFrag", String.valueOf(contacts.size()));
		
		ArrayAdapter<FeedsModel> adapter = new FeedInteractiveArrayAdapter(this, contacts);
	    setListAdapter(adapter);
	  }

	  public FeedsModel get(String s,int ups, int downs, int comments, String image, String desc, List<String> commentList, String fid, long timestamp) {
	    return new FeedsModel(s,ups,downs,comments, image,desc,commentList, fid, timestamp);
	  }
	  
	  public void setUserName(String name){
		  this.userName = name;
	  }
	  
	  public void addListenerOnSpinnerItemSelection() {
			spinner1 = (Spinner) getActivity().findViewById(R.id.spinner1);
			spinner1.setOnItemSelectedListener(this);
	  }
	  
	  @Override
	public void onResume() {
	    super.onResume();
	  }

	  @Override
	public void onPause() {
	    super.onPause();
	  }

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {
		Log.v("Feed", String.valueOf(pos));
		
		if(pos == 1)
		{
			filterMostRecent();
		}
		if(pos == 3)
		{
			filterMe();
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
