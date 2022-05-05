package com.noidea.hootel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noidea.hootel.Models.Address;
import com.noidea.hootel.Models.Branch;
import com.noidea.hootel.Models.Hotel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotelFragment extends Fragment {
    private final String TAG = HotelFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "hotels";
    private static final String ARG_PARAM2 = "branches";

    private ArrayList<Hotel> hotels;
    private ArrayList<Branch> branches;

    public HotelFragment() {
        // Required empty public constructor
    }

    public static HotelFragment newInstance(String param1, String param2) {
        HotelFragment fragment = new HotelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String hotelsStr = getArguments().getString(ARG_PARAM1);
            String branchesStr = getArguments().getString(ARG_PARAM2);

            try {
                JSONArray arr = new JSONArray(hotelsStr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String hotelId = obj.getString("hotelId");
                    Address address = new Address(obj.getString("address"),
                                                  obj.getString("country"));
                    String email = obj.getString("email");
                    String name = obj.getString("name");
                    String ownerId = obj.getString("ownerId");
                    hotels.add(new Hotel(hotelId, address, email, name, ownerId));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
        HotelListAdapter listAdapter = new HotelListAdapter(getContext(), hotels);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);
        return view;
    }
}