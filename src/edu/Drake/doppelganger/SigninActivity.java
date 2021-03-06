package edu.Drake.doppelganger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class SigninActivity extends Activity {
	
	ImageButton signup;
	ImageButton login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);	
		
	}

	public void signUp(View v) {
		Intent signup = new Intent(SigninActivity.this, FacebookActivity.class);
		startActivity(signup);
	}
	
	public void logIn(View v) {
		Intent login = new Intent(SigninActivity.this, MainActivity.class);
		startActivity(login);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_signin, menu);
		return true;
	}
	
	public void signin(View v) {
		Intent intent = new Intent(v.getContext(), FacebookActivity.class);
		startActivity(intent);
	}
}
