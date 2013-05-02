package edu.Drake.doppelganger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.app.ActionBar;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
	private TextWatcher filterTextWatcher = null;
	List<CelebrityEntry> list = new ArrayList<CelebrityEntry>();
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 Activity mActivity = this.getActivity();
		 PopulateTask task = new PopulateTask(mActivity);
		 task.execute(new String[] { "http://www.ethanclevenger.com/MirrorMe/celebrity-master.txt" });
	     return inflater.inflate(R.layout.activity_celebrity, container, false);
	    }
	
	 private class PopulateTask extends AsyncTask<String, Void, List<CelebrityEntry>> {   
		 public Activity mActivity;

		    PopulateTask(Activity mActivity) {
		        this.mActivity = mActivity;
		    }
		 @Override
		    protected List<CelebrityEntry> doInBackground(String... urls) {
		      String name = "";
		      String pic = "";
		      List<CelebrityEntry> list = new ArrayList<CelebrityEntry>();
		      for (String url : urls) {
		        DefaultHttpClient client = new DefaultHttpClient();
		        HttpGet httpGet = new HttpGet(url);
		        try {
		          HttpResponse execute = client.execute(httpGet);
		          InputStream content = execute.getEntity().getContent();

		          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
		          while ((name = buffer.readLine()) != null) {
		            pic = buffer.readLine();
		            list.add(new CelebrityEntry(name, pic));
		          }

		        } catch (Exception e) {
		          e.printStackTrace();
		        }
		      }
		      return list;
		    }

		    @Override
		    protected void onPostExecute(List<CelebrityEntry> result) {
		      adapter = new CelebrityEntryAdapter(mActivity, result);
		      setListAdapter(adapter);
		    }
	 }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		filterText = (EditText) getView().findViewById(R.id.search_box);
		filterTextWatcher = new TextWatcher() {

			public void afterTextChanged(Editable s) {
		    }
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    }
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				adapter.getFilter().filter(s);
		    }
		};
		filterText.addTextChangedListener(filterTextWatcher);
		final ActionBar actionBar = getActivity().getActionBar();
	    actionBar.setCustomView(R.layout.custom_actionbar);
	    actionBar.setDisplayShowCustomEnabled(false);
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		
		CelebrityEntry celebrity = (CelebrityEntry) getListView().getItemAtPosition(position);
		Intent makeAPost = new Intent(v.getContext(), Post.class);
		makeAPost.putExtra("image", celebrity.getPic());
		makeAPost.putExtra("celeb", celebrity.getName());
		startActivity(makeAPost);
		
	}
	
	
	//private List<CelebrityEntry> getModel() {
	    //List<CelebrityEntry> list = new ArrayList<CelebrityEntry>();
	    //list.add(get("Adam Savage","http://www.ethanclevenger.com/MirrorMe/images/hanks.png"));
	    /*list.add(getOrig("Anne Hathaway",R.drawable.hathaway));
	    list.add(getOrig("Ellen Page",R.drawable.page));
	    list.add(getOrig("Jon Heder/Napolean Dynamite",R.drawable.ndynamite));
	    list.add(get("Kirsten Dunst",R.drawable.dunst));
	    list.add(get("Emma Stone",R.drawable.stone));
	    list.add(get("Pierce Brosnan",R.drawable.brosnan));
	    list.add(get("Zooey Deschanel",R.drawable.zooey));*/
	    //return list;
	 // }
}
