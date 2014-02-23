package com.vajy.dealme;


import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import org.json.JSONObject;


public class Main extends Activity {
	
	private Button getDealsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getDealsButton = (Button)findViewById(R.id.getdeals);
		getDealsButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View arg0) {
				System.out.println("button clicked");
				System.out.println(new Request().execute()); 
			}
		});
		
	}
	
	public static void display(JSONObject obj){
		System.out.println(obj);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
