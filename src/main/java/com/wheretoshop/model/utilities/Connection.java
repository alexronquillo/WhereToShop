package com.wheretoshop.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.net.Uri.Builder;
import android.net.Uri;

public class Connection {

    private static final String LOG_TAG = "DB_CONNECTION_LOG_TAG";
	private static final String ENCODING = "utf-8";
	private static final int MAX_BYTES = 1024;
	private static final String URL = "http://73.54.203.186:9704";

    public Connection() { }

    public String post(String path, List<NameValuePair> params) {

        try 
		{
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(URL + path);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
			return decodeResponse(httpResponse);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
			return null;
        }
    }

	public String get(String path, List<NameValuePair> params)
	{
		try {
			Uri.Builder uriBuilder = Uri.parse(URL + path).buildUpon();

			for(NameValuePair pair : params)
				uriBuilder.appendQueryParameter(pair.getName(), pair.getValue());

			String getUri = uriBuilder.build().toString(); 

            HttpClient httpClient = new DefaultHttpClient();
          	HttpGet httpGet = new HttpGet(getUri);
            HttpResponse httpResponse = httpClient.execute(httpGet);
			return decodeResponse(httpResponse);
 
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
			return null;
        }
	}

	private String decodeResponse(HttpResponse response)
	{
		try
		{
			HttpEntity httpEntity = response.getEntity();
			InputStream inputStream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, ENCODING), 8);
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			inputStream.close();

			return stringBuilder.toString();
		}
		catch(Exception e)
		{
			Log.e(LOG_TAG, e.getMessage());
			return null;
		}
	}
}
