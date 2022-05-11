package com.noidea.hootel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.noidea.hootel.Models.Branch;
import com.noidea.hootel.Models.Hotel;

import java.util.ArrayList;

public class BranchListAdapter extends ArrayAdapter<Branch>{
    private TextView hotelNameView;
    public BranchListAdapter(Context ctx, ArrayList<Branch> branchArrayList) {
        super(ctx, R.layout.selection_item, branchArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Branch branch = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.selection_item, parent, false);
            hotelNameView = convertView.findViewById(R.id.selection_textView);
            hotelNameView.setText(branch.getName());
        }

        // Set views on selection_item. Just Hotel Name with ID?
        return convertView;
    }
}
