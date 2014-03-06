package com.example.potago.profil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import com.example.potago.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ProfilActivity extends Activity {

	private ViewPager viewPager;
	DisplayImageOptions options;
	String[] imageUrls = null;
	ImageLoader imageLoader = null;

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
		initialiserContenuProfilEtFilCommentaire();
		// TODO init galerie seulement si c'est un jardinier.
		initialiserGalerieImage();
		initialiserHandlerBoutonEnteteProfil();
	}

	/**
	 * Cette fonction permet d'initialiser les handler pour acceder au dial et au systeme de localisation
	 */
	private void initialiserHandlerBoutonEnteteProfil() {
		final ImageButton imageDial = (ImageButton) this.findViewById(R.id.imageDial);
		final ImageButton imageLoc = (ImageButton) this.findViewById(R.id.imageLoc);

		imageDial.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				startActivity(new Intent(ProfilActivity.this, Tchat.class));
			}
		});

		imageLoc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				final Intent geoIntent = new Intent();
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

				// On affecte la vue personnalisé que l'on a crée à notre AlertDialog
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
					}
				});

				// On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
				adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int which) {
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
		final ProfilActivity profil = this;
		imageLoader = ImageLoader.getInstance();

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

	private void initialiserContenuProfilEtFilCommentaire() {
		final ProfilActivity profil = this;
		new JsonReadTask(Constantes.RECUPERATION_COMMENTAIRE) {

			@Override
			public void recuperationDonnee(final String reponse) {
				final TextView contenuCommentaire1 = (TextView) profil.findViewById(R.id.contenuCommentaire1);
				final TextView contenuCommentaire2 = (TextView) profil.findViewById(R.id.contenuCommentaire2);
				final TextView contenuDescription = (TextView) profil.findViewById(R.id.contenuDescription);
				final TextView auteurCommentaire = (TextView) profil.findViewById(R.id.nomAuteurCommentaire1);
				final TextView auteurCommentaire2 = (TextView) profil.findViewById(R.id.nomAuteurCommentaire2);
				try {

					final JSONObject response = new JSONObject(reponse);
					final JSONArray commentaires = response.getJSONArray("commentaires");
					contenuDescription.setText(response.getString("description"));
					switch (commentaires.length()) {
					case 0:
						break;
					case 1:
						contenuCommentaire1.setText(commentaires.getJSONObject(0).getString("contenuCommentaire"));
						auteurCommentaire.setText(commentaires.getJSONObject(0).getString("auteurCommentaire"));
						break;
					case 2:
						contenuCommentaire1.setText(commentaires.getJSONObject(0).getString("contenuCommentaire"));
						auteurCommentaire.setText(commentaires.getJSONObject(0).getString("auteurCommentaire"));
						contenuCommentaire2.setText(commentaires.getJSONObject(1).getString("contenuCommentaire"));
						auteurCommentaire2.setText(commentaires.getJSONObject(1).getString("auteurCommentaire"));
						break;
					default:
						contenuCommentaire1.setText(commentaires.getJSONObject(0).getString("contenuCommentaire"));
						auteurCommentaire.setText(commentaires.getJSONObject(0).getString("auteurCommentaire"));
						contenuCommentaire2.setText(commentaires.getJSONObject(1).getString("contenuCommentaire"));
						auteurCommentaire2.setText(commentaires.getJSONObject(1).getString("auteurCommentaire"));
						if (commentaires.length() > 2) {
							// si on a plus de 2 commentaires alors on affiche "voir plus de commentaire"
							final Button boutonMoreCommentaire = (Button) profil.findViewById(R.id.boutonPlusCommentaire);
							boutonMoreCommentaire.setVisibility(View.VISIBLE);
							boutonMoreCommentaire.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(final View v) {
									profil.startActivity(new Intent(profil, AffichageCommentaireActivity.class));

								}
							});
						}
					}

				} catch (final JSONException e) {

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