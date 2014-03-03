package com.example.potago.profil;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.potago.R;

public class ImagePagerActivity extends Activity {

	ViewPager pager;
	private static final String STATE_POSITION = "STATE_POSITION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagepager);

		Bundle bundle = getIntent().getExtras();
		String[] imageUrls = bundle.getStringArray("imageurlpostion");
		int pagerPosition = bundle.getInt("imagepostion", 0);

		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}
		pager = (ViewPager) findViewById(R.id.pager);

		ImagePagerAdapter adapter = new ImagePagerAdapter(ImagePagerActivity.this, imageUrls);
		pager.setAdapter(adapter);
		pager.setCurrentItem(pagerPosition);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}

}
