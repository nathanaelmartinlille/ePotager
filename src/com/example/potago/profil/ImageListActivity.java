package com.example.potago.profil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.potago.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageListActivity extends Activity implements OnItemClickListener {

	private ListView listview = null;
	private String[] imageUrls;
	protected ImageLoader imageLoader = null;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagelist);

		listview = (ListView) findViewById(R.id.listView_image);
		imageLoader = ImageLoader.getInstance();
		final Bundle bundle = getIntent().getExtras();
		imageUrls = Constants.IMAGES;
		final CustomAdapter adapter = new CustomAdapter(ImageListActivity.this, imageUrls);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(final AdapterView<?> arg0, final View arg1, final int position, final long arg3) {
		final Intent intent = new Intent(this, ImagePagerActivity.class);
		intent.putExtra("imageurlpostion", imageUrls);
		intent.putExtra("imagepostion", position);
		startActivity(intent);

	}

	@Override
	public void onBackPressed() {
		AnimateFirstDisplayListener.displayedImages.clear();
		super.onBackPressed();
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

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_clear_memory_cache:
			imageLoader.clearMemoryCache();
			return true;
		case R.id.item_clear_disc_cache:
			imageLoader.clearDiscCache();
			return true;
		default:
			return false;
		}
	}
}
