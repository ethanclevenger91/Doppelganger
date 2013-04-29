package edu.Drake.doppelganger;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class CelebritiesFragment extends ListFragment {
//
	private EditText filterText = null;
	ArrayAdapter<CelebrityEntry> adapter = null;
	private TextWatcher filterTextWatcher = new TextWatcher() {

	    public void afterTextChanged(Editable s) {
	    }

	    public void beforeTextChanged(CharSequence s, int start, int count,
	            int after) {
	    }

	    public void onTextChanged(CharSequence s, int start, int before,
	            int count) {
	        adapter.getFilter().filter(s);
	    }

	};
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        //Log.i("Right", "onCreateView()");
	        return inflater.inflate(R.layout.activity_celebrity, container, false);
	    }
	
		
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		filterText = (EditText) getView().findViewById(R.id.search_box);
		filterText.addTextChangedListener(filterTextWatcher);
		 
		 adapter = new CelebrityEntryAdapter(this.getActivity(), getModel());
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
	    list.add(get("Adam Savage",R.drawable.savage));
	    list.add(get("Anne Hathaway",R.drawable.hathaway));
	    list.add(get("Ellen Page",R.drawable.page));
	    list.add(get("Jon Heder/Napolean Dynamite",R.drawable.ndynamite));
	    list.add(get("Kirsten Dunst",R.drawable.dunst));
	    list.add(get("Emma Stone",R.drawable.stone));
	    list.add(get("Pierce Brosnan",R.drawable.brosnan));
	    list.add(get("Zooey Deschanel",R.drawable.zooey));
	    return list;
	  }
	
private CelebrityEntry get(String name, int pic) {
    return new CelebrityEntry(name, pic);
  }
}
