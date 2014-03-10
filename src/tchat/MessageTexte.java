package tchat;

import com.example.potago.R;
import com.example.potago.R.layout;
import com.example.potago.R.menu;
import com.example.potago.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class MessageTexte extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_texte);
		Utils.initialisationBoutonNavigation(this);
		
		WebView webViewMessages = (WebView)findViewById(R.id.webViewMessages);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message_texte, menu);
		return true;
	}

}
