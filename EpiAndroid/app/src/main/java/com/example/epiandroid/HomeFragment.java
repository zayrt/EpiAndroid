package com.example.epiandroid;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	public static HomeFragment newInstance(String logtime, Bitmap image, ArrayList<History> list) {
	    HomeFragment fhome = new HomeFragment();
	    Bundle bundle = new Bundle();
	    
	    bundle.putString("logtime", logtime);
	    bundle.putSerializable("myhistory", list);
	    bundle.putParcelable("bitMap", image);
	    fhome.setArguments(bundle);
		return fhome;
	}
	
	public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	View myview = inflater.inflate(R.layout.home_page, container, false);
    	
    	TextView log = (TextView) myview.findViewById(R.id.activeTime);
    	ImageView image = (ImageView) myview.findViewById(R.id.photo);
    	ListView mylist = (ListView) myview.findViewById(R.id.lastmessage);
    	
		image.setImageBitmap((Bitmap) getArguments().getParcelable("bitMap"));
        log.setText(getArguments().getString("logtime"));
    	LastMessageAdapter adapt = new LastMessageAdapter(myview.getContext(), (ArrayList<History>) getArguments().getSerializable("myhistory"));
		mylist.setAdapter(adapt);

    	return myview;
    }
}
