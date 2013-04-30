package edu.Drake.doppelganger;

import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NotificationsFragment extends ListFragment {

	public View ColoredView;
	
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    ListView listView = (ListView) getActivity().findViewById(R.id.mylist);
	    String[] values = new String[] { "Jessica Harris tagged you: Tom Cruise doppleganger (2hrs)", "Mike Roger comment on your Repert Grint doppleganger (4 hrs)", "Sarah Smith like your Rupert Grint doppleganger (yesterday)",
	            "Jen Adams liked your Rupert Grint doppleganger (yesterday)", "Michael Jaes likes the Sarah Smith: Zooey Deschanel doppleganger you posted (Monday)", "Mike Roger commented on your Rupert Grint doppleganger (Sunday)" };
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_list_item_1, values); 
	            
	      setListAdapter(adapter);
	      
	    //
	    /*
	    ArrayAdapter<FeedsModel> adapter = new FeedInteractiveArrayAdapter(this, getModel());
	    setListAdapter(adapter);
		*/
	}
	  
	/*
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
	  */
	
	

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // Do something with the data
		  /*if (ColoredView != null)
              ColoredView.setBackgroundColor(Color.WHITE); //original color
			*/
		  
		  Log.v("NotificationsFragment", "hi");
		  v.setBackgroundColor(Color.TRANSPARENT); //selected color
          ColoredView = v;
	  }

}
//
