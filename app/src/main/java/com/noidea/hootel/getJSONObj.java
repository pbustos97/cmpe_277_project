package com.noidea.hootel;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getJSONObj extends AsyncTask<String, Void, JSONObject> {
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String resultJson = "";
    JSONObject user = null;
    @Override
    protected JSONObject doInBackground(String... param) {
        try {
            URL url = new URL(param[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            resultJson = buffer.toString();
            user = new JSONObject(resultJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}