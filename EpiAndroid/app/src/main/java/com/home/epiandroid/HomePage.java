package com.home.epiandroid;

import com.example.epiandroid.R;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class HomePage extends FragmentActivity {
	private String login;
	private String token;
	private String[] drawerItemsList;
	private ListView menu;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle menuDrawerToggle;
	private LoadData loadData;
	private String load;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

    	setContentView(R.layout.menu);
    	Intent intent = getIntent();
    	
    	if (intent != null){
    		token = intent.getStringExtra("token");
    		login = intent.getStringExtra("login");
    	}
    	else
    		this.finish();
		getActionBar().setTitle(R.string.home);
    	load = "Home";
    	
    	drawerItemsList = getResources().getStringArray(R.array.items);
    	menu = (ListView) findViewById(R.id.mymenu);
    	drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    	
		menu.setAdapter(new ArrayAdapter<String>(this,
				R.layout.menu_content, drawerItemsList));
		menu.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id){
				String clickedItem = (String) adapter.getAdapter().getItem(position);
				getActionBar().setTitle(clickedItem);
				load = clickedItem;
				ConnectivityManager cm = (ConnectivityManager)view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
				if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
					loadData = new LoadData(token, login, loadData.activity);
					loadData.execute(clickedItem);
				}
				drawerLayout.closeDrawer(menu);
			}
		});
    	getActionBar().setDisplayHomeAsUpEnabled(true);
    	getActionBar().setHomeButtonEnabled(true);

		menuDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_launcher, R.string.drawer_open, R.string.drawer_close) {};
		drawerLayout.setDrawerListener(menuDrawerToggle);
		
		loadData = new LoadData(token, login, this);
    	loadData.execute("Home");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.main, menu);
      return true;
    }
	   @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	if (menuDrawerToggle.onOptionsItemSelected(item)) {
	    		return true;
	    	}
	    	switch (item.getItemId()) {
	    	  case R.id.refresh:
				ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
				if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
					loadData = new LoadData(token, login, loadData.activity);
					loadData.execute(load);
				}
			    return true;
	    	  default:
	    		return super.onOptionsItemSelected(item);
	    	}
	    }
}