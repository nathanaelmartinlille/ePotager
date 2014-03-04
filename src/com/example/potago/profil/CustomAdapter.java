package com.example.potago.profil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.potago.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class CustomAdapter extends BaseAdapter {

	private String imageurl[] = null;
	private Context context = null;
	DisplayImageOptions doption = null;
	private ImageLoadingListener animateFirstListener = null;

	public CustomAdapter(final Activity activity, final String[] imageurl) {
		this.context = activity;
		this.imageurl = imageurl;
		doption = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.ic_empty).showImageOnFail(R.drawable.ic_error).showStubImage(R.drawable.ic_stub)
				.cacheInMemory(true).cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(20)).build();
		animateFirstListener = new AnimateFirstDisplayListener();
	}

	@Override
	public int getCount() {
		return imageurl.length;
	}

	@Override
	public Object getItem(final int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(final int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {

		View view = convertView;
		final ViewHolder holder;

		if (convertView == null) {
			view = ((Activity) context).getLayoutInflater().inflate(R.layout.item_list_row, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) view.findViewById(R.id.image);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		final ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(imageurl[position], holder.image, doption, animateFirstListener);

		return view;
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

	private class ViewHolder {
		public TextView text;
		public ImageView image;
	}
}
