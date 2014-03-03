package com.example.potago;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.potago.entite.Jardinier;
import com.example.potago.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class Geolocalisation extends FragmentActivity {

	// On garde les éléments de la géolocalisation
	TextView rayon;
	static SeekBar distance;
	static CheckBox fruits;
	static CheckBox legumes;
	static CheckBox dispo;

	static LatLng userLocation;
	// Liste des jardiniers
	static ArrayList<Jardinier> listeJardiniers;
	static HashMap<Marker, Jardinier> listeMarkers;

	private static Context context;

	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	private static GoogleMap map;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO faire en sorte qu'on puisse afficher directement un profil en particulier sur la carte.
		// Cette option est utilisé par la page de profil. Acceder à un profil sur une carte.
		setContentView(R.layout.activity_geoloc);
		Utils.initialisationBoutonNavigation(this);

		listeMarkers = new HashMap<Marker, Jardinier>();
		listeJardiniers = new ArrayList<Jardinier>();

		// On prend le contexte de l'activity
		context = this.getBaseContext();

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		map = mapFragment.getMap();
		map.setMyLocationEnabled(true);

		TextView titre = (TextView) this.findViewById(R.id.titre);
		titre.setText("Géolocalisation");

		map.setMyLocationEnabled(true);
		map.setOnMapLoadedCallback(new OnMapLoadedCallback() {

			@Override
			public void onMapLoaded() {
				// TODO Auto-generated method stub
				userLocation = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
				map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
			}
		});
		map.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {

			@Override
			public void onMyLocationChange(Location arg0) {
				userLocation = new LatLng(arg0.getLatitude(), arg0.getLongitude());
				// map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
				// afficherJardinier();
			}

		});

		// On récupère les éléments
		rayon = (TextView) this.findViewById(R.id.rayon);
		distance = (SeekBar) this.findViewById(R.id.distance);
		fruits = (CheckBox) this.findViewById(R.id.checkBoxFruits);
		legumes = (CheckBox) this.findViewById(R.id.checkBoxLegumes);
		dispo = (CheckBox) this.findViewById(R.id.checkBoxDispo);

		/**
		 * On met la valeur du slide au milieu
		 */
		distance.setProgress(50);
		rayon.setText("50km");

		/**
		 * Listener pour la distance de recherche
		 */
		distance.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			/**
			 * Quand on a fini de changer la distance du slide, on recharge les données.
			 */
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				rayon.setText(seekBar.getProgress() + "km");
				afficherJardinier();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			/**
			 * Quand on change la valeur de la distance, on ne fait que changer le texte. On ne recharge pas tout de suite les données, sinon il y aurait trop
			 * de requêtes.
			 */
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				rayon.setText(seekBar.getProgress() + "km");
			}
		});

		/**
		 * Les fruits, les légumes et la disponibilité ont tous le même listener
		 */
		OnCheckedChangeListener listener = new MonClickListener();
		fruits.setOnCheckedChangeListener(listener);
		legumes.setOnCheckedChangeListener(listener);
		dispo.setOnCheckedChangeListener(listener);

		accessWebService();

	}

	// On accède au web service
	public void accessWebService() {
		System.out.println("accessWebService");
		new JsonReadTask(Constantes.RECUPERATION_GEOLOC) {

			@Override
			public void recuperationDonnee(String result) {

				System.out.println("Je fais la mise à jour des données");
				listeJardiniers.clear();
				listeMarkers.clear();
				System.out.println("liste : " + listeJardiniers);
				try {
					// FIXME:Des fois ça bug ici.....
					JSONObject jsonResponse = new JSONObject(result);
					JSONArray jsonMainNode = jsonResponse.optJSONArray("Jardiniers");

					for (int i = 0; i < jsonMainNode.length(); i++) {
						JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
						System.out.println(jsonChildNode.optBoolean("Legumes"));
						Jardinier jardinier = new Jardinier(jsonChildNode.optString("Nom"), jsonChildNode.optString("Prenom"),
								jsonChildNode.optString("Description"), jsonChildNode.optString("Latitude"), jsonChildNode.optString("Longitude"),
								jsonChildNode.optBoolean("Fruits"), jsonChildNode.optBoolean("Legumes"), jsonChildNode.optBoolean("Disponible"));
						listeJardiniers.add(jardinier);
					}
				} catch (JSONException e) {
					System.out.println("je catch une exception : " + e);
				}
				afficherJardinier();
			}

		};
	}

	public static void afficherJardinier() {
		// J'enlève tous les anciens markers
		map.clear();
		for (Jardinier j : listeJardiniers) {
			boolean aLeDroitDexister = true;
			LatLng jLatLng = new LatLng(j.getLatitude(), j.getLongitude());
			float[] results = new float[5];

			// Je récupère la position de l'user et je calcule la distance entre l'user et le jardinier
			if (userLocation != null) {
				Location.distanceBetween(jLatLng.latitude, jLatLng.longitude, userLocation.latitude, userLocation.longitude, results);
			}

			// On regarde si ils sont plus loins que la distance
			// Attention ! la distance est en mètres !
			System.out.println("distance : " + results[0] / 1000 + " d2 : " + distance.getProgress());
			if (results[0] / 1000 < distance.getProgress()) {
				aLeDroitDexister = true;
				if ((dispo.isChecked() && j.isEstDispo()) || (!dispo.isChecked() && !j.isEstDispo())) {
					aLeDroitDexister = true;
					if ((fruits.isChecked() && j.isVendFruits()) || (!fruits.isChecked() && !j.isVendFruits())) {
						aLeDroitDexister = true;
						if ((legumes.isChecked() && j.isVendLegumes()) || (!legumes.isChecked() && !j.isVendLegumes())) {
							aLeDroitDexister = true;
						} else {
							aLeDroitDexister = false;
						}
					} else {
						aLeDroitDexister = false;
					}
				} else {
					aLeDroitDexister = false;
				}
			} else {
				aLeDroitDexister = false;
			}

			if (aLeDroitDexister) {
				System.out.println("j'ai le droit d'exister + " + j);
				Marker jardinier = map.addMarker(new MarkerOptions().position(jLatLng).title(j.getNom()).snippet(j.getPrenom())
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.repere)));
				listeMarkers.put(jardinier, j);
			}
		}

		/**
		 * On définit la vue quand on clique sur le jardinier
		 */
		map.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoWindow(Marker arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public View getInfoContents(Marker arg0) {
				LayoutInflater li = LayoutInflater.from(context);
				View v = li.inflate(R.layout.marker_jardinier, null);

				Jardinier jar = listeMarkers.get(arg0);
				System.out.println("jardinier : " + jar);

				TextView nomPrenom = (TextView) v.findViewById(R.id.nom_prenom_marker);
				TextView description = (TextView) v.findViewById(R.id.description_marker);

				nomPrenom.setText(jar.getPrenom() + " " + jar.getNom());
				description.setText("" + jar.getDescription());

				// Quand on appuie sur le bouton mail
				ImageButton mail = (ImageButton) v.findViewById(R.id.mail_marker);
				mail.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO faire cette fonction
						// On va sur la vue de chat avec l'id du jardinier
						/*
						 * Geolocalisation g = new Geolocalisation(); Intent myIntent = new Intent(g, Tchat.class); g.startActivity(myIntent);
						 */
					}
				});

				// Returning the view containing InfoWindow contents
				return v;
			}
		});

		/**
		 * Quand on clique sur un jardinier sur la map
		 */
		map.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO faire cette fonction
				System.out.println("j'ai cliqué sur le marker du jardinier");
				// On va sur le profil du jardinier
				/*
				 * Geolocalisation g = new Geolocalisation(); Intent myIntent = new Intent(g, ProfilActivity.class); g.startActivity(myIntent);
				 */
				return false;
			}
		});

		/**
		 * Quand on clique sur les informations d'un jardinier, on va sur son profil.
		 */
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker arg0) {
				// TODO Auto-generated method stub

				System.out.println("j'ai cliqué sur les infos du jardinier");
			}
		});
	}
}

class MonClickListener implements OnCheckedChangeListener {

	public MonClickListener() {

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Geolocalisation.afficherJardinier();
	}

}
