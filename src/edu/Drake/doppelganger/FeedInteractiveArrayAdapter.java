package edu.Drake.doppelganger;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
	      
	      viewHolder.photoButton.setOnClickListener(new OnClickListener() {
	    	 @Override
	    	 public void onClick(View v) {

	    		 FeedsModel element = (FeedsModel) viewHolder.downCount
	                      .getTag();
	    		 
	    	            Intent intent = new Intent(v.getContext(), MoreInfo.class);
	    	            
	    	            Bundle b = new Bundle();
	    	            b.putString("ups", (String) viewHolder.upCount.getText());
	    	            b.putString("downs", (String) viewHolder.downCount.getText());
	    	            b.putString("desc", element.getDesc());
	    	            b.putString("image", element.getImageId());
	    	            b.putStringArrayList("commentList", (ArrayList<String>) element.getCommentList());
	    	            
	    	            intent.putExtras(b);
	    	            Log.v("activity context", v.getContext().toString());
	    	    		((Activity) v.getContext()).startActivityForResult(intent,1);
	    		
	    	 }
	    	 
	      });
	      
	      viewHolder.commentsButton.setOnClickListener(new OnClickListener() {
		    	 @Override
		    	 public void onClick(View v) {

		    	            Intent intent = new Intent(v.getContext(), MoreInfo.class);
		    	    		v.getContext().startActivity(intent);
		    		
		    	 }
		      });
	      
	      view.setTag(viewHolder);
	      viewHolder.upCount.setTag(list.get(position));
	      viewHolder.upButton.setTag(list.get(position));
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
	      
	    }
	   // ViewHolder holder = (ViewHolder) view.getTag();
	   // holder.text.setText(list.get(position).getName());
	    
	    ViewHolder holder = (ViewHolder) view.getTag();
	    holder.upCount.setText(list.get(position).getUps());
	    holder.downCount.setText(list.get(position).getDowns());
	    holder.commentCount.setText(list.get(position).getComments());
	    holder.name.setText(list.get(position).getName());
	    
	    String uri = "raw/" + model.getImageId();
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        holder.photoButton.setImageResource(imageResource);
	    
	    return view;
	  }
	  
	  /*
	  
	  @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        // TODO Auto-generated method stub
			
	        super.onActivityResult(requestCode, resultCode, data);
	        
	     // ScrollView scroll = (ScrollView) findViewById(R.id.scroll_view1);
	        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_view1);
	        
	        if(data.getExtras()!=null){
	        	
	        	TextView tv1 = new TextView(this);
	        	tv1.setText(data.getStringExtra("post"));
	        	tv1.setTextSize(20);
	        	tv1.setBackgroundResource(R.drawable.back);
	        	tv1.setPadding(20,20,20,20);
	        	
	        	LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	            llp.setMargins(0, 10, 0, 0); // llp.setMargins(left, top, right, bottom);
	        	tv1.setLayoutParams(llp);
	        	
	        	commentList.add(tv1.getText().toString());
	        	
	        	layout.addView(tv1,commentList.size()-1);
	 
	        }
	  
	  */
	  
	  
	  
	  
}
