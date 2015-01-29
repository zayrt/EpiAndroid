package com.example.epiandroid;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ModulesFragment extends Fragment {
	
	public static ModulesFragment newInstance(ArrayList<Module> list) {
	    ModulesFragment fmod = new ModulesFragment();
	    Bundle bundle = new Bundle();
	    
	    bundle.putSerializable("mymodule", list);
	    fmod.setArguments(bundle);
		return fmod;
	}
	
	public ModulesFragment() {}
	
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View myview = inflater.inflate(R.layout.modules_page, container, false);
    	
    	ListView listview = (ListView) myview.findViewById(R.id.modules);
    	listview.setAdapter(new ModuleAdapter(myview.getContext(), (List<Module>) getArguments().getSerializable("mymodule")));
    	
    	return myview;
    }
}
