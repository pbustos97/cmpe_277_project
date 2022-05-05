package com.noidea.hootel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.noidea.hootel.Models.Hotel;

import java.util.ArrayList;

public class HotelListAdapter extends ArrayAdapter<Hotel> {
    private TextView textName;
    private TextView textDescription;

    public HotelListAdapter(Context ctx, ArrayList<Hotel> hotelArrayList) {
        super(ctx, R.layout.selection_item, hotelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Hotel hotel = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.selection_item, parent, false);
        }

        // Set views on selection_item. Just Hotel Name and Address?
        textName = convertView.findViewById(R.id.selection_textView_name);
        textDescription = convertView.findViewById(R.id.selection_textView_description);

        textName.setText(hotel.getName());
        textDescription.setText(hotel.getAddress());


        return super.getView(position, convertView, parent);
    }
}
