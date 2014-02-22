package com.vajy.dealme;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
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
			@Override
			public void onClick(View arg0) {
				System.out.println("button clicked");
				// TODO Auto-generated method stub
				
			}
		});

		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
