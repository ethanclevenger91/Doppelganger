package edu.Drake.doppelganger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
		protected ImageButton commentsButton;
		protected ImageButton photoButton;
		protected boolean selected;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		FeedsModel model = getItem(position);

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
			viewHolder.photoButton = (ImageButton) view.findViewById(R.id.image_button1);
			viewHolder.commentsButton = (ImageButton) view.findViewById(R.id.image_comment);

			viewHolder.upButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					FeedsModel element = (FeedsModel) viewHolder.upCount.getTag();
					if(!element.isSelected()) {
						element.incrementUp();
						viewHolder.upCount.setText(element.getUps());
						viewHolder.upButton.setImageResource(R.raw.voteupblue);
						element.setSelected(true);

						int ups = Integer.parseInt(element.getUps());
						int downs = Integer.parseInt(element.getDowns());
						int commentCount = Integer.parseInt(element.getComments());
						String name = element.getName();
						String desc = element.getDesc();
						int idInt = element.getId();
						String image = element.getImageId();
						List<String> commentList = element.getCommentList();
						String fid = element.getFID();
						long timestamp = element.getTimestamp();
						String celeb = element.getCeleb();
						String tag = element.getTag();

						FeedSQLiteHelper db = new FeedSQLiteHelper(v.getContext());

						FeedsModel newModel = new FeedsModel(idInt, desc, name, celeb, ups, downs, commentCount, commentList, image, fid, timestamp, tag);
						db.updateContact(newModel);
					}
				}
			});

			viewHolder.downButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					FeedsModel element = (FeedsModel) viewHolder.downCount.getTag();
					if(!element.isSelected()) {
						element.incrementDown();
						viewHolder.downCount.setText(element.getDowns());
						element.setSelected(true);
						viewHolder.downButton.setImageResource(R.raw.votedownblue);

						int ups = Integer.parseInt(element.getUps());
						int downs = Integer.parseInt(element.getDowns());
						int commentCount = Integer.parseInt(element.getComments());
						String name = element.getName();
						String desc = element.getDesc();
						int idInt = element.getId();
						String image = element.getImageId();
						List<String> commentList = element.getCommentList();
						String fid = element.getFID();
						long timestamp = element.getTimestamp();
						String celeb = element.getCeleb();
						String tag = element.getTag();

						FeedSQLiteHelper db = new FeedSQLiteHelper(v.getContext());

						FeedsModel newModel = new FeedsModel(idInt, desc, name, celeb, ups, downs, commentCount, commentList, image, fid, timestamp, tag);
						db.updateContact(newModel);

					}
				}
			});

			viewHolder.photoButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					FeedsModel element = (FeedsModel) viewHolder.downCount.getTag();

					Intent intent = new Intent(v.getContext(), MoreInfo.class);

					Bundle b = new Bundle();
					b.putString("ups", (String) viewHolder.upCount.getText());
					b.putString("downs", (String) viewHolder.downCount.getText());
					b.putString("desc", element.getDesc());
					b.putString("name", element.getName());
					b.putString("image", element.getImageId());
					b.putString("id", String.valueOf(element.getId()));
					b.putStringArrayList("commentList", (ArrayList<String>) element.getCommentList());
					b.putString("fid", element.getFID());
					b.putString("timestamp", String.valueOf(element.getTimestamp()));
					b.putString("celeb", element.getCeleb());
					b.putString("tag", element.getTag());

					intent.putExtras(b);
					((Activity) v.getContext()).startActivityForResult(intent,1);
				}

			});

			viewHolder.commentsButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					FeedsModel element = (FeedsModel) viewHolder.downCount.getTag();

					Intent intent = new Intent(v.getContext(), MoreInfo.class);

					Bundle b = new Bundle();
					b.putString("ups", (String) viewHolder.upCount.getText());
					b.putString("downs", (String) viewHolder.downCount.getText());
					b.putString("desc", element.getDesc());
					b.putString("image", element.getImageId());
					b.putString("id", String.valueOf(element.getId()));
					b.putStringArrayList("commentList", (ArrayList<String>) element.getCommentList());
					b.putString("fid", element.getFID());
					b.putString("timestamp", String.valueOf(element.getTimestamp()));
					b.putString("celeb", element.getCeleb());
					b.putString("tag", element.getTag());

					intent.putExtras(b);
					((Activity) v.getContext()).startActivityForResult(intent,1);

				}
			});


			view.setTag(viewHolder);
			viewHolder.upCount.setTag(list.get(position));
			viewHolder.upButton.setTag(list.get(position));
			viewHolder.commentCount.setTag(list.get(position));
			viewHolder.downCount.setTag(list.get(position));
			viewHolder.downButton.setTag(list.get(position));
			viewHolder.photoButton.setTag(list.get(position));

			//view.setTag(viewHolder);
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).upCount.setTag(list.get(position));
			((ViewHolder) view.getTag()).upButton.setTag(list.get(position));
			((ViewHolder) view.getTag()).downCount.setTag(list.get(position));
			((ViewHolder) view.getTag()).downButton.setTag(list.get(position));
			((ViewHolder) view.getTag()).photoButton.setTag(list.get(position));
			((ViewHolder) view.getTag()).commentCount.setTag(list.get(position));

		}
		// ViewHolder holder = (ViewHolder) view.getTag();
		// holder.text.setText(list.get(position).getName());

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.upCount.setText(list.get(position).getUps());
		holder.downCount.setText(list.get(position).getDowns());
		holder.commentCount.setText(list.get(position).getComments());
		holder.name.setText(String.format(view.getResources()
                .getString(R.string.user_posted),
                list.get(position).getName()));

		int newHeight = (int) (((Activity) view.getContext()).getWindowManager().getDefaultDisplay().getHeight() /2.5);
		int newWidth = ((Activity) view.getContext()).getWindowManager().getDefaultDisplay().getWidth();

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				newWidth, newHeight);

		params.setMargins(15, 0, 15, 0);
		params.addRule(RelativeLayout.BELOW, R.id.textView1);

		holder.photoButton.setLayoutParams(params);

		Bitmap bitmap = BitmapHelper.decodeFile(new File(model.getImageId()), newWidth, newHeight, false);
		holder.photoButton.setImageBitmap(bitmap);
		holder.photoButton.setBackgroundResource(R.drawable.background);


		return view;
	}
}
