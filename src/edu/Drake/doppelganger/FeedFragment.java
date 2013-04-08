package edu.Drake.doppelganger;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class FeedFragment extends ListFragment{
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    ArrayAdapter<FeedsModel> adapter = new FeedInteractiveArrayAdapter(this, getModel());
	    setListAdapter(adapter);
	    
	}
	  
	public static final Integer[] images = { R.raw.urness,
        R.raw.inman, R.raw.morrow};
	
	public static final String[] comments = { "You are so right", "Like looking into a mirror", 
		"They must be related", "They are so similar that is is scary", "They don't look anything alike, how can you people not see it"};
	
	
	private List<FeedsModel> getModel() {
	    List<FeedsModel> list = new ArrayList<FeedsModel>();
	    list.add(get("Jessica Harris",10,2,3,"urness","It must be his twin seriously",comments));
	    list.add(get("Mike Roger",300,100,123,"morrow","They look so alike",comments));
	    list.add(get("Sarah Smith",3,75,1,"inman","Check out these long lost twins",comments));
	    list.add(get("Jen Adams",17,25,3,"morrow","I can't believe how similar these two look.",comments));
	    list.add(get("Michael Jaes",2000,146,240,"inman","What do you think?",comments));
	    list.add(get("Mike Roger",100,200,57,"urness","Yes or No?",comments));
	    return list;
	  }

	  private FeedsModel get(String s,int ups, int downs, int comments, String image, String desc, String[] commentList) {
	    return new FeedsModel(s,ups,downs,comments, image,desc,commentList);
	  }

	
}
