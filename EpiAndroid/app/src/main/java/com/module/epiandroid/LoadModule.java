package com.module.epiandroid;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.example.epiandroid.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class LoadModule extends AsyncTask<String, Void, Void>  {
    private String token;
    private View view;
    private List<BModule> allmodule;

    public LoadModule(String t, View v) {
        token = t;
        view = v;
    }

    public String getJson(String str) throws IOException {
        URL url = new URL(str);
        URLConnection connexion = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
        String ligne;
        StringBuilder total = new StringBuilder();

        while ((ligne = reader.readLine()) != null) {
            total.append(ligne);
        }
        return total.toString();
    }

    protected void onPreExecute(){
        ListView listview = (ListView) view.findViewById(R.id.modules);
        LinearLayout l = (LinearLayout) view.findViewById(R.id.progressBar);
        l.setVisibility(View.VISIBLE);
        listview.setVisibility(View.GONE);
    }
    
    @Override
    protected Void doInBackground(String... params) {
        try {
            String buf = getJson("https://epitech-api.herokuapp.com/allmodules?token=" + token + "&scolaryear=2014&location=FR/PAR&course=bachelor/classic");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(buf);
            node = node.get("items");
            TypeReference<List<BModule>> typeRef = new TypeReference<List<BModule>>(){};
            allmodule = mapper.readValue(node.traverse(), typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void args) {
        ListView listview = (ListView) view.findViewById(R.id.modules);
        listview.setAdapter(new BModuleAdapter(view.getContext(), allmodule, token));
        LinearLayout l = (LinearLayout) view.findViewById(R.id.progressBar);
        l.setVisibility(View.GONE);
        listview.setVisibility(View.VISIBLE);
    }
}
