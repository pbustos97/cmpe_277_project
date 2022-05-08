package com.noidea.hootel.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.noidea.hootel.LoggedInActivity;
import com.noidea.hootel.MainActivity;
import com.noidea.hootel.Models.User;
import com.noidea.hootel.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectionFragment extends Fragment {

    private Button bLogout;
    private Button bProfile;
    private Button bHotel;

    public SelectionFragment() {
        // Required empty public constructor
    }

    public static SelectionFragment newInstance(String param1, String param2) {
        SelectionFragment fragment = new SelectionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selection, container, false);
        bLogout = view.findViewById(R.id.SelectionLogout);
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });
        bProfile = view.findViewById(R.id.SelectionUser);
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile();
            }
        });
        bHotel = view.findViewById(R.id.SelectionHotel);
        bHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hotel();
            }
        });
        return view;
    }

    private void Logout() {
        User.setUserId(null);
        User.setToken(null);
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    private void Profile() {
        LoggedInActivity parent = (LoggedInActivity) getActivity();
        parent.changeFragment("Profile");
    }

    private void Hotel() {
        LoggedInActivity parent = (LoggedInActivity) getActivity();
        parent.changeFragment("Hotels");
    }
}