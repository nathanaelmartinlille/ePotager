package com.example.potago.upload;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class HttpUploader extends Service {

	private Intent mInvokeIntent;
	private volatile Looper mUploadLooper;
	private volatile ServiceHandler mUploadHandler;

	private int check = 0;

	private final class ServiceHandler extends Handler {
		public ServiceHandler(final Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(final Message msg) {
			// get extra datas
			final Uri selectedImg = (Uri) msg.obj;
			Log.i(getClass().getSimpleName(), "selectedImg =" + selectedImg);

			// upload the file to the web server
			doHttpUpload(selectedImg);

			Log.i(getClass().getSimpleName(), "Message: " + msg);
			Log.i(getClass().getSimpleName(), "Done with #" + msg.arg1);
			stopSelf(msg.arg1);
		}
	};

	// Method called when (an instance of) the Service is created
	@Override
	public void onCreate() {
		Log.i(getClass().getSimpleName(), "HttpUploader on create");

		// This is who should be launched if the user selects our persistent
		// notification.
		mInvokeIntent = new Intent();
		mInvokeIntent.setClassName("com.test.upload", "com.test.upload.HttpUploader");

		// Start up the thread running the service. Note that we create a
		// separate thread because the service normally runs in the process's
		// main thread, which we don't want to block.
		final HandlerThread thread = new HandlerThread("HttpUploader");
		thread.start();

		mUploadLooper = thread.getLooper();
		mUploadHandler = new ServiceHandler(mUploadLooper);
	}

	@Override
	public void onStart(final Intent uploadintent, final int startId) {
		// recup des data pour envoi via msg dans la msgqueue pour traitement
		final Message msg = mUploadHandler.obtainMessage();
		msg.arg1 = startId;
		// on place l'uri reçu dans l'intent dans le msg pour le handler
		msg.obj = uploadintent.getData();
		mUploadHandler.sendMessage(msg);
		Log.d(getClass().getSimpleName(), "Sending: " + msg);

	}

	public void doHttpUpload(final Uri myImage) {
		final String lineEnd = "\r\n";
		final String twoHyphens = "--";
		final String boundary = "*****";
		String photofile = null;
		String httpResponse; // to read http response
		String filename = null;

		final String urlString = "http://www.site.com/fileUpload.php";
		HttpURLConnection conn = null;

		InputStream fis = null;
		Bitmap mBitmap = null;
		String pathfile;

		if (myImage != null) {
			// on récupère le nom du fichier photo construit avec date et heure
			filename = "photo.jpg";

			final String[] projection = { MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DISPLAY_NAME };
			final ContentResolver cr = getContentResolver();
			final Cursor c = cr.query(myImage, projection, null, null, null);
			if (c != null && c.moveToFirst()) {
				pathfile = c.getString(0); // column0Value
				photofile = c.getString(1); // column1Value
				Log.i(getClass().getSimpleName(), "Data : " + pathfile);
				Log.i(getClass().getSimpleName(), "Display name : " + photofile);
			}

			try {
				fis = getContentResolver().openInputStream(myImage);
				mBitmap = BitmapFactory.decodeStream(fis);

				try {
					final int bytesAvailable = fis.available();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.i(getClass().getSimpleName(), "échec de lecture de la photo");
					stopSelf();
				}
			} catch (final FileNotFoundException e) {
				e.printStackTrace();
				Toast.makeText(HttpUploader.this, "échec de lecture de la photo ", Toast.LENGTH_SHORT).show();
				Log.i(getClass().getSimpleName(), "échec de lecture de la photo");
				stopSelf();
			}

		} else {
			Log.i(getClass().getSimpleName(), "myImage is null");
		}

		try {
			final URL site = new URL(urlString);
			conn = (HttpURLConnection) site.openConnection();

			// on peut écrire et lire
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// Use a post method.
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			final DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			Log.i(getClass().getSimpleName(), "Display name : " + photofile);
			Log.i(getClass().getSimpleName(), "Filename : " + filename);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + filename + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			Log.i(getClass().getSimpleName(), "Headers are written");

			// compression de image pour envoi
			mBitmap.compress(CompressFormat.JPEG, 75, dos);

			// send multipart form data necesssary after file data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// close streams
			fis.close();
			dos.flush();
			dos.close();
			Log.e("fileUpload", "File is written on the queue");

		} catch (final MalformedURLException e) {
			e.printStackTrace();
			Toast.makeText(HttpUploader.this, "échec de connexion au site web ", Toast.LENGTH_SHORT).show();
			Log.i(getClass().getSimpleName(), "échec de connexion au site web 1");
		} catch (final IOException e) {
			e.printStackTrace();
			Toast.makeText(HttpUploader.this, "échec de connexion au site web ", Toast.LENGTH_SHORT).show();
			Log.i(getClass().getSimpleName(), "échec de connexion au site web 2");
		}

		// lecture de la réponse http
		try {
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			Log.i(getClass().getSimpleName(), "try HTTP reponse");
			while ((httpResponse = rd.readLine()) != null) {
				Log.i(getClass().getSimpleName(), "HTTP reponse= " + httpResponse);
				if (httpResponse.contains("error")) {
					// there is a http error
					check += 1;
				}
			}
			rd.close();
		} catch (final IOException ioex) {
			Log.e("HttpUploader", "error: " + ioex.getMessage(), ioex);
			ioex.printStackTrace();
			Toast.makeText(HttpUploader.this, "échec de lecture de la réponse du site web ", Toast.LENGTH_SHORT).show();
			Log.i(getClass().getSimpleName(), "échec de lecture de la réponse du site web");
		}

	}

	// Method called when the (instance of) the Service is requested to terminate
	@Override
	public void onDestroy() {
		mUploadLooper.quit();

		if (check == 0) { // http response contains no error
			Toast.makeText(HttpUploader.this, "photo envoyée", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(HttpUploader.this, "échec d'envoi de la photo", Toast.LENGTH_SHORT).show();
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(final Intent intent) {
		return null;
	}
}