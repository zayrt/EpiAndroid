package com.mark.epiandroid;

import java.util.ArrayList;

import com.example.epiandroid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class MarkFragment extends Fragment {
	
	public static MarkFragment newInstance(ArrayList<Mark> list) {
	    MarkFragment fmark = new MarkFragment();
	    Bundle bundle = new Bundle();
	    
	    bundle.putSerializable("mymark", list);
	    fmark.setArguments(bundle);
		return fmark;
	}
	
	public MarkFragment(){}
	
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View myview = inflater.inflate(R.layout.modules_page, container, false);
    	
    	ListView listview = (ListView) myview.findViewById(R.id.modules);
    	listview.setAdapter(new MarkAdapter(myview.getContext(), (ArrayList<Mark>) getArguments().getSerializable("mymark")));
    	
    	Button b = (Button) myview.findViewById(R.id.button);
    	b.setVisibility(View.GONE);
    	return myview;
    }
}
