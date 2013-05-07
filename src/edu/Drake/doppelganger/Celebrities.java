package edu.Drake.doppelganger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class Celebrities extends ListActivity {
	
	private EditText filterText = null;
	ArrayAdapter<CelebrityEntry> adapter = null;
	DefaultHttpClient httpclient = new DefaultHttpClient();
	private TextWatcher filterTextWatcher = null;
	Activity mActivity = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_celebrity);

		//disables the up button
		 getActionBar().setDisplayHomeAsUpEnabled(true);
		 filterText = (EditText) findViewById(R.id.search_box);
		 PopulateTask task = new PopulateTask(mActivity);
		 task.execute(new String[] { "http://www.ethanclevenger.com/MirrorMe/celebrity-master.txt" });
		 
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
		    }
		  }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_celebrities, menu);
		return true;
	}
	
	@Override
	//this is used on the up button press
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            
	        	onBackPressed();
	        		 
	        	//disables the up button
	        	getActionBar().setDisplayHomeAsUpEnabled(false);
	        		 
	        	return true;
	        case R.id.menu_cancel:
	        	cancelMenuItem();
	        	break;
	    }
	    //returns the item selected, in this case the up button
	    return super.onOptionsItemSelected(item);
	}
	
	public void useAsDoppelganger(View v){
		onBackPressed();
	}
	
	private void cancelMenuItem() {
		onBackPressed();
	}
	
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		
		CelebrityEntry celebrity = (CelebrityEntry) getListView().getItemAtPosition(position);
		Intent makeAPost = new Intent(v.getContext(), Post.class);
		makeAPost.putExtra("image", celebrity.getPic());
		makeAPost.putExtra("celeb", celebrity.getName());
		
		setResult(RESULT_OK,makeAPost);
    	finish();
	}
	
	
	
	/*private List<CelebrityEntry> getModel() {
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
	  }*/
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    filterText.removeTextChangedListener(filterTextWatcher);
	}
	
}
