package com.planning.epiandroid;

import java.util.ArrayList;

import com.example.epiandroid.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PlanningAdapter extends BaseAdapter {

	private ArrayList<Planning> list = null;
	private LayoutInflater inflater;
	private View layoutItem;

    public PlanningAdapter (Context context, ArrayList<Planning> mylist, String t) {
        list = mylist;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
	@Override
	public int getCount() {
		if (list == null)
			return (0);
		return (list.size());
	}

	@Override
	public Object getItem(int position) {
		return (list.get(position));
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		layoutItem = convertView;
		
		if (layoutItem == null)
			layoutItem = inflater.inflate(R.layout.event, parent, false);
		  
	    TextView title = (TextView) layoutItem.findViewById(R.id.title);
	    if (list.get(position).getTitle() == null)
	    	title.setText(list.get(position).getActi_title());
	    else
	    	title.setText(list.get(position).getTitle());
	    
	    TextView date = (TextView) layoutItem.findViewById(R.id.date);
	    if (list.get(position).getRdv_group_registered() != null)
		    date.setText(list.get(position).getRdv_group_registered().replace("|", "  "));
	    else if (list.get(position).getRdv_indiv_registered() != null)
		    date.setText(list.get(position).getRdv_indiv_registered());
	    else
	    	date.setText(list.get(position).getStart() + "   " + list.get(position).getEnd());
	   	
	    TextView room = (TextView) layoutItem.findViewById(R.id.room);
	    if (list.get(position).getRoom() != null){
	    	if (list.get(position).getRoom().getCode() != null)
	    		room.setText(list.get(position).getRoom().getCode());
	    	else
	    		room.setText("No room");
	    }
	    else
	    	room.setText("Susie class");
	    TextView enterToken = (TextView) layoutItem.findViewById(R.id.token);
	  if (list.get(position).getEvent_registered().equals("present") || list.get(position).getAllow_token().equals("false"))
	    	enterToken.setVisibility(View.GONE);

		return layoutItem;
	}
}
