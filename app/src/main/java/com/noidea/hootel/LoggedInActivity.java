package com.noidea.hootel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.noidea.hootel.Models.User;

public class LoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();
        bundle.putString("userId", User.getUserId());
        bundle.putString("accessToken", User.getToken());
        this.addFragment(R.id.main_container, SelectionFragment.class, bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loggedIn();
    }

    protected void addFragment(int container, Class fragment, Bundle bundle) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(container, fragment, bundle)
                .commit();
    }

    protected void removeFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        getSupportFragmentManager().beginTransaction()
                .remove(fragment).commit();
    }

    public void loggedIn() {
        if (!User.isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}