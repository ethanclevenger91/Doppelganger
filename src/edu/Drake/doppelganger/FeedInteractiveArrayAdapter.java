package edu.Drake.doppelganger;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class FeedInteractiveArrayAdapter extends ArrayAdapter<FeedsModel> {

	  private final List<FeedsModel> list;
	  private final Activity context;

	  public FeedInteractiveArrayAdapter(Fragment context, List<FeedsModel> list) {
	    super(context.getActivity(), R.layout.activity_notifications, list);
	    this.context = context.getActivity();
	    this.list = list;
	  }

	  /*
	  static class ViewHolder {
	    //protected TextView text;
	  }
	  */

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    if (convertView == null) {
	      LayoutInflater inflator = context.getLayoutInflater();
	      view = inflator.inflate(R.layout.activity_feed, null);
	      //final ViewHolder viewHolder = new ViewHolder();
	     // viewHolder.text = (TextView) view.findViewById(R.id.label);
	      //view.setTag(viewHolder);
	    } else {
	      view = convertView;
	    }
	   // ViewHolder holder = (ViewHolder) view.getTag();
	   // holder.text.setText(list.get(position).getName());
	    return view;
	  }
}
