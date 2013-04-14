package edu.Drake.doppelganger;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class CelebritiesFragment extends ListFragment {

	//@Override
    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	 // Inflate the layout for this fragment
    //return inflater.inflate(R.layout.activity_celebrities, container, false);
//s}
	
		
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 ArrayAdapter<CelebrityEntry> adapter = new CelebrityEntryAdapter(this.getActivity(), getModel());
		 setListAdapter(adapter);
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		
		CelebrityEntry celebrity = (CelebrityEntry) getListView().getItemAtPosition(position);
		Intent makeAPost = new Intent(v.getContext(), Post.class);
		makeAPost.putExtra("image", celebrity.getPic());
		startActivity(makeAPost);
		
	}
	
	private List<CelebrityEntry> getModel() {
	    List<CelebrityEntry> list = new ArrayList<CelebrityEntry>();
	    list.add(get("Kirsten Dunst",R.drawable.dunst));
	    list.add(get("Adam Savage",R.drawable.savage));
	    list.add(get("Pierce Brosnan",R.drawable.brosnan));
	    list.add(get("Zooey Deschanel",R.drawable.zooey));
	    return list;
	  }
	
private CelebrityEntry get(String name, int pic) {
    return new CelebrityEntry(name, pic);
  }
}
