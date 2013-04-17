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
	    
	    //db.onUpgrade(db, 1, 2);
	    
	    //comment = new ArrayList<String>();
	    comment = comment();
	    feeds = new ArrayList<FeedsModel>();
	    //feeds = getModel();
	    
	    db.addContact(new FeedsModel("That is him", "Clayton Brady", 3, 0, 2));
	    //
	    List<FeedsModel> contacts = db.getAllContacts();
	    //
	    for (FeedsModel cn : contacts) {
	    	
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
		ArrayAdapter<FeedsModel> adapter = new FeedInteractiveArrayAdapter(this, feeds);
	    setListAdapter(adapter);
	  }
	
	/*
	public List<FeedsModel> getModel() {
	    List<FeedsModel> list = new ArrayList<FeedsModel>();
	    list.add(get("Jessica Harris",10,2,3,"urness","It must be his twin seriously",comment));
	    list.add(get("Mike Roger",300,100,123,"morrow","They look so alike",comment));
	    list.add(get("Sarah Smith",3,75,1,"inman","Check out these long lost twins",comment));
	    list.add(get("Jen Adams",17,25,3,"morrow","I can't believe how similar these two look.",comment));
	    list.add(get("Michael Jaes",2000,146,240,"inman","What do you think?",comment));
	    list.add(get("Mike Roger",100,200,57,"urness","Yes or No?",comment));
	    return list;
	  }
	  */

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
