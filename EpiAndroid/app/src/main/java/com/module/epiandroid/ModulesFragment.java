package com.module.epiandroid;

import java.util.ArrayList;
import java.util.List;

import com.example.epiandroid.R;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class ModulesFragment extends Fragment  implements View.OnClickListener {
    private String token;
    private LoadModule loadModule;
    private List<Module> my_module;
    private View myview;

	public static ModulesFragment newInstance(ArrayList<Module> list, String t) {
	    ModulesFragment fmod = new ModulesFragment();
	    Bundle bundle = new Bundle();
	    
	    bundle.putSerializable("mymodule", list);
        bundle.putString("token", t);
	    fmod.setArguments(bundle);
		return fmod;
	}
	
	public ModulesFragment() {}
	
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	myview = inflater.inflate(R.layout.modules_page, container, false);
    	
    	ListView listview = (ListView) myview.findViewById(R.id.modules);
        my_module = (List<Module>) getArguments().getSerializable("mymodule");
    	listview.setAdapter(new ModuleAdapter(myview.getContext(), my_module));
        token = (String) getArguments().getSerializable("token");
        Button b = (Button) myview.findViewById(R.id.button);
        b.setText("Tous les modules");
        b.setOnClickListener(this);

    	return myview;
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v.findViewById(R.id.button);
        String buttonText = b.getText().toString();

        if (buttonText.equals("Tous les modules")) {
			ConnectivityManager cm = (ConnectivityManager) myview.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
				b.setText("Mes modules");
				loadModule = new LoadModule(token, myview);
				loadModule.execute();
			}
        }
        else {
            b.setText("Tous les modules");
            ListView listview = (ListView) myview.findViewById(R.id.modules);
            listview.setAdapter(new ModuleAdapter(myview.getContext(), my_module));
        }
    }
}
