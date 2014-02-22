package com.vajy.dealme;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

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
import org.json.JSONObject;

import android.os.Looper;

public class Http 
{
	
	private static String root = "http://deals.yellowapi.com/";
	private static String data = "search/";
	private static String latitude = "45.505768";
	private static String longitude = "-73.5786841";
	private static String API_KEY = "tnna9yr2ysxntqxzkvw6aq4k";
	private static String URL = root+data+"geo/"+longitude+"/"+latitude+"?lang=en&UID=24&apikey="+API_KEY;

	public static void getNewDeals()
	{
		System.out.println("yo");
		System.out.println(URL);

        Thread t = new Thread() 
        {

            public void run() 
            {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
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
                    	System.out.println(response.getStatusLine().getStatusCode());
                    	System.out.println(response.getEntity().getContentType());
                    	System.out.println(response.getAllHeaders().getClass());
                    	System.out.println(response);
                    	
                    	

      
                    	
                    	//InputStream in = response.getEntity().getContent(); //Get the data in the entity
                    	//System.out.println("Response is :"+ readResponse(in));
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        };

        t.start();  
     			
	}
	
	public static void sendJson(final String name1, final String value1, final String name2, final String value2, final String URL) 
	{
        Thread t = new Thread() 
        {

            public void run() 
            {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();

                try 
                {
                    HttpPost post = new HttpPost(URL);
                    json.put(name1, value1);
                    json.put(name2, value2);
                    //json.put("gender", gender);
                    StringEntity se = new StringEntity( json.toString());  
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);
                    System.out.println("Request sent!");

                    if(response!=null)
                    {
                    	InputStream in = response.getEntity().getContent(); //Get the data in the entity
                    	
                    	System.out.println("Response is :"+ readResponse(in));
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();      
    }
	
	public static String readResponse(InputStream in) throws IOException
	{
		 BufferedInputStream bis = new BufferedInputStream(in);
	     ByteArrayBuffer baf = new ByteArrayBuffer(1024);

	     int current = 0;
	     while ((current = bis.read()) != -1) {
	         baf.append((byte) current);
	     }
	     String res = new String(baf.toByteArray());

		return res;
	}

}
