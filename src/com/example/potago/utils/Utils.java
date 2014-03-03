package com.example.potago.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.ImageButton;

import com.example.potago.R;

/**
 * Cette classe permet de regrouper tous les utilitaires communs à l'application.
 * 
 * @author nath
 * 
 */
public class Utils {

	/**
	 * Permet d'afficher une boite de dialogue OUI NON
	 * 
	 * @param _context
	 *            le context de l'activity
	 * @param title
	 *            le titre de la boite de dialogue
	 * @param message
	 *            le message à prompt à lu
	 * @param onYesListener
	 *            un listener déjà initialisé lorsque l'utilisateur va cliquer sur OUI
	 * @param onNoListener
	 *            un listener déjà initialisé lorsque l'utilisateur va cliquer sur NON
	 */
	public static void showYesNoPrompt(Context _context, String title, String message, OnClickListener onYesListener, OnClickListener onNoListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(_context);
		builder.setTitle(title);
		builder.setIcon(android.R.drawable.ic_dialog_info); // lame icon
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setPositiveButton("Yes", onYesListener);
		builder.setNegativeButton("No", onNoListener);
		builder.show();
	}

	/**
	 * Cette methode permet d'initialiser les boutons de navigation en fonction de l'activity courante
	 * 
	 * @param activite
	 *            l'activité qui a besoin d'une initialisation des boutons
	 */
	public static void initialisationBoutonNavigation(final Activity activite) {
		ImageButton back = (ImageButton) activite.findViewById(R.id.boutonBack);
		if (back != null) {
			back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					activite.finish();
				}
			});
		}
		ImageButton home = (ImageButton) activite.findViewById(R.id.boutonHome);
		if (home != null) {
			home.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					activite.finish();
				}
			});
		}
	}

}
