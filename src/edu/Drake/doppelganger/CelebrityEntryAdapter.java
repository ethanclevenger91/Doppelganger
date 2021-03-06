package edu.Drake.doppelganger;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import android.app.Activity;
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
 
	public CelebrityEntryAdapter(Activity context, List<CelebrityEntry> list) {
		super(context, R.layout.activity_celebrity, list);
		this.context = context;
	    this.list = list;
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
	    int id = context.getResources().getIdentifier("edu.Drake.doppelganger:drawable/" + "loading", null, null);
	    holder.imageView.setImageResource(id);
	    
	    ImageLoader imageLoader = ImageLoader.getInstance();
	    imageLoader.displayImage(model.getPic(), holder.imageView);
	    //new GetBitmapFromURL(holder).execute(model.getPic());
		//
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
