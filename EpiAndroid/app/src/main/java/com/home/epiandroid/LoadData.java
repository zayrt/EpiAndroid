package com.home.epiandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.example.epiandroid.R;
import com.mark.epiandroid.Mark;
import com.mark.epiandroid.MarkFragment;
import com.module.epiandroid.Module;
import com.module.epiandroid.ModulesFragment;
import com.planning.epiandroid.Planning;
import com.planning.epiandroid.PlanningFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class LoadData extends AsyncTask<String, Void, Void> {

	private LinearLayout pb;
	private FrameLayout frame;
	private String login;
	private String token;
	private String myimage = null;
	private Bitmap bitMap;
	private String logtime;
	private String minlog;
	private String normlog;
	public HomePage activity;
	private String itemSelected;
	private Map<String, Method> list = new HashMap<String, Method>();
	private Map<String, Method> mySetView = new HashMap<String, Method>();
	private ArrayList<History> myhistory;
	private ArrayList<Module> mymodule;
	private ArrayList<Mark> mymark;
	private ArrayList<Planning> myplanning;
	private String date;
	private String dateseven;
	
	LoadData(String t, String l, HomePage v){
		login = l;
		token = t;
		activity = v;
		String myFunctions[] = {"Home", "Modules", "Mark", "Planning"};
		String mySetFunctions[] = {"setHome", "setModules", "setMark", "setPlanning"};
		pb = (LinearLayout) activity.findViewById(R.id.progressBar);
		frame = (FrameLayout) activity.findViewById(R.id.content_frame);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		date = dateformat.format(calendar.getTime());
		calendar.add(Calendar.DATE, 6);
		dateseven = dateformat.format(calendar.getTime());

		for (int i = 0; i < myFunctions.length; ++i){
			try {
				list.put(myFunctions[i], this.getClass().getMethod(myFunctions[i]));
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}	
		}
		for (int i = 0; i < mySetFunctions.length; ++i){
			try {
				mySetView.put(myFunctions[i], this.getClass().getMethod(mySetFunctions[i]));
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}	
		}
	}
	
    @Override
    protected void onPreExecute() {
		super.onPreExecute();
    	frame.setVisibility(View.GONE);
    	pb.setVisibility(View.VISIBLE);
    }
    
	public void setMark(){
		Collections.reverse(mymark);
		MarkFragment fmark = MarkFragment.newInstance(mymark);
		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.content_frame, fmark);
		transaction.commit();
	}
	
	public void setModules(){
		Collections.reverse(mymodule);
		ModulesFragment fmod = ModulesFragment.newInstance(mymodule, token);
		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.content_frame, fmod);
		transaction.commit();
	}
	
	public void setHome(){
		Double log = Double.parseDouble(logtime);
		Double dminlog = Double.parseDouble(minlog);
		if (log >= dminlog){
			dminlog = Double.parseDouble(normlog);
			if (log >= dminlog)
				logtime = String.format("%.0f", log) + "h OK";
			else 
				logtime = String.format("%.0f", log) + "h < " + String.format("%.0f", dminlog) + "h";
		}
		else
			logtime = String.format("%.0f", log) + "h < " + String.format("%.0f", dminlog) + "h";
		
		HomeFragment fhome = HomeFragment.newInstance(logtime, bitMap, myhistory);
		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.content_frame, fhome);
		transaction.commit();
	}
	
	public void setPlanning(){
		PlanningFragment fhome = PlanningFragment.newInstance(myplanning, token);
		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.content_frame, fhome);
		transaction.commit();
	}
	
	protected Void doInBackground(String... args){
		itemSelected = args[0];
		try {
			list.get(itemSelected).invoke(this);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(Void args) {
		try {
			mySetView.get(itemSelected).invoke(this);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
    	pb.setVisibility(View.GONE);
	   	frame.setVisibility(View.VISIBLE);
	}
	
	public void Planning(){
		String mytoken = "token=" + token;
		String start = "start=" + date;
		String end = "end=" + dateseven;
		
		String myurl = "http://epitech-api.herokuapp.com/planning?" + mytoken + "&" + start + "&" + end;
		try {
			URL url = new URL(myurl);

			JsonFactory factory = new JsonFactory();
			JsonParser parser = factory.createJsonParser(url);
			ObjectMapper mapper = new ObjectMapper();
			myplanning = mapper.readValue(parser, new TypeReference<ArrayList<Planning>>(){});
			int i = 0;
			while (i < myplanning.size()){
				if (myplanning.get(i).getEvent_registered() == null)
					myplanning.remove(i);
				else
					i++;
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Home(){
		String mytoken = "token=" + token;
		String mylogin = "login=" + login;
		String myurl = "http://epitech-api.herokuapp.com/photo?" + mytoken + "&" + mylogin;
		
		try {
			URL url = new URL(myurl);
			JsonFactory factory = new JsonFactory();
			JsonParser parser = factory.createJsonParser(url);
			
			if (parser.nextToken() != JsonToken.START_OBJECT || parser.nextToken() == JsonToken.END_OBJECT){
				myimage = "JSon empty";
			}
			
			while (parser.nextToken() != JsonToken.END_OBJECT) {
				String token = parser.getCurrentName();
				
				if (token.equals("url")){
					myimage = parser.getText();
					url = new URL(myimage);
					InputStream is = url.openStream();
					bitMap = BitmapFactory.decodeStream(is);
					break;
				}
			}
			parser.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logtime();
	}
	
	protected void logtime(){
		try {
			String myurl = "http://epitech-api.herokuapp.com/infos";
			HttpPost post = new HttpPost(myurl);
			HttpClient send = new DefaultHttpClient();
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			
			pairs.add(new BasicNameValuePair("token", token));
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = send.execute(post);
			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() >= 500 && status.getStatusCode() <= 599){
				return ;
			}
			
			InputStreamReader in = new InputStreamReader(response.getEntity().getContent());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(in);
			JsonNode cur = node.get("current");
			Current current = mapper.readValue(cur.traverse(), Current.class);
			logtime = current.getActive_log();
			minlog = current.getNslog_min();
			normlog = current.getNslog_norm();
			
			JsonNode hist = node.get("history");
			myhistory = mapper.readValue(hist.traverse(),  new TypeReference<ArrayList<History>>(){});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public String getJson(String str) throws IOException {
        URL url = new URL(str);
        URLConnection connexion = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
        String ligne;
        StringBuilder total = new StringBuilder();

        while ((ligne = reader.readLine()) != null) {
            total.append(ligne); 
        }
        return total.toString();
    }
    
	public void Modules(){
		try {
			String buf = getJson("https://epitech-api.herokuapp.com/modules?token=" + token);			
			ObjectMapper mapper = new ObjectMapper();
			buf = buf.replace("modules", "\"modules\"");
			JsonNode node = mapper.readTree(buf);
			node = node.get("modules");
			TypeReference<ArrayList<Module>> typeRef = new TypeReference<ArrayList<Module>>(){};
			mymodule = mapper.readValue(node.traverse(), typeRef);
		} catch (IOException e) {
			e.printStackTrace();
			}
	}
    
	public void Mark(){
		try {
			String buf = getJson("https://epitech-api.herokuapp.com/marks?token=" + token);
	        ObjectMapper mapper = new ObjectMapper();
	        buf = buf.replace("notes", "\"notes\"");
	        JsonNode node = mapper.readTree(buf);
	        node = node.get("notes");
	        TypeReference<ArrayList<Mark>> typeRef = new TypeReference<ArrayList<Mark>>(){};
	        mymark = mapper.readValue(node.traverse(), typeRef);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}