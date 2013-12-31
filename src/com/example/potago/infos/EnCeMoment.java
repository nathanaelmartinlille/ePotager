package com.example.potago.infos;

import java.util.Calendar;

import com.example.potago.R;
import com.example.potago.ePotager;
import com.example.potago.R.layout;
import com.example.potago.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class EnCeMoment extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_en_ce_moment);
		
		ImageButton back = (ImageButton)this.findViewById(R.id.boutonBack);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		ImageButton home = (ImageButton)this.findViewById(R.id.boutonHome);
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EnCeMoment.this, ePotager.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		// On récupère le mois présent pour savoir quels légumes et fruits planter
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		 Toast.makeText(getApplicationContext(), "quel mois "+month , Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.en_ce_moment, menu);
		return true;
	}

}
