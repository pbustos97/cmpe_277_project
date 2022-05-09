package com.noidea.hootel;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.noidea.hootel.Models.myRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class sendRequest extends AsyncTask<myRequest, Void, JSONObject> {
    private final static String TAG = sendRequest.class.getSimpleName();
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    String result = "";

    @Override
    protected JSONObject doInBackground(myRequest... myRequests) {
        try {
            URL url = new URL(myRequests[0].getUrl());
            String Json = myRequests[0].getJson();
            Log.d(TAG, "getJSON url: " + url.toString());
            connection = (HttpsURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("accept", "*/*");
            if (Json != null && !TextUtils.isEmpty(Json)) {
                connection.setRequestProperty("Content-Length", String.valueOf(Json.length()));
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                out.append(Json);
                out.flush();
                out.close();
                Log.d(TAG, String.valueOf(connection.getResponseCode()));

            }
            if (connection.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                result = reader.readLine();
                Log.d(TAG, "result: " + result);

            }
        } catch (IOException e) {
            Log.e(TAG, "getJSON error: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            return new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
