package com.example.potago;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Upload extends Activity {

	protected static final int SELECT_FILE1 = 15;
	private static final int SELECT_FILE2 = 16;
	protected Uri image = null; // acces au fichier via contentResolver
	File fichier; // le fichier à uploader
	private String selectedPath1;
	private String selectedPath2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);

		final Button b1 = (Button) findViewById(R.id.boutonUpload);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				openGallery(SELECT_FILE1);
			}

		});
	}

	public void openGallery(final int req_code) {

		final Intent intent = new Intent();

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

			if (requestCode == SELECT_FILE2)

			{

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

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}