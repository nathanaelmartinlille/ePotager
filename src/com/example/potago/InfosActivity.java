package com.example.potago;

import com.example.potago.infos.CGU_Infos;
import com.example.potago.infos.EnCeMoment;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class InfosActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infos);
		
		TextView titre = (TextView)this.findViewById(R.id.titre);
		titre.setText("Informations");
		
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
				finish();
			}
		});
		
		// Liste des informations
		final ListView listeInfos = (ListView)this.findViewById(R.id.listeInfos);
		String[] listeStrings = {"Quels sont les meilleurs fruits et légumes en ce moment ?","Conditions générales d'utilisation","Informations"};
		listeInfos.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listeStrings));
		listeInfos.setClickable(true);
		listeInfos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				// On regarde sur quelle ligne il a cliqué
				switch (arg2) {
				case 0:
					// En ce moment
					Intent myIntent = new Intent(InfosActivity.this, EnCeMoment.class);
					InfosActivity.this.startActivity(myIntent);
					break;
				case 1:
					// CGU
					Intent myIntentCGU = new Intent(InfosActivity.this, CGU_Infos.class);
					myIntentCGU.putExtra("QUELWEB", "CGU");
					InfosActivity.this.startActivity(myIntentCGU);
					break;
				case 2:
					Intent myIntentInfos = new Intent(InfosActivity.this, CGU_Infos.class);
					myIntentInfos.putExtra("QUELWEB", "INFOS");
					InfosActivity.this.startActivity(myIntentInfos);
					break;
				default:
					break;
				}
			}
		});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.infos, menu);
		return true;
	}

}
