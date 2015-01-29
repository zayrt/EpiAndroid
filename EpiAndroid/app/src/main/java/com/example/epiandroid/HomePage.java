package com.example.epiandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
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
	private ActionBarDrawerToggle mDrawerToggle;
	private LoadData loadData;
	
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
		
    	drawerItemsList = getResources().getStringArray(R.array.items);
    	menu = (ListView) findViewById(R.id.mymenu);
    	drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    	
		menu.setAdapter(new ArrayAdapter<String>(this,
				R.layout.menu_content, drawerItemsList)); /* Je pète le nom des items qu'il y aurant dans le menu */
		menu.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id){
				String clickedItem = (String) adapter.getAdapter().getItem(position);
				getActionBar().setTitle(clickedItem);
				loadData = new LoadData(token, login, loadData.activity); /* je recrée un objet asynctask, la fonction execute ne pouvant etre appelé que une fois*/
		    	loadData.execute(clickedItem); /* si je clique sur Home la string envoyé sera Home*/
				drawerLayout.closeDrawer(menu);
			}
		});
    	getActionBar().setDisplayHomeAsUpEnabled(true);
    	getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_launcher, R.string.drawer_open, R.string.drawer_close) {};
		drawerLayout.setDrawerListener(mDrawerToggle);
		
		loadData = new LoadData(token, login, this);
    	loadData.execute("Home");
    }

	   @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	if (mDrawerToggle.onOptionsItemSelected(item)) {
	    		return true;
	    	}
	    	switch (item.getItemId()) {
	    	  case R.id.action_settings:
	    		return true;
	    	  default:
	    		return super.onOptionsItemSelected(item);
	    	}
	    }
}