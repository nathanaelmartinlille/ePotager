package com.example.potago;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MessageTexte extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_texte);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message_texte, menu);
		return true;
	}

}
