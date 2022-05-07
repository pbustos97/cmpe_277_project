package com.noidea.hootel;

import android.util.Log;

import com.noidea.hootel.Models.CreditCard;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtilSingle {
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

            String jsonString = sb.toString();

            return new JSONObject(jsonString);
        } catch (Exception e) {
            Log.d("HTTpUtiliSingle: ", e.toString());
            e.printStackTrace();
        }
        return null;
    }
}
