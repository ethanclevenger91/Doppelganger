package edu.Drake.doppelganger;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CelebrityActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_celebrity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_celebrity, menu);
		return true;
	}

}
