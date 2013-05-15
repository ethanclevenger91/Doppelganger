package edu.Drake.doppelganger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class MoreInfo extends Activity {
	
	private String upCount;
	private String downCount;
	private String desc;
	private String image;
	private String name;
	private String celeb;
	private String fid;
	private String read;
	private String tag;
	private String time;
	private List<String> commentList;
	private String id;
	private ShareActionProvider mShareActionProvider;
	private Uri myUri;
	ImageButton upButton;
	ImageButton downButton;
	boolean upVoted;
	boolean downVoted;
	
	public void addComment(View v) {
		
		Intent intent = new Intent(getBaseContext(), AddComment.class);
 		startActivityForResult(intent,1);
 		overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_info);
		
		final ActionBar actionBar = getActionBar();
	    actionBar.setTitle("More Information");
		
		TextView poster = (TextView) findViewById(R.id.textView1);
		TextView descView = (TextView) findViewById(R.id.description);
		TextView upView = (TextView) findViewById(R.id.text_up);
		TextView downView = (TextView) findViewById(R.id.text_down);
		ImageButton photoButton = (ImageButton) findViewById(R.id.image_button1);
		upButton = (ImageButton) findViewById(R.id.image_up_vote);
		downButton = (ImageButton) findViewById(R.id.image_down_vote);
		TextView addComment = (TextView) findViewById(R.id.create_comment);
		
    	addComment.setTextSize(20);
    	addComment.setBackgroundResource(R.drawable.blueback);
    	addComment.setPadding(20,20,20,20);
    	
    	LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        llp1.setMargins(0, 10, 0, 0); // llp.setMargins(left, top, right, bottom);
    	addComment.setLayoutParams(llp1);
		
		addComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			
				Intent intent = new Intent(getBaseContext(), AddComment.class);
		 		startActivityForResult(intent,1);
		 		overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);
		 		
			}
		});
		
		upVoted = false;
		downVoted = false;
		
		upCount = getIntent().getStringExtra("ups");
		downCount = getIntent().getStringExtra("downs");
		desc = getIntent().getStringExtra("desc");
		image = getIntent().getStringExtra("image");
		id = getIntent().getStringExtra("id");
		name = getIntent().getStringExtra("name");
		commentList = getIntent().getStringArrayListExtra("commentList");
		fid= getIntent().getStringExtra("fid");
		time = getIntent().getStringExtra("timestamp");
		celeb= getIntent().getStringExtra("celeb");
		tag = getIntent().getStringExtra("tag");
		read = getIntent().getStringExtra("read");
		
		myUri = Uri.fromFile(new File(image));
		
		//String uri = "raw/" + image;
        //int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        //photoButton.setImageResource(imageResource);
        
        @SuppressWarnings("deprecation")
		int newHeight = (int) (getWindowManager().getDefaultDisplay().getHeight() /2.5);
		@SuppressWarnings("deprecation")
		int newWidth = getWindowManager().getDefaultDisplay().getWidth();
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
			    newWidth, newHeight);
		params.setMargins(15, 10, 35, 10);
		params.addRule(RelativeLayout.BELOW, R.id.textView1);
		photoButton.setLayoutParams(params);
		
		
        
		Bitmap bitmap = BitmapHelper.decodeFile(new File(image), newWidth, newWidth, false);
        photoButton.setImageBitmap(bitmap);
        photoButton.setBackgroundResource(R.drawable.background);
        
       // ScrollView scroll = (ScrollView) findViewById(R.id.scroll_view1);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_view1);
        
        //loop through array of comments and add to top of scroll view here
        if(commentList!=null){
        	
        	for(int i=0; i<commentList.size();i++){
        		TextView tv1 = new TextView(this);
        		tv1.setText(commentList.get(i));
        		tv1.setTextSize(20);
        		tv1.setBackgroundResource(R.drawable.back);
        		tv1.setPadding(20,20,20,20);
        	
        		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            	llp.setMargins(0, 10, 0, 0); // llp.setMargins(left, top, right, bottom);
            	tv1.setLayoutParams(llp);
        		layout.addView(tv1);
        	}
        }
		descView.setText(desc);
		upView.setText(upCount);
		downView.setText(downCount);
		poster.setText(String.format(getResources().getString(R.string.user_posted),name));
		
		
		
        //shows the back button
        getActionBar().setDisplayHomeAsUpEnabled(true);
	
	}
	
	@Override
	public void onBackPressed(){
		//disables the up button
		 getActionBar().setDisplayHomeAsUpEnabled(false);
		 

	    	Intent intent = getIntent();
	    	intent.putExtra("ups", upCount);
	    	intent.putExtra("downs", downCount);
	    	intent.putExtra("id", id);
	    	intent.putExtra("desc", desc);
	    	intent.putExtra("name", name);
	    	intent.putExtra("image",image);
	    	intent.putExtra("fid", fid);
	    	intent.putExtra("timestamp", time);
	    	intent.putExtra("celeb", celeb);
	    	intent.putExtra("tag", tag);
	    	intent.putExtra("read", read);
	    	
	    	
	    	if(commentList!=null) {
	    		Log.v("Moreinfo", "putting comments in");
	    		intent.putExtra("comment", String.valueOf(commentList.size()));
	    		Log.v("Moreinfo", String.valueOf(commentList.size()));
	    		intent.putStringArrayListExtra("commentList", (ArrayList<String>) commentList);
	    	}
	    	
	    	setResult(RESULT_OK,intent);
	    	finish();
		 
	}
	
	public void upVote(View v) {
		TextView upView = (TextView) findViewById(R.id.text_up);
		if(!upVoted && !downVoted)
		{
			upButton.setImageResource(R.raw.voteupblue);
			int ups = Integer.parseInt(upCount);
			ups++;
			upCount = String.valueOf(ups);
			upView.setText(upCount);
			upVoted = true;
		}
		else if(upVoted && !downVoted)
		{
			upButton.setImageResource(R.raw.voteup);
			int ups = Integer.parseInt(upCount);
			ups--;
			upCount = String.valueOf(ups);
			upView.setText(upCount);
			upVoted = false;
		}
		else if(!upVoted && downVoted)
		{
			TextView downView = (TextView) findViewById(R.id.text_down);
			downButton.setImageResource(R.raw.votedown);
			upButton.setImageResource(R.raw.voteupblue);
			int ups = Integer.parseInt(upCount);
			int downs = Integer.parseInt(downCount);
			ups++;
			downs--;
			upCount = String.valueOf(ups);
			downCount = String.valueOf(downs);
			upView.setText(upCount);
			downView.setText(downCount);
			downVoted = false;
			upVoted = true;
		}
	}
	
	public void downVote(View v) {
		TextView downView = (TextView) findViewById(R.id.text_down);
		if(!downVoted && !upVoted)
		{
			int downs = Integer.parseInt(downCount);
			downs++;
			downCount = String.valueOf(downs);
			downView.setText(downCount);
			downButton.setImageResource(R.raw.votedownblue);
			downVoted = true;
		}
		else if(downVoted && !upVoted)
		{
			int downs = Integer.parseInt(downCount);
			downs--;
			downCount = String.valueOf(downs);
			downView.setText(downCount);
			downButton.setImageResource(R.raw.votedown);
			downVoted = false;
		}
		else if(!downVoted && upVoted)
		{
			int downs = Integer.parseInt(downCount);
			int ups = Integer.parseInt(upCount);
			downs++;
			ups--;
			downCount = String.valueOf(downs);
			upCount = String.valueOf(ups);
			downView.setText(downCount);
			TextView upView = (TextView) findViewById(R.id.text_up);
			upView.setText(upCount);
			upVoted = false;
			downVoted = true;
			downButton.setImageResource(R.raw.votedownblue);
			upButton.setImageResource(R.raw.voteup);
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_more_info, menu);
		mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_share)
		        .getActionProvider();
		 Intent intent = new Intent(Intent.ACTION_SEND);
		 intent.setType("image/*");
		 intent.putExtra(Intent.EXTRA_STREAM, myUri);
		 intent.putExtra(Intent.EXTRA_TEXT, desc + " - Made with the #MirrorMe app");
		 mShareActionProvider.setShareIntent(intent);
		 return true;
	}
	
	
	@Override
	//this is used on the up button press
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	getActionBar().setDisplayHomeAsUpEnabled(false);
	        	onBackPressed();
	        	break;
	    }
	    //returns the item selected, in this case the up button
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
        super.onActivityResult(requestCode, resultCode, data);
        
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
        	if(commentList!=null){
        		commentList.add(tv1.getText().toString());
        		layout.addView(tv1,commentList.size()-1);
        	}
        	else{
        		List<String> newList = new ArrayList<String>();
        		newList.add(tv1.getText().toString());
        		layout.addView(tv1,newList.size()-1);
        		commentList = newList;
        	}
        }
    }
	
	
}