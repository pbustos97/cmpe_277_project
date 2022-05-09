package com.noidea.hootel.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.noidea.hootel.HotelActivity;
import com.noidea.hootel.HotelListAdapter;
import com.noidea.hootel.Models.Helpers.Address;
import com.noidea.hootel.Models.Branch;
import com.noidea.hootel.Models.Hotel;
import com.noidea.hootel.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotelFragment extends Fragment {
    private final String TAG = HotelFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "hotels";
    private static final String ARG_PARAM2 = "branches";
    private static final String ARG_PARAM3 = "userId";
    private static final String ARG_PARAM4 = "accessToken";

    private ArrayList<Hotel> hotels;
    private ArrayList<Branch> branches;

    private ListView hotelList;

    public HotelFragment() {
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

        hotels = new ArrayList<Hotel>();
        branches = new ArrayList<Branch>();

        if (getArguments() != null) {
            String hotelStr = getArguments().getString(ARG_PARAM1);
            String branchStr = getArguments().getString(ARG_PARAM2);

            try {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray arr = new JSONArray(hotelStr);
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);
                                String hotelId = obj.getString("hotelId");
                                Address address = new Address(obj.getString("Address"),
                                        obj.getString("Country"));
                                String email = obj.getString("email");
                                String name = obj.getString("HotelName");
                                String ownerId = obj.getString("ownerId");
                                hotels.add(new Hotel(hotelId, address, email, name, ownerId));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
                t.join();
            } catch (Exception e) {
                Log.e(TAG, "Error getting hotel list");
                e.printStackTrace();
            }

        } else {
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);

        HotelListAdapter listAdapter = new HotelListAdapter(getContext(), hotels);
        hotelList = view.findViewById(R.id.HotelListView);
        hotelList.setAdapter(listAdapter);
        hotelList.setClickable(true);
        hotelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Log.d(TAG, "select item: " + pos);
                Intent intent = new Intent(getActivity(), HotelActivity.class);
                Hotel hotel = hotels.get(pos);
                Log.d(TAG, "hotel: " + hotel.getHotelId());
                intent.putExtra("hotelId", hotel.getHotelId());
                intent.putExtra("hotelName", hotel.getName());
                intent.putExtra("hotelAddress", hotel.getAddress());
                intent.putExtra("hotelEmail", hotel.getEmail());
                intent.putExtra("userId", getArguments().getString(ARG_PARAM3));
                intent.putExtra("accessToken", getArguments().getString(ARG_PARAM4));
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}