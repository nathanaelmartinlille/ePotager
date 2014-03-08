package com.example.potago.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
	 * @param _context le context de l'activity
	 * @param title le titre de la boite de dialogue
	 * @param message le message à prompt à lu
	 * @param onYesListener un listener déjà initialisé lorsque l'utilisateur va cliquer sur OUI
	 * @param onNoListener un listener déjà initialisé lorsque l'utilisateur va cliquer sur NON
	 */
	public static void showYesNoPrompt(final Context _context, final String title, final String message, final OnClickListener onYesListener, final OnClickListener onNoListener) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(_context);
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
	 * @param activite l'activité qui a besoin d'une initialisation des boutons
	 */
	public static void initialisationBoutonNavigation(final Activity activite) {
		final ImageButton back = (ImageButton) activite.findViewById(R.id.boutonBack);
		if (back != null) {
			back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(final View v) {
					activite.finish();
				}
			});
		}
		final ImageButton home = (ImageButton) activite.findViewById(R.id.boutonHome);
		if (home != null) {
			home.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(final View v) {
					activite.finish();
				}
			});
		}
	}

	public static String convertirURLAvecParam(String url, final Map<String, String> params) {
		String enPlus = "?";
		final Set<String> cles = params.keySet();
		final Iterator<String> it = cles.iterator();
		while (it.hasNext()) {
			final String cle = it.next();
			final String valeur = params.get(cle);
			enPlus += cle + "=" + valeur + "&";
		}
		enPlus.substring(0, enPlus.length() - 2);
		url += enPlus;

		System.out.println("on a creer l'URL : " + url);
		return url;
	}

	public static StringBuilder inputStreamToString(final InputStream is) {
		String rLine = "";
		final StringBuilder answer = new StringBuilder();
		final BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		try {
			while ((rLine = rd.readLine()) != null) {
				answer.append(rLine);
			}
		}

		catch (final IOException e) {
			e.printStackTrace();
		}
		return answer;
	}

	/**
	 * Cette methode permet d'afficher un champ en erreur et d'afficher une infobulle au dessus pour spécifier l'erreur.
	 * 
	 * @param champ le champ dont l'erreur provient
	 * @param erreur l'erreur
	 * @return le textview
	 */
	public static View setErreur(final TextView champ, final String erreur) {
		final int ecolor = Color.BLACK; // whatever color you want
		final ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
		final SpannableStringBuilder ssbuilder = new SpannableStringBuilder(erreur);
		ssbuilder.setSpan(fgcspan, 0, erreur.length(), 0);
		champ.setError(ssbuilder);
		return champ;
	}
}
