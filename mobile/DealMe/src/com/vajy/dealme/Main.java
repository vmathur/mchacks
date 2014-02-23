package com.vajy.dealme;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.*;
import java.util.HashMap; 


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Button;

public class Main extends Activity {
	private Button getDealsButton;
	private NumberPicker getRadius;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getRadius = (NumberPicker)findViewById(R.id.numberPicker1);
		getDealsButton = (Button)findViewById(R.id.getdeals);
		getDealsButton.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) {
				System.out.println("button clicked");
				setContentView(R.layout.result_page);
				//accept int getRadius.getValue() to set as radius
				new Request().execute(); 
			}
		});
		
	}
	
	public static void display(JSONObject newDeals) throws ParserConfigurationException, SAXException, IOException, JSONException{
		
		//put user location on a map
		//Map.initializeMap(ShowLocationActivity.getUserPosition());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		Document doc = builder.parse(new File("result_page.xml"));  
		Element root = doc.getDocumentElement(); 

		//update xml file with deal values
		JSONArray data = newDeals.getJSONArray("data");
		for(int i = 0; i < data.length(); i++){
		    JSONObject result = data.getJSONObject(i).getJSONObject("result");
		    
		    String long2 =  result.getJSONObject("Merchant").getString("longitude");
		    String lat2 =  result.getJSONObject("Merchant").getString("latitude");
		    System.out.println("Deal: " + result.getJSONObject("Deal").getJSONObject("Translation").getJSONObject("en").getString("short_title") + " Store: " + result.getJSONObject("Merchant").getJSONObject("Translation").getJSONObject("en").getString("name") + " Expires: " + result.getJSONObject("Deal").getString("expires_at"));	
		    System.out.println("Distance: "+distance(-73.5786841,45.505768,Double.parseDouble(long2),Double.parseDouble(lat2),"K".charAt(0)));
		    //append to element
		    root.getElementsByTagName("EditText").item(0).appendChild(doc.createTextNode("Deal: " + result.getJSONObject("Deal").getJSONObject("Translation").getJSONObject("en").getString("short_title") + " Store: " + result.getJSONObject("Merchant").getJSONObject("Translation").getJSONObject("en").getString("name") + "Distance: " + distance(-73.5786841,45.505768,Double.parseDouble(long2),Double.parseDouble(lat2),"K".charAt(0)) + " Expires: " + result.getJSONObject("Deal").getString("expires_at")));
		    //put deal locations on map
		    //Map.placeMarker(result);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
	      double theta = lon1 - lon2;
	      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
	      dist = Math.acos(dist);
	      dist = rad2deg(dist);
	      dist = dist * 60 * 1.1515;
	      if (unit == 'K') {
	        dist = dist * 1.609344;
	      } else if (unit == 'N') {
	        dist = dist * 0.8684;
	        }
	      return (dist);
	    }
	 
	private static double deg2rad(double deg) {
		 return (deg * Math.PI / 180.0);
	}
	 
	private static double rad2deg(double rad) {
		 return (rad * 180.0 / Math.PI);
	}
	
	

}
