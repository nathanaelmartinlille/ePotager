package com.example.potago;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class ePotager extends Activity {

	private SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accueil);

		final ImageButton geolocalisation = (ImageButton) this.findViewById(R.id.geoloc_accueil);
		geolocalisation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				 Intent myIntent = new Intent(ePotager.this, Geolocalisation.class);
				 ePotager.this.startActivity(myIntent);
			}
		});

		final ImageButton profil = (ImageButton) this.findViewById(R.id.profil_accueil);
		profil.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				testerConnexion(ProfilActivity.class);
			}
		});

		final ImageButton chat = (ImageButton) this.findViewById(R.id.chat_accueil);
		chat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				testerConnexion(Tchat.class);
			}
		});

		final ImageButton infos = (ImageButton) this.findViewById(R.id.infos_accueil);
		infos.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				final Intent myIntent = new Intent(ePotager.this, InfosActivity.class);
				ePotager.this.startActivity(myIntent);

			}
		});
	}
	
	/**Cette methode permet de tester si l'utilisateur est connecté ou non. Si non connecté alors on bacule vers l'activity de login. Sinon on va sur la rubrique.
	 * @param activitySuivante
	 */
	public void testerConnexion(Class<? extends Activity> activitySuivante){
		sharedPreferences = getSharedPreferences(Constantes.NOM_PREFERENCE, Context.MODE_PRIVATE);
		if(sharedPreferences.contains(Constantes.LOGIN) && sharedPreferences.contains(Constantes.PASSWORD)) {
			// si on a un login dans la base et que le mot de passe est renseigné alors on autorise
			ePotager.this.startActivity(new Intent(ePotager.this, activitySuivante));
		}else{
			ePotager.this.startActivity(new Intent(ePotager.this, LoginActivity.class ));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accueil, menu);
		return true;
	}

}
