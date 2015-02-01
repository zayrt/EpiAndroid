package com.planning.epiandroid;

import com.example.epiandroid.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EnterToken extends Activity {
	private Planning detail;
	private String token;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_token);
		
		Intent intent = getIntent();
		detail = (Planning) intent.getExtras().getSerializable("detail");
		token = (String) intent.getStringExtra("token");

		Button valid = (Button) findViewById(R.id.validate);
		valid.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				System.out.println("alloa");
				RelativeLayout rl = (RelativeLayout) findViewById(R.id.allowToken);
				EditText et = (EditText) findViewById(R.id.token);
				TextView tv = (TextView) findViewById(R.id.ok);
				TokenThread tToken = new TokenThread(detail, rl, token, tv, v);
				tToken.execute(et);
			}
		});
	}
}
