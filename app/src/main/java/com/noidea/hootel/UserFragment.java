package com.noidea.hootel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    private final String TAG = UserFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "userId";
    private static final String ARG_PARAM2 = "param2";

    private String userId;
    private JSONObject[] userLogin;
    private JSONObject[] userRole;

    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewAddress;

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
            Log.d(TAG, "ARG_PARAM1 ==> " + ARG_PARAM1);
        }

        String role = "userId=".concat(userId);
        Log.d(TAG, "role ==> " + role);
        String finalRole = role;
        userLogin = new JSONObject[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpUtil util = new HttpUtil(R.string.api_user, getContext());
                    userLogin[0] = util.getJSON("user-login?".concat(finalRole));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        role = role.concat("&role=Customer");

        String finalRole1 = role;
        userRole = new JSONObject[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpUtil util = new HttpUtil(R.string.api_user, getContext());
                    userRole[0] = util.getJSON("get-role?".concat(finalRole1));
//                    Log.d(TAG, userRole[0].toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        textViewName = view.findViewById(R.id.textViewName);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewAddress = view.findViewById(R.id.textViewAddress);
        bRefreshUser = view.findViewById(R.id.refreshUser);
        bRefreshUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshUser();
            }
        });
        refreshUser();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
        }
        if (userRole[0] != null) {
            Log.d(TAG, "Refreshing customer info");
            try {
                JSONObject user = userRole[0].getJSONObject("user");
                // Collect list of bookingIds and send to booking fragment?
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}