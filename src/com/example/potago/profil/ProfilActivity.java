package com.example.potago.profil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.potago.Constantes;
import com.example.potago.JsonReadTask;
import com.example.potago.R;
import com.example.potago.Tchat;
import com.example.potago.utils.Utils;

public class ProfilActivity extends Activity {

	private ViewPager viewPager;
	private final int[] images = { R.drawable.dd1, R.drawable.dd2, R.drawable.dd3, R.drawable.dd4, R.drawable.dd5 };
	private View cell;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		Utils.initialisationBoutonNavigation(this);
		setContentView(R.layout.activity_voir_profil);
		creerProfil();
	}

	private void creerProfil() {
		initialiserEnteteProfil();
		initialiserRatingBar(this);
		initialiserFilCommentaire();
		initialiserGalerieImage();
		initialiserHandlerBoutonEnteteProfil();
	}

	/**
	 * Cette fonction permet d'initialiser les handler pour acceder au dial et au systeme de localisation
	 */
	private void initialiserHandlerBoutonEnteteProfil() {
		ImageButton imageDial = (ImageButton) this.findViewById(R.id.imageDial);
		ImageButton imageLoc = (ImageButton) this.findViewById(R.id.imageLoc);

		imageDial.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ProfilActivity.this, Tchat.class));
			}
		});

		imageLoc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent geoIntent = new Intent();
				geoIntent.putExtra("idProfil", recupererIdProfilCourant());
				startActivity(geoIntent);
			}

			private Integer recupererIdProfilCourant() {
				return null;
			}
		});

	}

	/**
	 * Initialise la partie ratingbar en mettant des evenements dessus
	 * 
	 * @param profilActivity
	 */
	private void initialiserRatingBar(final ProfilActivity profilActivity) {
		RatingBar rating = (RatingBar) this.findViewById(R.id.ratingBar);
		rating.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				LayoutInflater factory = LayoutInflater.from(profilActivity);
				final View alertDialogView = factory.inflate(R.layout.ajout_commentaire_dialog, null);
				RatingBar ratingConfirm = (RatingBar) alertDialogView.findViewById(R.id.ratingBarConfirm);
				ratingConfirm.setProgress(ratingBar.getProgress());
				// Création de l'AlertDialog
				AlertDialog.Builder adb = new AlertDialog.Builder(profilActivity);

				// On affecte la vue personnalisé que l'on a crée à notre AlertDialog
				adb.setView(alertDialogView);

				// On donne un titre à l'AlertDialog
				adb.setTitle("Ajouter un commentaire");

				// On modifie l'icône de l'AlertDialog pour le fun ;)
				adb.setIcon(android.R.drawable.ic_dialog_alert);

				// On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
				adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						// Lorsque l'on cliquera sur le bouton "OK", on récupère l'EditText correspondant à notre vue personnalisée (cad à alertDialogView)
						EditText et = (EditText) alertDialogView.findViewById(R.id.saisieLibelleCommentaire);

						// On affiche dans un Toast le texte contenu dans l'EditText de notre AlertDialog
						Toast.makeText(ProfilActivity.this, et.getText(), Toast.LENGTH_SHORT).show();
					}
				});

				// On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
				adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				adb.show();

			}
		});

	}

	/**
	 * Cette methode permet d'initialiser le nom le prenom et l'image de la personne.
	 */
	private void initialiserEnteteProfil() {
		final ImageButton imageProfil = (ImageButton) this.findViewById(R.id.imageProfil);
		imageProfil.setBackgroundResource(R.drawable.image_profil_vide);

		// final ImageButton imageDial = (ImageButton) this.findViewById(R.id.imageDial);
		// imageDial.setBackgroundResource(R.drawable.ic_tab_dial);
		//
		// final ImageButton imageLoc = (ImageButton) this.findViewById(R.id.imageLoc);
		// imageLoc.setBackgroundResource(R.drawable.ic_menu_loc);

		final TextView prenomProfil = (TextView) this.findViewById(R.id.prenomTexte);
		prenomProfil.setText("Nathanael");

		final TextView nomProfil = (TextView) this.findViewById(R.id.nomTexte);
		nomProfil.setText("Martin");

	}

	/**
	 * Cette methode permet de recuperer une galerie d'image qui correspond à un certain profil.
	 */
	private void initialiserGalerieImage() {
		LinearLayout mainLayout;
		viewPager = (ViewPager) findViewById(R.id.pager);

		mainLayout = (LinearLayout) findViewById(R.id._linearLayout);

		for (int i = 0; i < images.length; i++) {

			cell = getLayoutInflater().inflate(R.layout.image_scroll, null);

			final ImageView imageView = (ImageView) cell.findViewById(R.id._image);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {

					viewPager.setVisibility(View.VISIBLE);
					viewPager.setAdapter(new GalleryPagerAdapter(ProfilActivity.this, images));
					viewPager.setCurrentItem(v.getId());
					viewPager.bringToFront();
				}
			});

			imageView.setId(i);

			imageView.setImageResource(images[i]);

			mainLayout.addView(cell);
		}
	}

	private void initialiserFilCommentaire() {
		final TextView contenuDescription = (TextView) this.findViewById(R.id.contenuDescription);
		final TextView contenuCommentaire1 = (TextView) this.findViewById(R.id.contenuCommentaire1);
		final TextView contenuCommentaire2 = (TextView) this.findViewById(R.id.contenuCommentaire2);

		final TextView auteurCommentaire = (TextView) this.findViewById(R.id.nomAuteurCommentaire1);
		final TextView auteurCommentaire2 = (TextView) this.findViewById(R.id.nomAuteurCommentaire2);

		contenuDescription.setText("J'ai un potager de 60 m2 dans lequel j'adore planter mes carottes dans la terre");
		contenuCommentaire1.setText("j'ai adoré ses carottes, je pense que c'est sa spécialité hummm ! ");
		contenuCommentaire2.setText("Ce mec est un gros pervers, en fait c'est pas vraiment des carottes qu'il plante !");

		auteurCommentaire.setText("Matthieu");
		auteurCommentaire2.setText("Elodie");

		new JsonReadTask(Constantes.RECUPERATION_COMMENTAIRE) {

			@Override
			public void recuperationDonnee(String reponse) {
				try {
					JSONObject response = new JSONObject(reponse);
					JSONArray commentaires = response.getJSONArray("commentaires");

					for (int i = 0; i < commentaires.length(); i++) {
						Integer idProfil = commentaires.getJSONObject(i).getInt("idProfil");
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};

	}

	@Override
	public void onBackPressed() {

		if (viewPager != null && viewPager.isShown()) {

			viewPager.setVisibility(View.GONE);
		} else {

			super.onBackPressed();
		}
	}

	private class ImagePagerAdapter extends PagerAdapter {
		private final int[] mImages = new int[] { R.drawable.dd1, R.drawable.dd2, R.drawable.dd3, R.drawable.dd4, R.drawable.dd5 };

		@Override
		public int getCount() {
			return mImages.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((ImageView) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Context context = ProfilActivity.this;
			ImageView imageView = new ImageView(context);
			int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
			imageView.setPadding(padding, padding, padding, padding);
			imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			imageView.setImageResource(mImages[position]);
			((ViewPager) container).addView(imageView, 0);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((ImageView) object);
		}
	}
}