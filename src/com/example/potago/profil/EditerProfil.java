package com.example.potago.profil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.potago.Constantes;
import com.example.potago.JsonReadTask;
import com.example.potago.R;
import com.example.potago.entite.Utilisateur;
import com.example.potago.utils.Utils;

public class EditerProfil extends Activity {

	private Utilisateur utilisateurModif = null;
	private String selectedPath1;
	private String selectedPath2;
	private static final int SELECT_FILE1 = 15;
	private static final int SELECT_FILE2 = 16;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editer_profil);
		chargerUtilisateur(getSharedPreferences(Constantes.NOM_PREFERENCE, MODE_PRIVATE).getString(Constantes.LOGIN, null));
	}

	private void chargerUtilisateur(String mail) {
		final Map<String, String> mapArgument = new HashMap<String, String>();
		mapArgument.put("mail", mail);
		new JsonReadTask(Constantes.RECUPERATION_INFO_PROFIL, mapArgument) {

			@Override
			public void recuperationDonnee(String result) {
				final Utilisateur utilisateur = new Utilisateur();
				JSONObject response;
				try {
					response = new JSONObject(result);
					response = (JSONObject) response.getJSONArray("Resultat").get(0);

					utilisateur.setDescription(response.getString("description"));
					utilisateur.setIdUtilisateur(response.getInt("ID_utilisateur"));
					utilisateur.setMail(response.getString("Mail"));
					utilisateur.setNom(response.getString("Nom"));
					utilisateur.setPrenom(response.getString("Prenom"));
					utilisateur.setLatitude(response.getDouble("latitude"));
					utilisateur.setLongitude(response.getDouble("longitude"));
					utilisateurModif = utilisateur;
					initValeurTextField();
					initHandlerBouton();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}

	private void initHandlerBouton() {
		Button boutonPhotoProfil = (Button) findViewById(R.id.boutonChargerPhotoProfil);
		Button boutonPhotoGalerie = (Button) findViewById(R.id.boutonChargerImageGalery);
		Button boutonDelete = (Button) findViewById(R.id.boutonSupprimerPhotoGalery);
		Button boutonValider = (Button) findViewById(R.id.boutonValiderModification);
		boutonPhotoProfil.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO on charge la page d'upload avec un intent pour la photo profil
			}
		});

		boutonPhotoGalerie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openGallery(SELECT_FILE1);
			}
		});

		boutonDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO on charge la page d'upload avec un intent pour la photo profil
			}
		});

		boutonValider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				validerSaisie(utilisateurModif);
			}
		});

	}

	private void initValeurTextField() {
		EditText textNom = (EditText) findViewById(R.id.editionNom);
		EditText textPrenom = (EditText) findViewById(R.id.editionPrenom);
		EditText textAdresse = (EditText) findViewById(R.id.editionAdresse);
		EditText textDescr = (EditText) findViewById(R.id.editionDescription);

		textNom.setText(utilisateurModif.getNom());
		textPrenom.setText(utilisateurModif.getPrenom());
		textDescr.setText(utilisateurModif.getDescription());
		Geocoder gc = new Geocoder(this, Locale.getDefault());
		List<Address> addresses;
		try {
			addresses = gc.getFromLocation(utilisateurModif.getLatitude(), utilisateurModif.getLongitude(), 1);

			String adresseStr = "";
			if (addresses != null && addresses.size() > 0) {
				for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++) {
					adresseStr += addresses.get(0).getAddressLine(i) + " ";
				}
				textAdresse.setText(adresseStr);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void validerSaisie(Utilisateur utilisateur) {
		EditText textNom = (EditText) findViewById(R.id.editionNom);
		EditText textPrenom = (EditText) findViewById(R.id.editionPrenom);
		EditText textAdresse = (EditText) findViewById(R.id.editionAdresse);
		EditText textDescr = (EditText) findViewById(R.id.editionDescription);

		Map<String, String> parametres = new HashMap<String, String>();
		parametres.put("Nom", textNom.getText().toString());
		parametres.put("Prenom", textPrenom.getText().toString());
		parametres.put("Description", textDescr.getText().toString());
		parametres.put("Mail", getSharedPreferences(Constantes.NOM_PREFERENCE, MODE_PRIVATE).getString(Constantes.LOGIN, ""));
		validerAdresse(textAdresse.getText().toString(), parametres);

		new JsonReadTask(Constantes.MODIFICATION_PROFIL, parametres) {

			@Override
			public void recuperationDonnee(String result) {
				// si tout est ok on s'en va !
				startActivity(new Intent(EditerProfil.this, ProfilActivity.class));
				finish();
			}
		};
	}

	/**
	 * Cette methode permet de rechercher la latitude et longitude en fonction d'une adresse texte saisie
	 * 
	 * @param adresse
	 * @param param
	 */
	private void validerAdresse(String adresse, Map<String, String> param) {
		String searchPattern = adresse;
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// I use last known location, but here we can get real location
		Location lastKnownLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		List<Address> addresses = null;
		try {
			// trying to get all possible addresses by search pattern
			addresses = (new Geocoder(this)).getFromLocationName(searchPattern, Integer.MAX_VALUE);
		} catch (IOException e) {
		}
		if (addresses == null || lastKnownLocation == null) {
			Utils.setErreur((TextView) findViewById(R.id.editionAdresse), getString(R.string.error_field_address_incorrect));
		}

		Address closest = null;
		float closestDistance = Float.MAX_VALUE;
		// look for address, closest to our location
		for (Address adr : addresses) {
			if (closest == null) {
				closest = adr;
			} else {
				float[] result = new float[1];
				Location.distanceBetween(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), adr.getLatitude(), adr.getLongitude(), result);
				float distance = result[0];
				if (distance < closestDistance) {
					closest = adr;
					closestDistance = distance;
				}
			}
		}
		if (closest != null) {
			param.put("Latitude", closest.getLatitude() + "");
			param.put("Longitude", closest.getLongitude() + "");
		} else {
			Utils.setErreur((TextView) findViewById(R.id.editionAdresse), getString(R.string.error_field_address_incorrect));
		}

	}

	public void openGallery(final int req_code) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select file to upload "), req_code);
	}

	@Override
	public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

		if (resultCode == RESULT_OK) {
			final Uri selectedImageUri = data.getData();
			if (requestCode == SELECT_FILE1) {
				selectedPath1 = getPath(selectedImageUri);
				System.out.println("selectedPath1 : " + selectedPath1);
			}

			if (requestCode == SELECT_FILE2) {
				selectedPath2 = getPath(selectedImageUri);
				System.out.println("selectedPath2 : " + selectedPath2);
			}
			System.out.println("Selected File paths : " + selectedPath1 + "," + selectedPath2);

		}

	}

	public String getPath(final Uri uri) {

		final String[] projection = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		final Cursor cursor = managedQuery(uri, projection, null, null, null);
		final int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}
