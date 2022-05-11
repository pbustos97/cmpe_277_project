//package com.noidea.hootel;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.noidea.hootel.Models.Hotel;
//
//import java.util.ArrayList;
//
//public class HotelListAdapter extends ArrayAdapter<Hotel>{
//    private TextView hotelNameView;
//    public HotelListAdapter(Context ctx, ArrayList<Hotel> hotelArrayList) {
//        super(ctx, R.layout.selection_item, hotelArrayList);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Hotel hotel = getItem(position);
//        final View res;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.selection_item, parent, false);
//            hotelNameView = convertView.findViewById(R.id.selection_textView);
//            hotelNameView.setText(hotel.getName());
//        }
//
//        // Set views on selection_item. Just Hotel Name with ID?
//        return convertView;
//    }
//}
