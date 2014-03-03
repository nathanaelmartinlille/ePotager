package com.example.potago.infos;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;

import com.example.potago.R;
import com.example.potago.utils.Utils;

public class CGU_Infos extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cgu__infos);

		Utils.initialisationBoutonNavigation(this);

		WebView maWebView = (WebView) this.findViewById(R.id.maWebView);

		Bundle extras = getIntent().getExtras();
		String quelleInfo = extras.getString("QUELWEB");

		if (quelleInfo.equals("CGU")) {
			maWebView.loadUrl("file:///android_asset/CGU.html");
		} else {
			maWebView.loadUrl("file:///android_asset/Infos.html");
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cgu__infos, menu);
		return true;
	}

}
