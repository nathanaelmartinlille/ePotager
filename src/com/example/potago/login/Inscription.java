package com.example.potago.login;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.potago.Constantes;
import com.example.potago.JsonReadTask;
import com.example.potago.R;
import com.example.potago.profil.ProfilActivity;

public class Inscription extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);
		Button valider = (Button) findViewById(R.id.valider_bouton);
		valider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				validerInscription();
			}
		});
	}

	public void validerInscription() {
		// Inflate the menu; this adds items to the action bar if it is present.
		final TextView loginEmail = (TextView) this.findViewById(R.id.email_inscription);
		final TextView password = (TextView) this.findViewById(R.id.password_inscription);
		final TextView password2 = (TextView) this.findViewById(R.id.password_confirm_inscription);

		final RadioGroup radioGroupChoixUtilisateur = (RadioGroup) this.findViewById(R.id.radio_type_utilisateur);

		final int selectedId = radioGroupChoixUtilisateur.getCheckedRadioButtonId();

		// find the radiobutton by returned id
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
		if (cancel) {
			return;
		}
		// si tout est ok alors on va enregistrer le nouvel utilisateur
		// on creer une MAP de clé valeur afin de passer en parametre les données
		Map<String, String> mappingInscription = new HashMap<String, String>();
		mappingInscription.put("mail", loginEmail.getText().toString());
		mappingInscription.put("mdp", password.getText().toString());
		mappingInscription.put("est_jardinier", (selectedId == R.id.radioProducteur ? "true" : "false"));
		new JsonReadTask(Constantes.INSERTION_COMPTE_UTILISATEUR, mappingInscription) {

			@Override
			public void recuperationDonnee(String result) {
				if (result != null && !"".equals(result)) {
					loginEmail.setError(getString(R.string.error_invalid_email));
				} else {
					startActivity(new Intent(Inscription.this, ProfilActivity.class));
				}
			}
		};
	}
}
