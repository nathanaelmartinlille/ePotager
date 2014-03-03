package com.example.potago;

import com.example.potago.utils.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class Tchat extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tchat);
		Utils.initialisationBoutonNavigation(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tchat, menu);
		return true;
	}

}
