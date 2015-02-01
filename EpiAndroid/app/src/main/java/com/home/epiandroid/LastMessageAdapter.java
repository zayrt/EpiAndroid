package com.home.epiandroid;

import java.util.ArrayList;

import com.example.epiandroid.R;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LastMessageAdapter extends BaseAdapter {

	private ArrayList<History> list = null;
	private Context myContext;
	private LayoutInflater inflater;
	
	LastMessageAdapter(Context c, ArrayList<History> l){
		myContext = c;
		list = l;
		inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	public View getView(int position, View view, ViewGroup group) {
		LinearLayout layoutItem;
		TextView title;
		TextView content;
		TextView date;
		
		  if (view == null)
			  layoutItem = (LinearLayout) inflater.inflate(R.layout.last_message, group, false);
		  else
			  layoutItem = (LinearLayout) view;
		  title = (TextView) layoutItem.findViewById(R.id.title);
		  content = (TextView) layoutItem.findViewById(R.id.content);
		  date = (TextView) layoutItem.findViewById(R.id.date);

		  title.setText(Html.fromHtml(list.get(position).getTitle()));
		  content.setText(Html.fromHtml(list.get(position).getContent()));
		  date.setText(list.get(position).getDate());
		  
		return layoutItem;
	}

}
