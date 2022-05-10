package com.noidea.hootel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.noidea.hootel.Models.Hotel;
import com.noidea.hootel.Models.Reservation;
import com.noidea.hootel.Models.Room;

import com.noidea.hootel.Fragments.LoginFragment;
import com.noidea.hootel.Models.User;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSetMetaData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();
        bundle.putString("hotels", Hotel.getHotelList(getApplicationContext().getString(R.string.api_hotel)));
        this.addFragment(R.id.main_container, LoginFragment.class, bundle);

    }

    protected void addFragment(int container, Class fragment, Bundle bundle) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(container, fragment, bundle)
                .commit();
    }
}