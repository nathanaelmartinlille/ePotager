package com.example.potago;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.potago.entite.Commentaire;
import com.example.potago.entite.Utilisateur;
import com.example.potago.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class Tchat extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tchat);
		Utils.initialisationBoutonNavigation(this);
		
		ListView listeTchat = (ListView)findViewById(R.id.listeTchat);
		
		final SharedPreferences reference = getSharedPreferences(Constantes.NOM_PREFERENCE, Context.MODE_PRIVATE);
		final String mail = reference.getString(Constantes.LOGIN, null);
		if (mail != null) {
			chargerConversations(mail);
		}
	}
	
	private void chargerConversations(final String adresseMail) {
		final Map<String, String> mapArgument = new HashMap<String, String>();
		mapArgument.put("mail", adresseMail);
		new JsonReadTask(Utils.convertirURLAvecParam(Constantes.RECUPERATION_INFO_PROFIL, mapArgument)) {

			@Override
			public void recuperationDonnee(final String jsonResult) {
				try {
					final Utilisateur utilisateur = new Utilisateur();
					final List<Commentaire> commentaires = new ArrayList<Commentaire>();
					JSONObject response;
					JSONObject commentairesJSON;
					response = new JSONObject(jsonResult);
					commentairesJSON = (JSONObject) response.getJSONArray("Resultat").get(1);
					response = (JSONObject) response.getJSONArray("Resultat").get(0);

					utilisateur.setDescription(response.getString("description"));
					utilisateur.setIdUtilisateur(response.getInt("ID_utilisateur"));
					final JSONArray commentairesJSONArray = commentairesJSON.opt("Commentaires") != null ? commentairesJSON.getJSONArray("Commentaires") : new JSONArray();

					for (int i = 0; i < commentairesJSONArray.length(); i++) {
						final Commentaire commentaire = new Commentaire();
						commentaire.setUtilisateur(utilisateur);
						final Utilisateur auteurCommentaire = new Utilisateur();
						auteurCommentaire.setNom(commentairesJSONArray.getJSONObject(i).getString("Nom_auteur"));
						auteurCommentaire.setPrenom(commentairesJSONArray.getJSONObject(i).getString("Prenom_auteur"));
						auteurCommentaire.setMail(commentairesJSONArray.getJSONObject(i).getString("Mail_auteur"));
						auteurCommentaire.setIdUtilisateur(commentairesJSONArray.getJSONObject(i).getInt("ID_auteur"));

						commentaire.setContenu(commentairesJSONArray.getJSONObject(i).getString("Contenu"));
						commentaire.setNote(commentairesJSONArray.getJSONObject(i).getDouble("Note"));
						commentaire.setAuteur(auteurCommentaire);
						commentaire.setUtilisateur(utilisateur);
						commentaires.add(commentaire);
					}
					utilisateur.setCommentaires(commentaires);
					
				} catch (final JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tchat, menu);
		return true;
	}

}
