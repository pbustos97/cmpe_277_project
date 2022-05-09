package com.noidea.hootel;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.URL;


import javax.net.ssl.HttpsURLConnection;

public class HttpUtil extends Thread {
    private static String api_url;

    public HttpUtil(int res, Context ctx) {
        this.api_url = ctx.getString(res);
    }

    public static JSONObject getJSON(String endpoint) throws JSONException {
        String jsonString = "";
        try {
            URL url = new URL(api_url + endpoint);

            Log.d("RestHelper", "getJSON url: " + url.toString());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);
            Log.d("RestHelper", "connection created");
            connection.setRequestMethod("GET");
            Log.d("RestHelper", "set request method");
            connection.connect();
            Log.d("RestHelper", "getJSON Connected");

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                Log.d("RestHelper", "getJSON read: " + line);
                sb.append(line);
            }
            br.close();

            jsonString = sb.toString();
            Log.d("RestHelper", "getJSON jsonString: " + jsonString);

        } catch (Exception e) {
            Log.e("RestHelper", "getJSON error: " + e.getMessage());
        }
        return new JSONObject(jsonString);
    }

    public static JSONArray getJSONArray(String endpoint) throws JSONException {
        String jsonString = "";
        try {
            URL url = new URL(api_url + endpoint);

            Log.d("RestHelper", "getJSON url: " + url.toString());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);
            Log.d("RestHelper", "connection created");
            connection.setRequestMethod("GET");
            Log.d("RestHelper", "set request method");
            connection.connect();
            Log.d("RestHelper", "getJSON Connected");

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                Log.d("RestHelper", "getJSON read: " + line);
                sb.append(line);
            }
            br.close();

            jsonString = sb.toString();
            Log.d("RestHelper", "getJSON jsonString: " + jsonString);

        } catch (Exception e) {
            Log.e("RestHelper", "getJSON error: " + e.getMessage());
        }
        Log.d("RestHelper", "JSONArray: " + new JSONArray(jsonString).toString());
        return new JSONArray(jsonString);
    }

    public static JSONObject sendRequest(String endpoint, String Method, String Json, String accessToken) throws JSONException {
        BufferedReader reader = null;
        String result = "";

        try {
            URL url = new URL(endpoint);

            Log.d("RestHelper", "getJSON url: " + url.toString());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(Method);
            connection.setUseCaches(false);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Authorization", accessToken);
            Log.d("RestHelper", "accessToken->" + accessToken);
            if (Json != null && !TextUtils.isEmpty(Json)) {
                connection.setRequestProperty("Content-Length", String.valueOf(Json.length()));
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                out.append(Json);
                out.flush();
                out.close();
                Log.d("RestHelper", String.valueOf(connection.getResponseCode()));

            }
            if (connection.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                result = reader.readLine();
            }
            Log.d("RestHelper", "result: " + result);
        } catch (IOException e) {
            Log.e("RestHelper", "getJSON error: " + e.getMessage());
            e.printStackTrace();
        }
        return new JSONObject(result);
    }

}