package com.login.epiandroid;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import com.example.epiandroid.R;
import com.home.epiandroid.HomePage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

public class Login extends AsyncTask<ArrayList<String>, Void, Void>{

	Context context;
	ProgressDialog prog;
	String login;
	Boolean error = false;
	String mytoken = null;
	TextView textError;
	
	public Login(Context c){
		context = c;
	    textError = (TextView) ((Activity)context).findViewById(R.id.textError);
	}
	
	protected void onPreExecute() {
		super.onPreExecute();
		prog = ProgressDialog.show(context,"Sign in...", "Checking Login and Password, please wait...");
	}

	@Override
	protected Void doInBackground(ArrayList<String>... args)
	{
		try {
			String myurl = "http://epitech-api.herokuapp.com/login";
			HttpPost post = new HttpPost(myurl);
			HttpClient send = new DefaultHttpClient();
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			
			login = args[0].get(0);
			pairs.add(new BasicNameValuePair("login", login));
			pairs.add(new BasicNameValuePair("password", args[0].get(1)));
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = send.execute(post);
			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() >= 500 && status.getStatusCode() <= 599){
				mytoken = "Server error, try again later";
				error = true;
				return null;
			}

			JsonFactory factory = new JsonFactory();
			JsonParser parser = factory.createJsonParser(new InputStreamReader(response.getEntity().getContent()));
			if (parser.nextToken() != JsonToken.START_OBJECT || parser.nextToken() == JsonToken.END_OBJECT){
				mytoken = "JSon empty";
				return null;
			}
			while (parser.nextToken() != JsonToken.END_OBJECT) {
				String token = parser.getCurrentName();
        	
				if (token.equals("token")){
					mytoken = parser.getText();
					break;
				}
				else if (token.equals("error")){
        			error = true;
	    	        token = parser.getCurrentName();
	    	        while (parser.nextToken() != JsonToken.END_OBJECT){
	    	        	token = parser.getCurrentName();
	    	        	if (token.equals("message")){
	    	        		parser.nextToken();
	    	        		mytoken = parser.getText();
	    	        		break;
	    	        	}
	    	        }
	    	        break;
				}
			}
			parser.close();
			send.getConnectionManager().shutdown();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void args) {
		if (error == false){
		  //  ((Activity)context).finish();
		    Intent home = new Intent(context,HomePage.class); 
		    home.putExtra("token", mytoken);
		    home.putExtra("login", login);
		    textError.setText("");
		    context.startActivity(home);
		}
		else{
        	textError.setText(mytoken);
        }
		prog.dismiss();
	}
}