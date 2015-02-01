package com.module.epiandroid;

import android.os.AsyncTask;
import android.widget.Button;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;



import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AsyncModule extends AsyncTask<String, Void, Void> {
    private BModule module;
    private String token;
    private boolean post;
    private Button button;
    private Boolean perror = true;
    
    public AsyncModule(String t, BModule m, boolean p, Button b) {
        module = m;
        token = t;
        post = p;
        button = b;
    }

    public void postModule() {
        try {
            HttpPost post = new HttpPost("https://epitech-api.herokuapp.com/module");
            HttpClient send = new DefaultHttpClient();
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();

            pairs.add(new BasicNameValuePair("token", token));
            pairs.add(new BasicNameValuePair("scolaryear", String.valueOf(module.getScolaryear())));
            pairs.add(new BasicNameValuePair("codemodule", module.getCode()));
            pairs.add(new BasicNameValuePair("codeinstance", module.getCodeinstance()));
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = send.execute(post);
            StatusLine status = response.getStatusLine();
            
			InputStreamReader in = new InputStreamReader(response.getEntity().getContent());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(in);
			JsonNode error = node.get("error");
			if (error == null)
				perror = false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteModule() {
        URL url = null;
        try {
            url = new URL("https://epitech-api.herokuapp.com/module?token=" + token + "&scolaryear="
            + module.getScolaryear() + "&codemodule=" + module.getCode() + "&codeinstance=" + module.getCodeinstance());
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.getResponseCode();

        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        if (post)
            postModule();
        else
            deleteModule();
        return null;
    }

    protected void onPostExecute(Void args) {
        String buttonText = button.getText().toString();

        if (buttonText.equals("Subscribe") && perror == false) {
            button.setText("Unsubscribe");
        }
        else if (buttonText.equals("Unsubscribe")) {
            button.setText("Subscribe");
        }
    }

}
