package com.example.potago;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

//Async Task to access the web
public abstract class JsonReadTask extends AsyncTask<String, Void, String> {

	public JsonReadTask(String url) {
		execute(new String[] { url });
	}

	@Override
	protected String doInBackground(String... params) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(params[0]);
		try {
			HttpResponse response = httpclient.execute(httppost);
			return inputStreamToString(response.getEntity().getContent()).toString();
		}

		catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private StringBuilder inputStreamToString(InputStream is) {
		String rLine = "";
		StringBuilder answer = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		try {
			while ((rLine = rd.readLine()) != null) {
				answer.append(rLine);
				// System.out.println("answer : "+answer);
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}

	@Override
	protected void onPostExecute(String result) {
		recuperationDonnee(result);

	}

	public abstract void recuperationDonnee(String result);
}// end async task