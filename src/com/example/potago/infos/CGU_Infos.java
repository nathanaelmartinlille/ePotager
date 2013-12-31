package com.example.potago.infos;

import com.example.potago.R;
import com.example.potago.ePotager;
import com.example.potago.R.layout;
import com.example.potago.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

public class CGU_Infos extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cgu__infos);
		
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
				Intent intent = new Intent(CGU_Infos.this, ePotager.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		WebView maWebView = (WebView)this.findViewById(R.id.maWebView);
		
		Bundle extras = getIntent().getExtras();
	    String quelleInfo = extras.getString("QUELWEB");
	   
	    if(quelleInfo.equals("CGU"))
	    	maWebView.loadUrl("file:///android_asset/CGU.html");
	    else
	    	maWebView.loadUrl("file:///android_asset/Infos.html");
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cgu__infos, menu);
		return true;
	}

}
