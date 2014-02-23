package com.vajy.dealme;

import com.vajy.dealme.Main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import javax.xml.parsers.ParserConfigurationException;

import android.os.AsyncTask;

import org.apache.http.StatusLine;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.xml.sax.SAXException;

import android.os.Looper;

public class Request extends AsyncTask<String, Void, JSONObject> {

	private static String root = "http://deals.sandbox.yellowapi.com/";
	private static String data = "search/";
	private static String latitude = "45.505768";
	private static String longitude = "-73.5786841";
	private static String API_KEY = "mk8cjj6thh84fbn6dgrebpez";
	
	private static String URL = root+data+"geo/"+longitude+"/"+latitude+"?radius=10&lang=en&UID=24&apikey="+API_KEY;
	
    protected JSONObject doInBackground(String... urls) {
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpResponse response;

        try 
        {
           HttpClient httpclient = new DefaultHttpClient();
	           HttpGet request = new HttpGet();
	           URI website = new URI(URL);
	           request.setURI(website);
	           
	           response = httpclient.execute(request);
	           
             if(response!=null)
             {
             	System.out.println("status: "+response.getStatusLine().getStatusCode());
             	System.out.println("content type: "+response.getEntity().getContentType());
             	
             	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
             	StringBuilder builder = new StringBuilder();
             	for (String line = null; (line = reader.readLine()) != null;) {
             	    builder.append(line).append("\n");
             	}
             	JSONObject myObject = new JSONObject(builder.toString());
             	return myObject;
             }

         } catch(Exception e) {
             e.printStackTrace();
         }
		return null;

    }

    protected void onPostExecute(JSONObject data) {
    	try {
			Main.display(data);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}