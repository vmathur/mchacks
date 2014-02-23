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
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		Document doc = builder.parse(new File("result_page.xml"));  
		Element root = doc.getDocumentElement(); 
		
		//update xml file with deal values
		//loop through newDeals.data array
		JSONArray data = new JSONArray(newDeals.getJSONArray("data"));
		for(int i = 0; i < data.length(); i++){
		    JSONObject result = data.getJSONObject(i);
		    System.out.println("Deal: " + result.getJSONObject("deal").getJSONObject("en").getString("short_title") + "Store: " + result.getJSONObject("merchant").getJSONObject("en").getString("name") + "Proximity" + result.getString("Kilometers") + "km" + "Expires: " + result.getJSONObject("deal").getString("expires_at"));		
			//append to element
		    root.getElementsByTagName("EditText").item(0).appendChild(doc.createTextNode("Deal: " + result.getJSONObject("deal").getJSONObject("en").getString("short_title") + "Store: " + result.getJSONObject("merchant").getJSONObject("en").getString("name") + "Proximity" + result.getString("Kilometers") + "km" + "Expires: " + result.getJSONObject("deal").getString("expires_at")));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
