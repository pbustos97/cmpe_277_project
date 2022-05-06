package com.noidea.hootel.Models;

import android.content.Context;

import com.noidea.hootel.HttpUtilSingle;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CreditCard {
    private String ownerId;
    private String number;
    private String cvv;
    private String expDate;

    private CreditCard(String ownerId, String number, String cvv, String expDate) {
        this.ownerId = ownerId;
        this.number = number;
        this.cvv = cvv;
        this.expDate = expDate;
    }

    public static CreditCard getCard(String ownerId, String endpoint) {
        try {
            String userId;
            String Number;
            String Cvv;
            String ExpDate;

            String url = endpoint.concat("get-card?userId=" + ownerId);

            JSONObject card = HttpUtilSingle.getJSON(url);
            card = (JSONObject) card.get("card");
            userId = card.getString("ownerId");
            Number = card.getString("CardNumber");
            Cvv = card.getString("CVV");
            ExpDate = card.getString("ExpirationDate");

            return new CreditCard(userId, Number, Cvv, ExpDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
