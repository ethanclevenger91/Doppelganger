package edu.Drake.doppelganger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class FacebookActivity extends Activity {
	
	ImageButton start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook);	
		
		start = (ImageButton) findViewById(R.id.start);
		start.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				Intent start = new Intent(FacebookActivity.this, MainActivity.class);
				startActivity(start);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_facebook, menu);
		return true;
	}
	
	public void signin(View v) {
		Intent intent = new Intent(v.getContext(), MainActivity.class);
		startActivity(intent);
	}
}
