package com.vajy.dealme;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONObject;

import android.os.Looper;

public class Http 
{
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
