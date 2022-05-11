package com.noidea.hootel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.noidea.hootel.HttpUtil;
import com.noidea.hootel.LoggedInActivity;
import com.noidea.hootel.Models.User;
import com.noidea.hootel.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    private final String TAG = UserFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "userId";
    private static final String ARG_PARAM2 = "param2";

    private static String userId;
    private JSONObject[] userLogin;
    private JSONObject[] userLoyalty;

    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewAddress;
    private TextView textViewLoyalty;

    private Button bRefreshUser;

    public UserFragment() {
    }

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
            userId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        textViewName = view.findViewById(R.id.textViewName);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewAddress = view.findViewById(R.id.textViewAddress);
        textViewLoyalty = view.findViewById(R.id.textViewLoyalty);
        bRefreshUser = view.findViewById(R.id.refreshUser);
        bRefreshUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshUser();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!User.isLoggedIn()) {
            LoggedInActivity parent = (LoggedInActivity) getActivity();
            parent.changeFragment("Selection");
        }

        String role = "userId=".concat(userId);

        String finalRole = role;
        userLogin = new JSONObject[1];
        userLoyalty = new JSONObject[1];
        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpUtil util = new HttpUtil(R.string.api_user, getContext());
                        userLogin[0] = util.getJSON("user-login?".concat(finalRole));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        HttpUtil util2 = new HttpUtil(R.string.api_loyalty, getContext());
                        userLoyalty[0] = util2.getJSON("loyalty-get?".concat(finalRole));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
            t.join();
            refreshUser();
        } catch (Exception e) {
            Log.e(TAG, "error getting user loyalty or login");
            e.printStackTrace();
        }
    }

    protected void refreshUser() {
        Log.d(TAG, "refreshUser");
        if (userLogin[0] != null) {
            Log.d(TAG, "Refreshing basic user info");
            try {
                JSONObject user = userLogin[0].getJSONObject("user");
                String name = user.getString("fName") + " ";
                name = name.concat(user.getString("lName"));
                textViewName.setText(name);

                textViewEmail.setText(user.getString("email"));

                String address = user.getString("address");
                address = address.concat(" " + user.getString("country"));
                textViewAddress.setText(address);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "User was not found");
        }
        if (userLoyalty[0] != null) {
            Log.d(TAG, "Refreshing user's loyalty info");
            try {
                JSONObject loyalty = userLoyalty[0].getJSONObject("loyalty");
                String points = "Loyalty Points: ".concat(loyalty.getString("amount"));
                textViewLoyalty.setText(points);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Loyalty account was not found");
        }
    }
}