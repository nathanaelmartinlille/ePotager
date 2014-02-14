package com.example.potago;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class InscriptionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inscription, menu);
		TextView loginEmail = (TextView) this.findViewById(R.id.email_inscription);
		TextView password = (TextView) this.findViewById(R.id.password_inscription);
		TextView password2 = (TextView) this.findViewById(R.id.password_confirm_inscription);
		
		loginEmail.setError(null);
		password.setError(null);

		// Store values at the time of the login attempt.
		String mEmail = loginEmail.getText().toString();
		String mPassword = password.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			password.setError(getString(R.string.error_field_required));
			focusView = password;
			cancel = true;
		} else if (mPassword.length() < 4) {
			password.setError(getString(R.string.error_invalid_password));
			focusView = password;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			loginEmail.setError(getString(R.string.error_field_required));
			focusView = loginEmail;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			loginEmail.setError(getString(R.string.error_invalid_email));
			focusView = loginEmail;
			cancel = true;
		}
		
		
		
		return true;
	}

}
