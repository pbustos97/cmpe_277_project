package com.noidea.hootel.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
                registerAccount();
            }
        });
        return view;
    }

    public void registerAccount() {

    }

    public void loginAccount() {
        CognitoSettings cognitoSettings = new CognitoSettings(this.getContext());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                cognitoSettings.login(loginField.getText().toString().replace(" ", ""), passwordField.getText().toString());
            }
        });
        thread.start();
        try {
           thread.join();

        } catch (Exception e) {
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