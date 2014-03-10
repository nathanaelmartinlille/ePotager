package com.example.potago;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.potago.entite.Commentaire;
import com.example.potago.entite.Message;
import com.example.potago.entite.Utilisateur;
import com.example.potago.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Tchat extends Activity {
	
	final List<Message> listeMessages = new ArrayList<Message>();
	private List<Integer> listeIdReceveur = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tchat);
		Utils.initialisationBoutonNavigation(this);
		
		
		final SharedPreferences reference = getSharedPreferences(Constantes.NOM_PREFERENCE, Context.MODE_PRIVATE);
		final String mail = reference.getString(Constantes.LOGIN, null);
		if (mail != null) {
			chargerConversations(mail);
		}
	}
	
	private void chargerConversations(final String adresseMail) {
		final Map<String, String> mapArgument = new HashMap<String, String>();
		mapArgument.put("mail", adresseMail);
		new JsonReadTask(Utils.convertirURLAvecParam(Constantes.RECUPERATION_TCHAT, mapArgument)) {

			@Override
			public void recuperationDonnee(final String jsonResult) {
				try {
					final Utilisateur utilisateurMnt = new Utilisateur();
					JSONObject response;
					JSONObject messagesJSON;
					System.out.println("jsonResult : "+jsonResult);
					response = new JSONObject(jsonResult);
					messagesJSON = (JSONObject) response.getJSONArray("Resultat").get(1);
					System.out.println("messagesJSON "+messagesJSON);
					final JSONArray messagesJSONArray = (JSONArray) response.getJSONArray("Resultat");
					
					if(messagesJSONArray.length() >0){
					for (int i = 0; i < messagesJSONArray.length(); i++) {
						System.out.println("messagesJSONArray.getJSONObject(i) : "+messagesJSONArray.getJSONObject(i));
						Utilisateur envoyeur = new Utilisateur();
						envoyeur.setIdUtilisateur(messagesJSONArray.getJSONObject(i).getInt("Envoyeur"));
						Utilisateur receveur = new Utilisateur();
						envoyeur.setIdUtilisateur(messagesJSONArray.getJSONObject(i).getInt("Receveur"));
						envoyeur.setNom(messagesJSONArray.getJSONObject(i).getString("Nom"));
						envoyeur.setPrenom(messagesJSONArray.getJSONObject(i).getString("Prenom"));
						final Message message = new Message(envoyeur, receveur, messagesJSONArray.getJSONObject(i).getString("message"));
						
						if(listeIdReceveur.contains(receveur.getIdUtilisateur()))
						{
							System.out.println("c'est le meme");
						}
						else{
							listeIdReceveur.add(receveur.getIdUtilisateur());
							listeMessages.add(message);
						}
						
					}
					
					definirListe();
					}
					
				} catch (final JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public void definirListe()
	{
		ListView listeTchat = (ListView)findViewById(R.id.listeTchat);
		

	    List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
	    
	   
	    for(int i = 0 ; i < listeMessages.size(); i++) {
	    	 HashMap<String, String> element = new HashMap<String, String>();
	    	Message m = listeMessages.get(i);
	      element.put("text1", m.getReceveur().getNom() + m.getReceveur().getPrenom());
	      System.out.println("message : "+m.getContenu());
	      element.put("text2",m.getContenu());
	      liste.add(element);
	    }
	    
	    ListAdapter adapter = new SimpleAdapter(this,  
	      liste, 
	      android.R.layout.simple_list_item_2,
	      new String[] {"text1", "text2"}, 
	      new int[] {android.R.id.text1, android.R.id.text2 });
	    
	    listeTchat.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tchat, menu);
		return true;
	}

}
