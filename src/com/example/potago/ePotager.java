package com.example.potago;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ePotager extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accueil);
		
		ImageButton geolocalisation = (ImageButton)this.findViewById(R.id.geoloc_accueil);
		geolocalisation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(ePotager.this, Geolocalisation.class);
				ePotager.this.startActivity(myIntent);
			}
		});
		
		ImageButton profil = (ImageButton)this.findViewById(R.id.profil_accueil);
		profil.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ImageButton chat = (ImageButton)this.findViewById(R.id.chat_accueil);
		chat.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ImageButton infos = (ImageButton)this.findViewById(R.id.infos_accueil);
		infos.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(ePotager.this, InfosActivity.class);
				ePotager.this.startActivity(myIntent);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accueil, menu);
		return true;
	}

}
