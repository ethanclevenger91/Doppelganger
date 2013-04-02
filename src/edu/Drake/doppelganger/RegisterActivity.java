package edu.Drake.doppelganger;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

public class RegisterActivity extends Activity {

	ImageButton start;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		start = (ImageButton) findViewById(R.id.start);
		start.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				Intent start = new Intent(RegisterActivity.this, MainActivity.class);
				startActivity(start);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_register, menu);
		return true;
	}

}
