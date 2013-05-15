package edu.Drake.doppelganger;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NotificationsArrayAdapter extends ArrayAdapter<FeedsModel> {
	
	private final List<FeedsModel> list;
	private final Activity context;

	public NotificationsArrayAdapter(Fragment context, List<FeedsModel> list) {
		super(context.getActivity(), R.layout.feed_list, list);
		this.context = context.getActivity();
		this.list = list;
	}

	static class ViewHolder {
		protected TextView notification;
		protected boolean selected;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		FeedsModel model = getItem(position);

		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.notifications_item, parent,false);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.notification = (TextView) view.findViewById(R.id.notifications_text);
			
			
			viewHolder.notification.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					FeedsModel element = (FeedsModel) viewHolder.notification.getTag();

					Intent intent = new Intent(v.getContext(), MoreInfo.class);

					Bundle b = new Bundle();
					b.putString("ups", (String) element.getUps());
					b.putString("downs", (String) element.getDowns());
					b.putString("desc", element.getDesc());
					b.putString("name", element.getName());
					b.putString("image", element.getImageId());
					b.putString("id", String.valueOf(element.getId()));
					b.putStringArrayList("commentList", (ArrayList<String>) element.getCommentList());
					b.putString("fid", element.getFID());
					b.putString("timestamp", String.valueOf(element.getTimestamp()));
					b.putString("celeb", element.getCeleb());
					b.putString("tag", element.getTag());
					b.putString("read", element.getRead());

					intent.putExtras(b);
					((Activity) v.getContext()).startActivityForResult(intent,1);
				}

			});

			view.setTag(viewHolder);
			viewHolder.notification.setTag(list.get(position));

		} else {
			view = convertView;
			((ViewHolder) view.getTag()).notification.setTag(list.get(position));
		}
		// ViewHolder holder = (ViewHolder) view.getTag();
		// holder.text.setText(list.get(position).getName());

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.notification.setText(String.format(view.getResources()
                .getString(R.string.tagged_in_post),
                list.get(position).getName()));
		
		Log.v("Notifications", list.get(position).getRead());
		if(list.get(position).getRead().equals("true"))
		{
			holder.selected = true;
			//holder.notification.setBackgroundColor(Color.BLUE);
			view.setBackgroundColor(Color.TRANSPARENT);
		}
		else
		{
			holder.selected = false;
			//holder.notification.setBackgroundColor(Color.RED);
			view.setBackgroundColor(Color.LTGRAY);
		}

		return view;
	}
	
	public void moreInfo()
	{
		Log.v("Notifications", "Got this again");
	}
	
	
}
