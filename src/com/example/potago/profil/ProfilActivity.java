package com.example.potago.profil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.devsmart.android.ui.HorizontalListView;
import com.example.potago.Constantes;
import com.example.potago.JsonReadTask;
import com.example.potago.R;
import com.example.potago.Tchat;
import com.example.potago.entite.Commentaire;
import com.example.potago.entite.Utilisateur;
import com.example.potago.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ProfilActivity extends Activity {

	DisplayImageOptions options;
	String[] imageUrls = null;
	ImageLoader imageLoader = null;
	Utilisateur utilisateurAffiche = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		Utils.initialisationBoutonNavigation(this);
		setContentView(R.layout.activity_voir_profil);
		imageLoader = ImageLoader.getInstance();

		// requete recuperer les informations de l'utilisateur
		final Intent intent = getIntent();
		// si on ne demande pas un profil expressement dans les parametres on recherche le profil courant de l'utilisateur
		if (intent.getStringExtra("mail") == null) {
			final SharedPreferences reference = getSharedPreferences(Constantes.NOM_PREFERENCE, Context.MODE_PRIVATE);
			final String mail = reference.getString(Constantes.LOGIN, null);
			if (mail != null) {
				chargerUtilisateur(mail);
			}
		} else {
			chargerUtilisateur(intent.getStringExtra("mail"));
		}

	}

	/**
	 * Cette methode va rechercher l'utilisateur mis en parametre
	 */
	private void chargerUtilisateur(final String adresseMail) {
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
					utilisateur.setMail(response.getString("Mail"));
					utilisateur.setNom(response.getString("Nom"));
					utilisateur.setPrenom(response.getString("Prenom"));
					utilisateur.setLatitude(response.getDouble("latitude"));
					utilisateur.setLongitude(response.getDouble("longitude"));
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
					utilisateurAffiche = utilisateur;
					creerProfil(utilisateur);
				} catch (final JSONException e) {
					e.printStackTrace();
				}
			}
		};

	}

	private void creerProfil(final Utilisateur utilisateur) {
		initialiserEnteteProfil(utilisateur);
		initialiserRatingBar(this, utilisateur);
		initialiserContenuProfilEtFilCommentaire(utilisateur);
		// TODO init galerie seulement si c'est un jardinier.
		initialiserGalerieImage(utilisateur);
		initialiserHandlerBoutonEnteteProfil(utilisateur);
	}

	/**
	 * Cette fonction permet d'initialiser les handler pour acceder au dial et au systeme de localisation
	 * 
	 * @param utilisateur
	 */
	private void initialiserHandlerBoutonEnteteProfil(final Utilisateur utilisateur) {
		ImageButton imageDial = (ImageButton) this.findViewById(R.id.imageDial);
		ImageButton imageLoc = (ImageButton) this.findViewById(R.id.imageLoc);
		Button boutonModifier = (Button) this.findViewById(R.id.boutonModifier);

		imageDial.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				startActivity(new Intent(ProfilActivity.this, Tchat.class));
			}
		});

		imageLoc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				Intent geoIntent = new Intent();
				geoIntent.putExtra("mail", utilisateur.getMail());
				startActivity(geoIntent);
			}
		});

		boutonModifier.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ProfilActivity.this, EditerProfil.class));
			}
		});

	}

	/**
	 * Initialise la partie ratingbar en mettant des evenements dessus
	 * 
	 * @param profilActivity
	 * @param utilisateur
	 */
	private void initialiserRatingBar(final ProfilActivity profilActivity, final Utilisateur utilisateur) {
		final RatingBar rating = (RatingBar) this.findViewById(R.id.ratingBar);
		rating.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(final RatingBar ratingBar, final float rating, final boolean fromUser) {
				final LayoutInflater factory = LayoutInflater.from(profilActivity);
				final View alertDialogView = factory.inflate(R.layout.ajout_commentaire_dialog, null);
				final RatingBar ratingConfirm = (RatingBar) alertDialogView.findViewById(R.id.ratingBarConfirm);
				ratingConfirm.setProgress(ratingBar.getProgress());
				// Création de l'AlertDialog

				final AlertDialog.Builder adb = new AlertDialog.Builder(profilActivity);
				if (fromUser) {
					adb.setView(alertDialogView);
					// On donne un titre à l'AlertDialog
					adb.setTitle("Ajouter un commentaire");

					// On modifie l'icône de l'AlertDialog pour le fun ;)
					adb.setIcon(android.R.drawable.ic_dialog_alert);

					// On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
					adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog, final int which) {

							// Lorsque l'on cliquera sur le bouton "OK", on récupère l'EditText correspondant à notre vue personnalisée (cad à alertDialogView)
							final EditText et = (EditText) alertDialogView.findViewById(R.id.saisieLibelleCommentaire);

							// On affiche dans un Toast le texte contenu dans l'EditText de notre AlertDialog
							Toast.makeText(ProfilActivity.this, et.getText(), Toast.LENGTH_SHORT).show();
							Map<String, String> map = new HashMap<String, String>();
							map.put("contenu", et.getText().toString());
							map.put("note", ratingConfirm.getRating() + "");
							map.put("mail_auteur", getSharedPreferences(Constantes.NOM_PREFERENCE, MODE_PRIVATE).getString(Constantes.LOGIN, ""));
							map.put("mail_utilisateur", utilisateurAffiche.getMail());
							new JsonReadTask(Constantes.INSERTION_COMMENTAIRE, map) {

								@Override
								public void recuperationDonnee(final String result) {
									// TODO recuperation id commenrtaire auteur etc
									try {
										final JSONObject response = (JSONObject) new JSONObject(result).get("Resultat");
										Commentaire com = new Commentaire();
										Utilisateur auteur = new Utilisateur();
										auteur.setIdUtilisateur(response.getInt("ID_auteur"));
										auteur.setNom(response.getString("Nom_auteur"));
										auteur.setPrenom(response.getString("Prenom_auteur"));

										com.setAuteur(auteur);
										com.setContenu(response.getString("Contenu"));
										com.setNote(response.getDouble("Note"));
										com.setUtilisateur(utilisateurAffiche);
										utilisateurAffiche.getCommentaires().add(com);
										reinitRatingProfil(utilisateurAffiche);
									} catch (final JSONException e) {
										e.printStackTrace();
									}
								}
							};

							reinitRatingProfil(utilisateur);
						}
					});

					// On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
					adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog, final int which) {
							dialog.cancel();
							reinitRatingProfil(utilisateur);
						}
					});
					adb.show();
				}
			}
		});

	}

	private void reinitRatingProfil(final Utilisateur utilisateur) {
		if (utilisateur.getCommentaires().size() > 0) {
			// dans le cas où on a au moins un commentaire on calcule la note globale
			Double total = 0D;
			Integer totalVote = 0;
			for (final Commentaire comm : utilisateur.getCommentaires()) {
				if (comm.getNote() != null) {
					total += comm.getNote();
					totalVote++;
				}
			}
			final Double noteFinale = total / totalVote;
			final RatingBar ratingGlobal = (RatingBar) findViewById(R.id.ratingBar);
			ratingGlobal.setRating(noteFinale.floatValue());
		}
	}

	/**
	 * Cette methode permet d'initialiser le nom le prenom et l'image de la personne.
	 * 
	 * @param utilisateur
	 */
	private void initialiserEnteteProfil(final Utilisateur utilisateur) {
		final ImageButton imageProfil = (ImageButton) this.findViewById(R.id.imageProfil);
		imageProfil.setBackgroundResource(R.drawable.image_profil_vide);

		final TextView prenomProfil = (TextView) this.findViewById(R.id.prenomTexte);
		prenomProfil.setText(utilisateur.getPrenom());

		final TextView nomProfil = (TextView) this.findViewById(R.id.nomTexte);
		nomProfil.setText(utilisateur.getNom());

	}

	/**
	 * Cette methode permet de recuperer une galerie d'image qui correspond à un certain profil.
	 * 
	 * @param utilisateur
	 */
	private void initialiserGalerieImage(final Utilisateur utilisateur) {
		final ProfilActivity profil = this;
		// TODO RECUPERATION LISTE LIEN VERS IMAGE GALERY
		imageUrls = Constants.IMAGES;
		// FIN TODONE

		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub).showImageForEmptyUri(R.drawable.ic_empty).showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true).displayer(new RoundedBitmapDisplayer(20)).build();

		final HorizontalListView listview = (HorizontalListView) findViewById(R.id.listview);
		listview.setAdapter(new ItemAdapter());

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> arg0, final View arg1, final int position, final long arg3) {
				final Intent intent = new Intent(profil, ImagePagerActivity.class);
				intent.putExtra("imageurlpostion", imageUrls);
				intent.putExtra("imagepostion", position);
				startActivity(intent);
			}
		});

	}

	private void initialiserContenuProfilEtFilCommentaire(final Utilisateur utilisateur) {
		cacherLignesCommentaire();
		if (utilisateur.getCommentaires().size() == 1) {
			chargerLigne1Commentaire(utilisateur);
		} else if (utilisateur.getCommentaires().size() == 2) {
			chargerLigne1Commentaire(utilisateur);
		} else {
			// on a beaucoup plus que deux commentaires on affiche donc un bouton +
			chargerLigne1Commentaire(utilisateur);
			chargerLigne2Commentaire(utilisateur);
			chargerBoutonPlusDeCommentaire(utilisateur);
		}

		reinitRatingProfil(utilisateur);
	}

	/**
	 * Cette methode permet de cacher l'ensemble des lignes de commentaire
	 */
	private void cacherLignesCommentaire() {
		this.findViewById(R.id.contenuCommentaire1).setVisibility(View.GONE);
		this.findViewById(R.id.nomAuteurCommentaire1).setVisibility(View.GONE);
		this.findViewById(R.id.imageAuteur1).setVisibility(View.GONE);

		this.findViewById(R.id.contenuCommentaire2).setVisibility(View.GONE);
		this.findViewById(R.id.nomAuteurCommentaire2).setVisibility(View.GONE);
		this.findViewById(R.id.imageAuteur2).setVisibility(View.GONE);

		this.findViewById(R.id.boutonPlusCommentaire).setVisibility(View.GONE);

	}

	/**
	 * Methode qui permet de charger la premiere ligne de commentaire
	 * 
	 * @param utilisateur
	 */
	private void chargerLigne1Commentaire(final Utilisateur utilisateur) {
		final TextView contenuCommentaire1 = (TextView) this.findViewById(R.id.contenuCommentaire1);
		final TextView auteurCommentaire = (TextView) this.findViewById(R.id.nomAuteurCommentaire1);
		final ImageView imageAuteur1 = (ImageView) this.findViewById(R.id.imageAuteur1);
		contenuCommentaire1.setVisibility(View.VISIBLE);
		auteurCommentaire.setVisibility(View.VISIBLE);
		imageAuteur1.setVisibility(View.VISIBLE);
		// on charge les données
		contenuCommentaire1.setText(utilisateur.getCommentaires().get(0).getContenu());
		auteurCommentaire.setText(utilisateur.getCommentaires().get(0).getAuteur().getNom() + " " + utilisateur.getCommentaires().get(0).getAuteur().getPrenom());
		imageLoader.displayImage(Constantes.REPERTOIRE_STOCKAGE_IMAGE + utilisateur.getCommentaires().get(0).getAuteur().getIdUtilisateur(), imageAuteur1);
	}

	/**
	 * Methode qui permet de charger la premiere ligne de commentaire
	 * 
	 * @param utilisateur
	 */
	private void chargerLigne2Commentaire(final Utilisateur utilisateur) {
		final TextView contenuCommentaire2 = (TextView) this.findViewById(R.id.contenuCommentaire2);
		final TextView auteurCommentaire2 = (TextView) this.findViewById(R.id.nomAuteurCommentaire2);
		final ImageView imageAuteur2 = (ImageView) this.findViewById(R.id.imageAuteur2);
		contenuCommentaire2.setVisibility(View.VISIBLE);
		auteurCommentaire2.setVisibility(View.VISIBLE);
		imageAuteur2.setVisibility(View.VISIBLE);
		// on charge les données
		contenuCommentaire2.setText(utilisateur.getCommentaires().get(1).getContenu());
		auteurCommentaire2.setText(utilisateur.getCommentaires().get(1).getAuteur().getNom() + " " + utilisateur.getCommentaires().get(1).getAuteur().getPrenom());

		imageLoader.displayImage(Constantes.REPERTOIRE_STOCKAGE_IMAGE + utilisateur.getCommentaires().get(1).getAuteur().getIdUtilisateur(), imageAuteur2);

	}

	private void chargerBoutonPlusDeCommentaire(final Utilisateur utilisateur) {
		final Button boutonPlusCom = (Button) this.findViewById(R.id.boutonPlusCommentaire);
		boutonPlusCom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				final Intent intent = new Intent(ProfilActivity.this, AffichageCommentaireActivity.class);
				intent.putExtra("idUtilisateur", utilisateur.getIdUtilisateur());
				startActivity(intent);
			}
		});
	}

	class ItemAdapter extends BaseAdapter {

		private final ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

		private class ViewHolder {
			public ImageView image;
		}

		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object getItem(final int position) {
			return position;
		}

		@Override
		public long getItemId(final int position) {
			return position;
		}

		@Override
		public View getView(final int position, final View convertView, final ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item_list_image, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) view.findViewById(R.id.image);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			imageLoader.displayImage(imageUrls[position], holder.image, options, animateFirstListener);

			return view;
		}

	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(final String imageUri, final View view, final Bitmap loadedImage) {
			if (loadedImage != null) {
				final ImageView imageView = (ImageView) view;
				final boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}