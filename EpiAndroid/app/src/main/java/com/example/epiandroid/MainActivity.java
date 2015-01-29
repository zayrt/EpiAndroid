package com.example.epiandroid;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button = (Button) findViewById(R.id.signin);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ConnectivityManager cm = (ConnectivityManager)v.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
				if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
					EditText login = (EditText) findViewById(R.id.Login);
					EditText password = (EditText) findViewById(R.id.Password);
					String mylogin = login.getText().toString().trim();
					String mypassword = password.getText().toString().trim();
					ArrayList<String> mylist = new ArrayList<String>();
			
					mylist.add(mylogin);
					mylist.add(mypassword);
					Login page = new Login(v.getContext());
					page.execute(mylist);
				}
				else{
					TextView textError = (TextView) findViewById(R.id.textError);
					textError.setText("No internet connectivity detected");
				}
			}
		});
	}
}
