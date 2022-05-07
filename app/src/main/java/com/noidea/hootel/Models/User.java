package com.noidea.hootel.Models;

import com.noidea.hootel.getJSONObj;
import com.noidea.hootel.Models.Helpers.Address;
import com.noidea.hootel.Models.Helpers.Name;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public static User getUser(String userId, String endpoint) throws ExecutionException, InterruptedException {
        String url = endpoint.concat("user-login?userId="+userId);
        JSONObject user = null;
        user = new getJSONObj().execute(url).get();
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
