package com.noidea.hootel;

import android.util.Log;

import com.noidea.hootel.Models.CreditCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtilSingle {
    private static final String TAG = HttpUtilSingle.class.getSimpleName();
    public static JSONObject getJSON(String endpoint) {
        try {
            URL url = new URL(endpoint);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            conn.disconnect();

            String jsonString = sb.toString();

            return new JSONObject(jsonString);
        } catch (Exception e) {
            Log.e(TAG, e.toString());

            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getJSONArray(String endpoint) {
        String jsonString = "";
        try {
            URL url = new URL(endpoint);

            Log.d(TAG, "getJSON url: " + url.toString());
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.getResponseCode();
            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
//                Log.d(TAG, "getJSON read: " + line);
                sb.append(line);
            }
            br.close();
            conn.disconnect();

            jsonString = sb.toString();
            Log.d(TAG, "getJSON jsonString: " + jsonString);
            return new JSONArray(jsonString);
        } catch (Exception e) {
            Log.e(TAG, "getJSON error: " + e.getMessage());
        }
        return null;
    }
}
