package com.example.potago.profil;

import com.example.potago.R;
import com.example.potago.R.layout;
import com.example.potago.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AffichageCommentaireActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_affichage_commentaire);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.affichage_commentaire, menu);
		return true;
	}

}
