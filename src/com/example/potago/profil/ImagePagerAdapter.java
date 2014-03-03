package com.example.potago.profil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.potago.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImagePagerAdapter extends PagerAdapter {

	private String imageurl[] = null;
	private Context context = null;
	DisplayImageOptions doption = null;

	public ImagePagerAdapter(Activity activity, String imageurl[]) {
		this.context = activity;
		this.imageurl = imageurl;
		doption = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.ic_empty).showImageOnFail(R.drawable.ic_error).resetViewBeforeLoading(true)
				.cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).displayer(new FadeInBitmapDisplayer(300)).build();
	}

	@Override
	public int getCount() {
		return imageurl.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		View imageLayout = ((Activity) context).getLayoutInflater().inflate(R.layout.item_imagepager, view, false);
		ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
		final ProgressBar progressbar = (ProgressBar) imageLayout.findViewById(R.id.loading);

		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(imageurl[position], imageView, doption, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				progressbar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
				case IO_ERROR:
					message = "Input/Output error";
					break;
				case DECODING_ERROR:
					message = "Image can't be decoded";
					break;
				case NETWORK_DENIED:
					message = "Downloads are denied";
					break;
				case OUT_OF_MEMORY:
					message = "Out Of Memory error";
					break;
				case UNKNOWN:
					message = "Unknown error";
					break;
				}
				progressbar.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				progressbar.setVisibility(View.GONE);
			}
		});

		((ViewPager) view).addView(imageLayout, 0);
		return imageLayout;
	}

}
