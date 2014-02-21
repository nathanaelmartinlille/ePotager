package com.example.potago.infos;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.potago.JsonReadTask;
import com.example.potago.R;
import com.example.potago.ePotager;
import com.example.potago.R.layout;
import com.example.potago.R.menu;

import Entite.Jardinier;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

public class EnCeMoment extends Activity {

	private final String url = "http://lauraleclercq.com/ePotager/enCeMoment.php";
	
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
		
		final WebView wv = (WebView)this.findViewById(R.id.webViewMoment);
		// On récupère le mois présent pour savoir quels légumes et fruits planter
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		month++;
		System.out.println("Quel jour ? "+c.get(Calendar.DAY_OF_MONTH));
		 Toast.makeText(getApplicationContext(), "quel mois "+month , Toast.LENGTH_SHORT).show();
		 
		 Map<String, String> params = new HashMap<String, String>();
		 params.put("numeroMois", ""+month);
		 JsonReadTask task = new JsonReadTask(url, params) {

				@Override
				public void recuperationDonnee(String result) {

					System.out.println("result : "+result);
					String summary = "";
					

					try {
						JSONObject jsonResponse = new JSONObject(result);
						JSONArray jsonMainNode = jsonResponse.optJSONArray("Resultat");

						for (int i = 0; i < jsonMainNode.length(); i++) {
							JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
							System.out.println(jsonChildNode.optBoolean("Legumes"));
							summary += jsonChildNode.optString("Description");
						}
					} catch (JSONException e) {
						System.out.println("je catch une exception : " + e);
					}
					
					System.out.println("summary : "+summary);
					wv.loadData(summary, "text/html", "utf-8");
					wv.reload();
				}

			
		 };
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.en_ce_moment, menu);
		return true;
	}

}
