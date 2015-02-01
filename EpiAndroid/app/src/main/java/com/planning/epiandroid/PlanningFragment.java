package com.planning.epiandroid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.example.epiandroid.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PlanningFragment extends Fragment {
	private String date;
	private String dateseven;
	private Calendar cal;
	private String token;
	private View myview;
	private ArrayList<Planning> detail;
	private Fragment frag;
	
	public static PlanningFragment newInstance(ArrayList<Planning> list, String t){
		PlanningFragment fplan = new PlanningFragment();
	    Bundle bundle = new Bundle();
	    
	    bundle.putString("token", t);
	    bundle.putSerializable("myplanning", list);
	    fplan.setArguments(bundle);
		return fplan;
	}
	
	public PlanningFragment() {}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	myview = inflater.inflate(R.layout.planning, container, false);
    	
    	cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		SimpleDateFormat dateformat = new SimpleDateFormat("E dd LLLL", Locale.US);
		date = dateformat.format(cal.getTime());
		cal.add(Calendar.DATE, 6);
		dateseven = dateformat.format(cal.getTime());
		TextView interval = (TextView) myview.findViewById(R.id.date);
		interval.setText(date + " - " + dateseven);
		
		cal.add(Calendar.DATE, -6);
		token = (String) getArguments().getSerializable("token");
		frag = this;
		Button prev = (Button) myview.findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				cal.add(Calendar.DATE, -1);
				PlanningThread plan = new PlanningThread(cal, true, myview, token, frag);
				plan.execute();
			}
		});
		Button next = (Button) myview.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				PlanningThread plan = new PlanningThread(cal, false, myview, token, frag);
				plan.execute();
			}
		});
		detail = (ArrayList<Planning>) getArguments().getSerializable("myplanning");
    	ListView listview = (ListView) myview.findViewById(R.id.event);
    	listview.setAdapter(new PlanningAdapter(myview.getContext(), detail, token));
    	listview.setOnItemClickListener(new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    			TextView t = (TextView) myview.findViewById(R.id.token);
    			if (t.getVisibility() == View.VISIBLE){
    				Intent intent = new Intent(getActivity(), EnterToken.class);
    				Bundle b = intent.getExtras();
    				b.putSerializable("detail", detail.get(position));
    				intent.putExtras(b);
    				intent.putExtra("token", token);
    				startActivity(intent);
    			}
    		}
    	});
    	
    	return (myview);
    }
}
