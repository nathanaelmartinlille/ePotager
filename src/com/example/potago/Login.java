package com.example.potago;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.potago.utils.Utils;

/**
 * Activity which displays a login screen to the user, offering registration as well.
 */
public class Login extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords. TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] { "toto@toto.com:toto" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	public Context context = this;

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Utils.initialisationBoutonNavigation(this);
		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(final TextView textView, final int id, final KeyEvent keyEvent) {
				if (id == R.id.login || id == EditorInfo.IME_NULL) {
					attemptLogin();
					return true;
				}
				return false;
			}
		});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View view) {
				attemptLogin();
			}
		});

		findViewById(R.id.bouton_enregistrement).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				// appel de l'activity enregistrement
				startActivity(new Intent(Login.this, InscriptionActivity.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form. If there are form errors (invalid email, missing fields, etc.), the errors are presented and no
	 * actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			final int ecolor = Color.BLACK; // whatever color you want
			final ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
			final SpannableStringBuilder ssbuilder = new SpannableStringBuilder(getString(R.string.error_invalid_password));
			ssbuilder.setSpan(fgcspan, 0, getString(R.string.error_invalid_password).length(), 0);
			mPasswordView.setError(ssbuilder);
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			final int ecolor = Color.BLACK; // whatever color you want
			final ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
			final SpannableStringBuilder ssbuilder = new SpannableStringBuilder(getString(R.string.error_field_required));
			ssbuilder.setSpan(fgcspan, 0, getString(R.string.error_field_required).length(), 0);
			mEmailView.setError(ssbuilder);
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			final int ecolor = Color.BLACK; // whatever color you want
			final ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
			final SpannableStringBuilder ssbuilder = new SpannableStringBuilder(getString(R.string.error_invalid_email));
			ssbuilder.setSpan(fgcspan, 0, getString(R.string.error_field_required).length(), 0);
			mEmailView.setError(ssbuilder);
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			final int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(final Animator animation) {
					mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
				}
			});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(final Animator animation) {
					mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
				}
			});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(final Void... params) {
			// TODO: APPEL WS POUR CHECKLOGIN(STRING, STRING)

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (final InterruptedException e) {
				return false;
			}

			for (final String credential : DUMMY_CREDENTIALS) {
				final String[] pieces = credential.split(":");
				if (pieces[0].equals(mEmail)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mPassword);
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(final Boolean success) {

			super.onPostExecute(success);

			mAuthTask = null;
			showProgress(false);
			if (success == null) {
				// on est dans le cas où aucun mail n'a été trouvé qui correspond à celui entré

				final Builder alertDialogBuilder = new AlertDialog.Builder(context);

				// on est dans le cas où aucun mail n'a été trouvé qui correspond à celui entré
				final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						switch (which) {
						case DialogInterface.BUTTON_POSITIVE:
							startActivity(new Intent(Login.this, InscriptionActivity.class));
							finish();
							break;

						case DialogInterface.BUTTON_NEGATIVE:
							dialog.dismiss();
							final int ecolor = Color.BLACK; // whatever color you want
							final String estring = "Tapez une adresse correcte"; // your error message
							final ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
							final SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
							ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
							mEmailView.setError(ssbuilder);
							break;
						}
					}
				};

				alertDialogBuilder.setMessage("Votre email n'existe pas encore, voulez-vous vous inscrire plutot ?").setPositiveButton("Yes", dialogClickListener)
						.setNegativeButton("No", dialogClickListener).show();

			} else if (success) {
				// le login et mot de passe correct.
				final SharedPreferences reference = getSharedPreferences(Constantes.NOM_PREFERENCE, Context.MODE_PRIVATE);
				final Editor edit = reference.edit();
				edit.putString(Constantes.LOGIN, mEmailView.getText().toString());
				edit.putString(Constantes.PASSWORD, mPasswordView.getText().toString());
				edit.commit();
				finish();
			} else {
				final int ecolor = Color.BLACK; // whatever color you want
				final String estring = "Tapez une adresse correcte"; // your error message
				final ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
				final SpannableStringBuilder ssbuilder = new SpannableStringBuilder(getString(R.string.error_incorrect_password));
				ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
				mPasswordView.setError(ssbuilder);
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
