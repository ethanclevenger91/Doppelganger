package edu.Drake.doppelganger;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
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
	    //
	    /*
	     * uncomment below to add a post with name: Clayton Brady, 
	     * caption: That is him, 3 likes, 0 dislikes, 2 comments
	    */
	    //db.addContact(new FeedsModel("That is him", "Clayton Brady", 3, 0, 2, comment));//
	    
	    List<FeedsModel> contacts = db.getAllContacts();
	    
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
