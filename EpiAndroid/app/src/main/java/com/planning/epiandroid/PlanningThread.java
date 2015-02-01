package com.planning.epiandroid;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.example.epiandroid.R;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PlanningThread extends AsyncTask<Void, Void, Void> {
	private Calendar cal;
	private View myview;
	private String date;
	private String dateseven;
	private String token;
	private ArrayList<Planning> myplanning;
	private LinearLayout prog;
	private TextView interval;
	private ListView listview;
	private Fragment fplan;
	
	public PlanningThread(Calendar c, Boolean b, View v, String t, Fragment f){
		cal = c;
		myview = v;
		token = t;
		fplan = f;
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		if (b == true){
			dateseven = dateformat.format(cal.getTime());
			cal.add(Calendar.DATE, -6);
		}
		else{
			cal.add(Calendar.DATE, 13);
			dateseven = dateformat.format(cal.getTime());
			cal.add(Calendar.DATE, -6);
		}
		date = dateformat.format(cal.getTime());
		prog = (LinearLayout) myview.findViewById(R.id.progressBar);
		interval = (TextView) myview.findViewById(R.id.date);
		listview = (ListView) myview.findViewById(R.id.event);
	}
	
	protected void onPreExecute(){
    	listview.setVisibility(View.GONE);
    	interval.setVisibility(View.GONE);
    	prog.setVisibility(View.VISIBLE);
	}
	protected Void doInBackground(Void... args){
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
		return null;
	}
	
	protected void onPostExecute(Void args){
		SimpleDateFormat dateformat = new SimpleDateFormat("E dd LLLL", Locale.US);
		date = dateformat.format(cal.getTime());
		cal.add(Calendar.DATE, 6);
		dateseven = dateformat.format(cal.getTime());
		interval.setText(date + " - " + dateseven);
		cal.add(Calendar.DATE, -6);
    	listview.setAdapter(new PlanningAdapter(myview.getContext(), myplanning, token));
    	listview.setVisibility(View.VISIBLE);
    	interval.setVisibility(View.VISIBLE);

    	listview.setOnItemClickListener(new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    			TextView t = (TextView) myview.findViewById(R.id.token);
    			if (t.getVisibility() == View.VISIBLE){
    				Intent intent = new Intent(fplan.getActivity(), EnterToken.class);
    				Bundle b = new Bundle();
    				b.putSerializable("detail", myplanning.get(position));
    				intent.putExtras(b);
    				intent.putExtra("token", token);
    				fplan.startActivity(intent);
    			}
    		}
    	});
    	prog.setVisibility(View.GONE);
	}
}
