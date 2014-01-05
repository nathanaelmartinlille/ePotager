package com.example.potago;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ProfilActivity extends Activity {

	// mainLayout is the child of the HorizontalScrollView ...
	private LinearLayout mainLayout;

	// this is an array that holds the IDs of the drawables ...
	private int[] images = { R.drawable.dd1, R.drawable.dd2, R.drawable.dd3, R.drawable.dd4, R.drawable.dd5 };

	private View cell;

	private ViewPager viewPager;

	@Override
	public void onBackPressed() {

		if (viewPager != null && viewPager.isShown()) {

			viewPager.setVisibility(View.GONE);
		} else {

			super.onBackPressed();
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.activity_profil);

		viewPager = (ViewPager) findViewById(R.id._viewPager);

		mainLayout = (LinearLayout) findViewById(R.id._linearLayout);

		for (int i = 0; i < images.length; i++) {

			cell = getLayoutInflater().inflate(R.layout.cell, null);

			final ImageView imageView = (ImageView) cell.findViewById(R.id._image);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {

					viewPager.setVisibility(View.VISIBLE);
					viewPager.setAdapter(new GalleryPagerAdapter(ProfilActivity.this, images));
					viewPager.setCurrentItem(v.getId());
				}
			});

			imageView.setId(i);

			imageView.setImageResource(images[i]);

			mainLayout.addView(cell);
		}
	}
}