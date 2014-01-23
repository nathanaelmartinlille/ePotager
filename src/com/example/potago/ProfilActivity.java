package com.example.potago;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfilActivity extends Activity {

	// mainLayout is the child of the HorizontalScrollView ...
	private LinearLayout mainLayout;

	// this is an array that holds the IDs of the drawables ...
	private int[] images = { R.drawable.dd1, R.drawable.dd2, R.drawable.dd3, R.drawable.dd4, R.drawable.dd5 };

	private View cell;

	private ViewPager viewPager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.activity_profil);
		creerProfil();
	}

	private void creerProfil() {
		initialiserEnteteProfil();
		initialiserGalerieImage();
		initialiserFilCommentaire();
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
		viewPager = (ViewPager) findViewById(R.id._viewPager);

		mainLayout = (LinearLayout) findViewById(R.id._linearLayout);

		for (int i = 0; i < images.length; i++) {

			cell = getLayoutInflater().inflate(R.layout.image_scroll, null);

			final ImageView imageView = (ImageView) cell.findViewById(R.id._image);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					viewPager.bringToFront();
					viewPager.setVisibility(View.VISIBLE);
					viewPager.setAdapter(new GalleryPagerAdapter(ProfilActivity.this, images));
					viewPager.setCurrentItem(v.getId());
				}
			});

			imageView.setId(i);

			imageView.setImageResource(images[i]);
			imageView.setScaleType(ScaleType.CENTER_INSIDE);
			mainLayout.addView(cell);
		}
	}

	private void initialiserFilCommentaire() {

	}

	@Override
	public void onBackPressed() {

		if (viewPager != null && viewPager.isShown()) {

			viewPager.setVisibility(View.GONE);
		} else {

			super.onBackPressed();
		}
	}

}