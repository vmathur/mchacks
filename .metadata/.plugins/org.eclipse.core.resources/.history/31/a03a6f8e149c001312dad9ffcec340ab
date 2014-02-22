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

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {
	private Button getDealsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getDealsButton = (Button)findViewById(R.id.getdeals);
		getDealsButton.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) {
				System.out.println("button clicked");
				//TODO Auto-generated method stub
				display(Http.getNewDeals());
			}
		});
	}

	protected void display(JSONObject newDeals) {
		//iniatilize xml reader
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		Document doc = builder.parse(new File("results.xml"));  
		Element root = doc.getDocumentElement(); 
		
		//update xml file with deal values
		System.out.println("Deal: " + newDeals.data.result.deal.en.short_title + "Store: " + newDeals.data.result.merchant.en.name + "Proximity" + newDeals.data.result.kilometers + "km" + "Expires: " + newDeals.data.result.deal.expires_at);		
		root.getElementsByTagName("results").item(0).setTextContent("Deal: " + newDeals.data.result.deal.en.short_title + "Store: " + newDeals.data.result.merchant.en.name + "Proximity" + newDeals.data.result.kilometers + "km" + "Expires: " + newDeals.data.result.deal.expires_at);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
