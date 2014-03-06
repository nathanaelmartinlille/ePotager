package com.example.potago;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class InscriptionActivity extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inscription, menu);
		final TextView loginEmail = (TextView) this.findViewById(R.id.email_inscription);
		final TextView password = (TextView) this.findViewById(R.id.password_inscription);
		final TextView password2 = (TextView) this.findViewById(R.id.password_confirm_inscription);

		final RadioGroup radioGroupChoixUtilisateur = (RadioGroup) this.findViewById(R.id.radio_type_utilisateur);
		final RadioButton choixUtilisateur;

		final int selectedId = radioGroupChoixUtilisateur.getCheckedRadioButtonId();

		// find the radiobutton by returned id
		choixUtilisateur = (RadioButton) findViewById(selectedId);

		loginEmail.setError(null);
		password.setError(null);

		// Store values at the time of the login attempt.
		final String mEmail = loginEmail.getText().toString();
		final String mPassword = password.getText().toString();
		final String mPassword2 = password2.getText().toString();

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
		if (TextUtils.isEmpty(mPassword2)) {
			password2.setError(getString(R.string.error_field_required));
			focusView = password2;
			cancel = true;
		} else if (mPassword2.length() < 4) {
			password2.setError(getString(R.string.error_invalid_password));
			focusView = password2;
			cancel = true;
		}

		if (!TextUtils.equals(mPassword, mPassword2)) {
			password2.setError(getString(R.string.error_not_the_same_password));
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
