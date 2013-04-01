package edu.Drake.doppelganger;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		//Pull email address from intent to auto-fill it if registering due to failed login
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String email = extras.getString("email");
		    EditText emailView = (EditText) findViewById(R.id.editText3);
		    emailView.setText(email);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_register, menu);
		return true;
	}

}
