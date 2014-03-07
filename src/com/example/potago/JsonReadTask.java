package com.example.potago;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import com.example.potago.utils.Utils;

//Async Task to access the web
public abstract class JsonReadTask extends AsyncTask<String, Void, String> {

	public JsonReadTask(String url) {
		execute(new String[] { url });
	}

	public JsonReadTask(String url, Map<String, String> params) {

		execute(new String[] { Utils.convertirURLAvecParam(url, params) });
	}

	@Override
	protected String doInBackground(String... params) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(params[0]);
		try {
			HttpResponse response = httpclient.execute(httppost);
			return Utils.inputStreamToString(response.getEntity().getContent()).toString();
		}

		catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result) {
		recuperationDonnee(result);

	}

	public abstract void recuperationDonnee(String result);
}// end async task