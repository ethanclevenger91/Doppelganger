package edu.Drake.doppelganger;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class FeedFragment extends ListFragment{
	
	private static final String TAG = "More Info";
	
	/*
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.v(TAG, "button pressed");
		Intent intent = new Intent(v.getContext(), MoreInfo.class);
		startActivity(intent);
	}
	*/
/*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    
		
		
		// Inflate the layout for this fragment
        //return inflater.inflate(R.layout.activity_feed, container, false);
		
	}
*/
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    
	    /*
	    ListView listView = (ListView) getActivity().findViewById(R.id.mylist);
	    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
	            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
	            "Linux", "OS/2" };
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_list_item_1, values);
	        setListAdapter(adapter);
	      }
	    */
	    //View rootView = inflater.inflate(R.layout.activity_feed, container, false);
		//ImageButton buttonimage = (ImageButton) rootView.findViewById(R.id.image_button1);
		
		//buttonimage.setOnClickListener(this);
	    
	    
	    ArrayAdapter<FeedsModel> adapter = new FeedInteractiveArrayAdapter(this, getModel());
	    setListAdapter(adapter);
	    
	}
	  
	
	private List<FeedsModel> getModel() {
	    List<FeedsModel> list = new ArrayList<FeedsModel>();
	    list.add(get("Jessica Harris tagged you: Tom Cruise doppleganger (2hrs)"));
	    list.add(get("Mike Roger comment on your Repert Grint doppleganger (4 hrs)"));
	    list.add(get("Sarah Smith like your Rupert Grint doppleganger (yesterday)"));
	    list.add(get("Jen Adams liked your Rupert Grint doppleganger (yesterday)"));
	    list.add(get("Michael Jaes likes the Sarah Smith: Zooey Deschanel doppleganger you posted (Monday)"));
	    list.add(get("Mike Roger commented on your Rupert Grint doppleganger (Sunday)"));
	    // Initially select one of the items
	    list.get(1).setSelected(true);
	    return list;
	  }

	  private FeedsModel get(String s) {
	    return new FeedsModel(s);
	  }

	
}
