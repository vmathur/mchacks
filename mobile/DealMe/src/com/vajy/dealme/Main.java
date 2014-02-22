package com.vajy.dealme;


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

	protected void display(String newDeals) {
		System.out.println("Deal: " + newDeals.data.result.deal.en.short_title + "Store: " + newDeals.data.result.merchant.en.name + "Proximity" + newDeals.data.result.kilometers + "km" + "Expires: " + newDeals.data.result.deal.expires_at);		
		root.getElementsByTagName("date").item(0).setTextContent("newValue");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
