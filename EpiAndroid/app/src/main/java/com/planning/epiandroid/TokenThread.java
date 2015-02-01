package com.planning.epiandroid;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;



import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TokenThread extends AsyncTask<EditText, Void, Void> {
	private Planning planning;
	private View myview;
	private String token;
	private Boolean ok = false;
	private ProgressDialog prog;
	private RelativeLayout rl;
	private TextView tv;
	
	public TokenThread(Planning p, RelativeLayout layout, String t, TextView text, View v){
		planning = p;
		token = t;
		myview = v;
		rl = layout;
		tv = text;
	}
	
	protected void onPreExecute(){
		prog = ProgressDialog.show(myview.getContext(),"Checking...", "Checking token, please wait...");
	}
	
	@Override
	protected Void doInBackground(EditText... args){
		try {
			String myurl = "http://epitech-api.herokuapp.com/token";
			HttpPost post = new HttpPost(myurl);
			HttpClient send = new DefaultHttpClient();
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			
			pairs.add(new BasicNameValuePair("token", token));
			pairs.add(new BasicNameValuePair("scolaryear", planning.getScolaryear()));
			pairs.add(new BasicNameValuePair("codemodule", planning.getCodemodule()));
			pairs.add(new BasicNameValuePair("codeinstance", planning.getCodeinstance()));
			pairs.add(new BasicNameValuePair("codeacti", planning.getCodeacti()));
			pairs.add(new BasicNameValuePair("codeevent", planning.getCodeevent()));
			pairs.add(new BasicNameValuePair("tokenvalidationcode", args[0].getText().toString()));
			post.setEntity(new UrlEncodedFormEntity(pairs));

			HttpResponse response;
			response = send.execute(post);

			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() >= 500 && status.getStatusCode() <= 599)
				return null;
			InputStreamReader in = new InputStreamReader(response.getEntity().getContent());
			ObjectMapper mapper = new ObjectMapper();

			JsonNode node = mapper.readTree(in);
			JsonNode error = node.get("error");		
			if (error == null)
				ok = true;
			else
				ok = false;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(Void args){
		if (ok == true){
			rl.setVisibility(View.GONE);
			tv.setText("Success");
			tv.setVisibility(View.VISIBLE);
			System.out.println("WHYYYYYY");
		}
		else
			tv.setText("Wrong token");
		prog.dismiss();
	}
}
