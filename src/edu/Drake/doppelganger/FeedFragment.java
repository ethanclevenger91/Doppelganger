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
	    list.add(get("Jessica Harris",10,2,3));
	    list.add(get("Mike Roger",300,100,123));
	    list.add(get("Sarah Smith",3,75,1));
	    list.add(get("Jen Adams",17,25,3));
	    list.add(get("Michael Jaes",2000,146,240));
	    list.add(get("Mike Roger",100,200,57));
	    return list;
	  }

	  private FeedsModel get(String s,int ups, int downs, int comments) {
	    return new FeedsModel(s,ups,downs,comments);
	  }

	
}
