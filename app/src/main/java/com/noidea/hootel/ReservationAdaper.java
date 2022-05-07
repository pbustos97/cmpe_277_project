package com.noidea.hootel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.noidea.hootel.Models.Reservation;

import java.util.List;

public class ReservationAdaper extends ArrayAdapter<Reservation> {
    private int resourceID;
    private List<Reservation> reservations;
    public ReservationAdaper(@NonNull Context context, int textViewResourceID , @NonNull List<Reservation> objects) {
        super(context, textViewResourceID , objects);
        this.reservations = objects;
        resourceID = textViewResourceID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       Reservation reservation = getItem(position);

       View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceID,null);
        }else{
            view = convertView;
        }
       TextView reservationInfo = view.findViewById(R.id.reservation_item);
       reservationInfo.setText(reservation.toString());
       return view;
    }

    public void setReservations(List<Reservation> reservationList) {
        reservations.clear();
        reservations.addAll(reservationList);
        notifyDataSetChanged();
    }
}
