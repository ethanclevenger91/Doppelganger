package edu.Drake.doppelganger;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class FeedFragment extends Fragment implements OnClickListener{
	
	private static final String TAG = "More Info";
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.v(TAG, "button pressed");
		Intent intent = new Intent(v.getContext(), MoreInfo.class);
		startActivity(intent);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    
		View rootView = inflater.inflate(R.layout.activity_feed, container, false);
		ImageButton buttonimage = (ImageButton) rootView.findViewById(R.id.image_button1);
		
		((ImageButton)buttonimage).setOnClickListener(this);
		
		// Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_feed, container, false);
	}

	
}
