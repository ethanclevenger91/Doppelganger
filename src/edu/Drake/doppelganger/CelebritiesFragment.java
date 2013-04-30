package edu.Drake.doppelganger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import android.app.ActionBar;
=======
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

>>>>>>> b3b17a5b646d44419d28c24e47bcc3cea61ed5e4
import android.app.ListFragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
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
	DefaultHttpClient httpclient = new DefaultHttpClient();
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
		
		final ActionBar actionBar = getActivity().getActionBar();
	    actionBar.setCustomView(R.layout.custom_actionbar);
	    actionBar.setDisplayShowCustomEnabled(false);
		 
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
	
	private CelebrityEntry get(String name, int pic) {
		return new CelebrityEntry(name, pic);
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
	
	/*public List<CelebrityEntry> populate() throws IOException {
		List<CelebrityEntry> list = new ArrayList<CelebrityEntry>();
		HttpGet httppost = new HttpGet("http://www.ethanclevenger.com/MirrorMe/celebrity-master.txt");
		HttpResponse response = httpclient.execute(httppost);
        HttpEntity ht = response.getEntity();

        BufferedHttpEntity buf = new BufferedHttpEntity(ht);

        InputStream is = buf.getContent();


        BufferedReader r = new BufferedReader(new InputStreamReader(is));

        StringBuilder total = new StringBuilder();
        String name;
        String url;
        Drawable d;
        while ((name = r.readLine()) != null) {
        	url = r.readLine();
        	d = LoadImageFromWebOperations(url);
            list.add(get(name, d);
        }
	}*/
	
	public static Drawable LoadImageFromWebOperations(String url) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        return d;
	    } catch (Exception e) {
	        return null;
	    }
	}
	
	
}
