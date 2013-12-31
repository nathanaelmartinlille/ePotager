package com.example.potago;

import java.util.ArrayList;

import Entite.Jardinier;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class Geolocalisation extends FragmentActivity{

	// On garde les éléments de la géolocalisation
	TextView rayon;
	SeekBar distance;
	CheckBox fruits;
	CheckBox legumes;
	CheckBox dispo;

	// Liste des jardiniers
	ArrayList<Jardinier> listeJardiniers;


	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	private GoogleMap map;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geoloc);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		map = mapFragment.getMap();
		map.setMyLocationEnabled(true);

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
				finish();
			}
		});

		TextView titre = (TextView)this.findViewById(R.id.titre);
		titre.setText("Géolocalisation");

		donnesPourTester();
		afficherJardinier();
		
		//FIXME:la location est toujours à null
		Location location = map.getMyLocation();
		if (location != null) {
			LatLng myLocation = new LatLng(location.getLatitude(),
					location.getLongitude());
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
					15));
		}


		// On récupère les éléments
		rayon = (TextView)this.findViewById(R.id.rayon);
		distance = (SeekBar)this.findViewById(R.id.distance);
		fruits = (CheckBox)this.findViewById(R.id.checkBoxFruits);
		legumes = (CheckBox)this.findViewById(R.id.checkBoxLegumes);
		dispo = (CheckBox)this.findViewById(R.id.checkBoxDispo);

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
				recupererDonnees();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			/**
			 * Quand on change la valeur de la distance, on ne fait que changer le texte.
			 * On ne recharge pas tout de suite les données, sinon il y aurait trop de requêtes.
			 */
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
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

	}

	/**
	 * Méthode qui fait des requêtes PHP sur la base de données
	 * avec les paramètres du slide et des checkbox
	 */
	public static void recupererDonnees()
	{
		System.out.println("Je fais la mise à jour des données");
	}

	public void afficherJardinier()
	{
		for (Jardinier j : listeJardiniers) {
			LatLng jLatLng = new LatLng(j.getLatitude(), j.getLongitude());
			Marker jardinier = map.addMarker(new MarkerOptions().position(jLatLng)
					.title(j.getNom())
					.snippet(j.getPrenom())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.ic_launcher)));
		}
	}

	public void donnesPourTester()
	{
		listeJardiniers = new ArrayList<Jardinier>();
		Jardinier j1 = new Jardinier("Leclercq", "Laura", 50.639221,3.155543,true, true,true);
		Jardinier j2 = new Jardinier("Martin", "Nathanaël", 50.610927,3.149669,false,true,true);
		listeJardiniers.add(j1);
		listeJardiniers.add(j2);
	}

}

class MonClickListener implements OnCheckedChangeListener {

	public MonClickListener() {

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Geolocalisation.recupererDonnees();
	}

}
