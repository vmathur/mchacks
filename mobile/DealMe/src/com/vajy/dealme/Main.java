package com.vajy.dealme;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import android.widget.ListView;
import java.util.ArrayList;
import android.content.Context;

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
import java.net.URL;
import java.util.List;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;




public class Main extends Activity implements LocationListener {
	private Button getDealsButton;
	private EditText getRadius;
	private LocationManager locationManager;
	private String provider;
	private TextView latituteField;
	private TextView longitudeField;
	private static Context cont;
	
	private static double curLat = 0.0;
	private static double curLong = 0.0;
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Context c = getApplicationContext();
		cont = c;
				
		latituteField = (TextView) findViewById(R.id.TextView05);
	    longitudeField = (TextView) findViewById(R.id.TextView04);
	    

	    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	    Criteria criteria = new Criteria();

	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
	    System.out.println(location);
	    
	    if (location != null) {
	      System.out.println("Provider " + provider + " has been selected.");
	      onLocationChanged(location);
	    } else {
	      latituteField.setText("Location not available");
	      longitudeField.setText("Location not available");
	    }
	    
		getRadius = (EditText)findViewById(R.id.editText1);
		getDealsButton = (Button)findViewById(R.id.getdeals);
		getDealsButton.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) {
				System.out.println("button clicked");
				new Request().execute(); 
				setContentView(R.layout.result_page);
				//accept int getRadius.getText() to set as radius
				new Request().execute(); 
				
				final ListView listview = (ListView) findViewById(R.id.list);
			    String[] values = new String[] { "Subway", "Zara", "WindowsMobile",
			        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
			        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
			        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
			        "Android", "iPhone", "WindowsMobile" };

			    final ArrayList<String> list = new ArrayList<String>();
			    for (int i = 0; i < values.length; ++i) {
			      list.add(values[i]);
			    }
			    final StableArrayAdapter adapter = new StableArrayAdapter(Main.this,
			        android.R.layout.simple_list_item_1, list);
			    listview.setAdapter(adapter);
				
			    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			        @Override
			        public void onItemClick(AdapterView<?> parent, final View view,
			            int position, long id) {
			          final String item = (String) parent.getItemAtPosition(position);
			          view.animate().setDuration(2000).alpha(0)
			              .withEndAction(new Runnable() {
			                @Override
			                public void run() {
			                  list.remove(item);
			                  adapter.notifyDataSetChanged();
			                  view.setAlpha(1);
			                }
			              });
			        }

			      });
			    
			}
		});
	}
	
	
	private class StableArrayAdapter extends ArrayAdapter<String> {

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<String> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      String item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

	  }
	
	public static void display(JSONObject newDeals) throws ParserConfigurationException, SAXException, IOException, JSONException{

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();		
		//Document doc = builder.parse(new File(Main.getFilesDir()+"res/layout/result_page.xml"));  
		//Element root = doc.getDocumentElement(); 
		
		//update xml file with deal values
		JSONArray data = newDeals.getJSONArray("data");
		for(int i = 0; i < data.length(); i++){
		    JSONObject result = data.getJSONObject(i).getJSONObject("result");
		    
		    String long2 =  result.getJSONObject("Merchant").getString("longitude");
		    String lat2 =  result.getJSONObject("Merchant").getString("latitude");
		    System.out.println("Deal: " + result.getJSONObject("Deal").getJSONObject("Translation").getJSONObject("en").getString("short_title") + " Store: " + result.getJSONObject("Merchant").getJSONObject("Translation").getJSONObject("en").getString("name") + " Expires: " + result.getJSONObject("Deal").getString("expires_at"));	
		    System.out.println("Distance: "+distance(-73.5786841,45.505768,Double.parseDouble(long2),Double.parseDouble(lat2),"K".charAt(0)));		    

		    

		    //root.getElementsByTagName("EditText").item(0).appendChild(doc.createTextNode("Deal: " + result.getJSONObject("Deal").getJSONObject("Translation").getJSONObject("en").getString("short_title") + " Store: " + result.getJSONObject("Merchant").getJSONObject("Translation").getJSONObject("en").getString("name") + "Distance: " + distance(-73.5786841,45.505768,Double.parseDouble(long2),Double.parseDouble(lat2),"K".charAt(0)) + " Expires: " + result.getJSONObject("Deal").getString("expires_at")));
		    //checkIfClose(result.getJSONObject("Merchant"),location);
		    
		    checkIfClose(cont, result,distance(curLong,curLat,Double.parseDouble(long2),Double.parseDouble(lat2),"K".charAt(0)));
		}
	}
	 
	private static double deg2rad(double deg) {
		 return (deg * Math.PI / 180.0);
	}
	 
	private static double rad2deg(double rad) {
		 return (rad * 180.0 / Math.PI);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/* Request updates at startup */
	@Override
	protected void onResume() {
	  super.onResume();
	  locationManager.requestLocationUpdates(provider, 400, 1, this);
	}
	
	  /* Remove the locationlistener updates when Activity is paused */
	  @Override
	  protected void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(this);
	  }

	@Override
	  public void onLocationChanged(Location location) {
	    double lat = (double) (location.getLatitude());
	    double lng = (double) (location.getLongitude());
	    double [] lnglat = new double[2];
	    lnglat[0] = lng;
	    lnglat[1] = lat;
	    latituteField.setText(String.valueOf(lat));
	    longitudeField.setText(String.valueOf(lng));
	      curLat = lat;
	      curLong = lng;
 	  }

	@Override
	  public void onStatusChanged(String provider, int status, Bundle extras) {
	    // TODO Auto-generated method stub

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
	
	public static void checkIfClose(Context context,JSONObject result,double distance) throws JSONException
	{

	    	if (distance<.2)
			{
				System.out.println("do stuff");
				sendAlertToPebble(context, result.getJSONObject("Merchant").getJSONObject("Translation").getJSONObject("en").getString("name"), result.getJSONObject("Deal").getJSONObject("Translation").getJSONObject("en").getString("short_title"));

			}
	    
	}
	
	public static void sendAlertToPebble(Context c, String title, String body) {
	    final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");

	    final Map data = new HashMap();
	    data.put("title",title);
	    data.put("body", body);
	    final JSONObject jsonData = new JSONObject(data);
	    final String notificationData = new JSONArray().put(jsonData).toString();

	    i.putExtra("messageType", "PEBBLE_ALERT");
	    i.putExtra("sender", "MyAndroidApp");
	    i.putExtra("notificationData", notificationData);

	    c.sendBroadcast(i);

	}
	
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}
