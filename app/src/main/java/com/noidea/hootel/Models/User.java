package com.noidea.hootel.Models;

import com.noidea.hootel.HttpUtilSingle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class User {
    protected String id;
    protected Address address;
    protected String email;
    protected Name name;
    protected ArrayList<String> roles;

    public User(String id, Address address, String email, Name name, ArrayList<String> roles) {
        this.id = id;
        this.address = address;
        this.email = email;
        this.name = name;
        this.roles = roles;
    }

    public static User getUser(String userId, String endpoint) {
        String url = endpoint.concat("user-login?userId="+userId);
        JSONObject user = HttpUtilSingle.getJSON(url);
        try {
            user = user.getJSONObject("user");
            String id = user.getString("userId");
            String fName = user.getString("fName");
            String lName = user.getString("lName");
            String email = user.getString("email");
            String address = user.getString("address");
            String country = user.getString("country");
            JSONArray roles = user.getJSONArray("role");
            ArrayList<String> role = new ArrayList<String>();
            for (int i = 0; i < roles.length(); i++) {
                role.add(roles.getString(i));
            }
            Address address1 = new Address(address, country);
            Name name = new Name(fName, lName);
            return new User(id, address1, email, name, role);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
