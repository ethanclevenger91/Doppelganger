package edu.Drake.doppelganger;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
/**
 * Adapts NewsEntry objects onto views for lists
 */
public final class CelebrityEntryAdapter extends ArrayAdapter<CelebrityEntry> {
 
	private final List<CelebrityEntry> list;
	private final Activity context;
 
	public CelebrityEntryAdapter(Fragment context, List<CelebrityEntry> list) {
		super(context.getActivity(), R.layout.feed_list, list);
		this.context = context.getActivity();
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
	    holder.titleView.setText(list.get(position).getName());
        holder.imageView.setImageResource(model.getPic());
		
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