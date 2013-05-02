package edu.Drake.doppelganger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//
 
/**
 * Adapts CelebrityEntry objects onto views for lists
 */
public final class CelebrityEntryAdapter extends ArrayAdapter<CelebrityEntry> {
 
	private final List<CelebrityEntry> list;
	private final Activity context;
	Bitmap bimage = null;
 
	public CelebrityEntryAdapter(Activity context, List<CelebrityEntry> list) {
		super(context, R.layout.activity_celebrity, list);
		this.context = context;
	    this.list = list;
	}
	
	private class GetBitmapFromURL extends AsyncTask<String, Void, Bitmap> {

		private ViewHolder mViewHolder = null;
		
		GetBitmapFromURL(ViewHolder mViewHolder)
		{
			this.mViewHolder = mViewHolder;
		}
		@Override
		protected Bitmap doInBackground(String... params) {
			
			try {
	            Log.v("src",params[0]);
	            URL url = new URL(params[0]);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoInput(true);
	            connection.connect();
	            InputStream input = connection.getInputStream();
	            Bitmap myBitmap = BitmapFactory.decodeStream(input);
	            Log.v("Bitmap","returned");
	            return myBitmap;
	        } catch (IOException e) {
	            e.printStackTrace();
	            Log.e("Exception","error getting bitmap");
	            return null;
	        }
		}
		 @Override
		    protected void onPostExecute(Bitmap result) {
			 bimage = result;
			 mViewHolder.imageView.setImageBitmap(bimage);
			 Log.v("made it", "made it");
		 }
	}

 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
	    CelebrityEntry model = getItem(position);
	    
	    if (convertView == null) {
	      LayoutInflater inflator = context.getLayoutInflater();
	      view = inflator.inflate(R.layout.activity_celebrities, parent,false);
	      final ViewHolder viewHolder = new ViewHolder();
		
	      viewHolder.titleView = (TextView) view.findViewById(R.id.celebrity_entry_name);
	      viewHolder.imageView = (ImageView) view.findViewById(R.id.celebrity_entry_pic);
	      
	      view.setTag(viewHolder);
	      viewHolder.titleView.setTag(list.get(position));
	      viewHolder.imageView.setTag(list.get(position));
	      
	    }
	    else {
		      view = convertView;
		      ((ViewHolder) view.getTag()).titleView.setTag(list.get(position));
		      ((ViewHolder) view.getTag()).imageView.setTag(list.get(position));
		      
		    }
	    
	    ViewHolder holder = (ViewHolder) view.getTag();
	    holder.titleView.setText(model.getName());
	    new GetBitmapFromURL(holder).execute(model.getPic());
		
	return view;
}
	
	/**
	 * ViewHolder allows us to avoid re-looking up view references
	 * Since views are recycled, these references will never change
	 */
	private static class ViewHolder {
		public TextView titleView;
		public ImageView imageView;
	}
	
	
}
