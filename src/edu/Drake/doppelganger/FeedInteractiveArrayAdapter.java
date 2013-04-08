package edu.Drake.doppelganger;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

public class FeedInteractiveArrayAdapter extends ArrayAdapter<FeedsModel> {

	  private final List<FeedsModel> list;
	  private final Activity context;

	  public FeedInteractiveArrayAdapter(Fragment context, List<FeedsModel> list) {
	    super(context.getActivity(), R.layout.feed_list, list);
	    this.context = context.getActivity();
	    this.list = list;
	  }

	  
	  static class ViewHolder {
		  protected TextView name;
		  protected TextView upCount;
		  protected TextView downCount;
		  protected TextView commentCount;
		  protected ImageButton upButton;
		  protected ImageButton downButton;
		  protected boolean selected;
	  }
	  
	  

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    if (convertView == null) {
	      LayoutInflater inflator = context.getLayoutInflater();
	      view = inflator.inflate(R.layout.activity_feed, parent,false);
	      final ViewHolder viewHolder = new ViewHolder();
	      viewHolder.name = (TextView) view.findViewById(R.id.textView1);
	      viewHolder.upCount = (TextView) view.findViewById(R.id.text_up);
	      viewHolder.downCount = (TextView) view.findViewById(R.id.text_down);
	      viewHolder.commentCount = (TextView) view.findViewById(R.id.text_comment);
	      viewHolder.upButton = (ImageButton) view.findViewById(R.id.image_up_vote);
	      viewHolder.downButton = (ImageButton) view.findViewById(R.id.image_down_vote);
	      
	      viewHolder.upButton.setOnClickListener(new OnClickListener() {
	    	  @Override
				public void onClick(View v) {
	    		  	FeedsModel element = (FeedsModel) viewHolder.upCount
	                      .getTag();
	    		  	if(!element.isSelected()) {
	                  element.incrementUp();
	                  viewHolder.upCount.setText(element.getUps());
	                  element.setSelected(true);
	    		  	}
				}
	      });

	      viewHolder.downButton.setOnClickListener(new OnClickListener() {
	    	  @Override
				public void onClick(View v) {
	    		  FeedsModel element = (FeedsModel) viewHolder.downCount
	                      .getTag();
	    		  if(!element.isSelected()) {
	                  element.incrementDown();
	                  viewHolder.downCount.setText(element.getDowns());
	                  element.setSelected(true);
	    		  }
	    	  }
	      });
	      
	      view.setTag(viewHolder);
	      viewHolder.upCount.setTag(list.get(position));
	      viewHolder.upButton.setTag(list.get(position));
	      viewHolder.downCount.setTag(list.get(position));
	      viewHolder.downButton.setTag(list.get(position));
	      
	      //view.setTag(viewHolder);
	    } else {
	      view = convertView;
	      ((ViewHolder) view.getTag()).upCount.setTag(list.get(position));
	      ((ViewHolder) view.getTag()).upButton.setTag(list.get(position));
	      ((ViewHolder) view.getTag()).downCount.setTag(list.get(position));
	      ((ViewHolder) view.getTag()).downButton.setTag(list.get(position));
	      
	    }
	   // ViewHolder holder = (ViewHolder) view.getTag();
	   // holder.text.setText(list.get(position).getName());
	    
	    ViewHolder holder = (ViewHolder) view.getTag();
	    holder.upCount.setText(list.get(position).getUps());
	    holder.downCount.setText(list.get(position).getDowns());
	    holder.commentCount.setText(list.get(position).getComments());
	    holder.name.setText(list.get(position).getName());
	    
	    return view;
	  }
}
