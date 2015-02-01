package com.module.epiandroid;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import com.example.epiandroid.R;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by zellou_i on 2/1/15.
 */
public class LoadMyModule extends AsyncTask<String, Void, Void> {
    private String token;
    private ArrayList<Module> mymodule;
    private View myview;

    public LoadMyModule(String token, View v) {
        this.token = token;
        this.myview = v;
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

    @Override
    protected Void doInBackground(String... params) {
        try {
            String buf = getJson("https://epitech-api.herokuapp.com/modules?token=" + token);
            ObjectMapper mapper = new ObjectMapper();
            buf = buf.replace("modules", "\"modules\"");
            JsonNode node = mapper.readTree(buf);
            node = node.get("modules");
            TypeReference<ArrayList<Module>> typeRef = new TypeReference<ArrayList<Module>>(){};
            mymodule = mapper.readValue(node.traverse(), typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void args) {
        ListView listview = (ListView) myview.findViewById(R.id.modules);
        listview.setAdapter(new ModuleAdapter(myview.getContext(), mymodule));
    }
}
