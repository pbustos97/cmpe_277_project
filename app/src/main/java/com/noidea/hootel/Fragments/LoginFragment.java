package com.noidea.hootel.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.noidea.hootel.Cognito.CognitoSettings;
import com.noidea.hootel.LoggedInActivity;
import com.noidea.hootel.MainActivity;
import com.noidea.hootel.Models.User;
import com.noidea.hootel.R;

public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();

    private EditText loginField;
    private EditText passwordField;

    private Button loginButton;
    private Button registerButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginField = view.findViewById(R.id.LoginEmailField);
        passwordField = view.findViewById(R.id.LoginPasswordField);
        loginButton = view.findViewById(R.id.LoginButtonLogin);
        registerButton = view.findViewById(R.id.LoginButtonRegistration);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAccount();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://main.d2pqi8z5jyvu7o.amplifyapp.com/customer"));
                startActivity(intent);

            }
        });
        return view;
    }

    public void registerAccount() {

    }

    public void loginAccount() {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    CognitoSettings cognitoSettings = new CognitoSettings(getContext());
                    cognitoSettings.login(loginField.getText().toString().replace(" ", ""), passwordField.getText().toString());
                }
            });
            thread.start();
            thread.join();
        } catch (Exception e) {
            Log.e(TAG, "Error getting cognito JWT");
            e.printStackTrace();
        }
        if (User.isLoggedIn()) {
//            Toast.makeText(getContext(), "Signed in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getContext(), LoggedInActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Failed to sign in", Toast.LENGTH_LONG).show();
        }
    }
}