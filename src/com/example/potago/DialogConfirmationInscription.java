package com.example.potago;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class DialogConfirmationInscription extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog_confirmation_inscription);

		Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// on est dans le cas où aucun mail n'a été trouvé qui correspond à celui entré
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					startActivity(new Intent(DialogConfirmationInscription.this, InscriptionActivity.class));
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					startActivity(new Intent(DialogConfirmationInscription.this, InscriptionActivity.class));
					break;
				}
			}
		};

		alertDialogBuilder.setMessage("Votre email n'existe pas encore, voulez-vous vous inscrire plutot ?").setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dialog_confirmation_inscription, menu);
		return true;
	}

}
