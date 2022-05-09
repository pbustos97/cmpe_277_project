package com.noidea.hootel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RoomActivity extends AppCompatActivity {

    TextView roomName;
    TextView roomPrice;
    TextView roomType;
    Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        roomName = findViewById(R.id.room_roomname);
        roomPrice = findViewById(R.id.room_roomprice);
        roomType = findViewById(R.id.room_roomtype);
        nextBtn = findViewById(R.id.room_nextbtn);
        Bundle b= getIntent().getExtras();
        roomPrice.setText(b.getString("roomPrice"));
        roomName.setText(b.getString("roomName"));
        roomType.setText(b.getString("roomType"));
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("roomId", b.getString("roomId"));
                bundle.putString("roomPrice", b.getString("roomPrice"));
                bundle.putString("roomName", b.getString("roomName"));
                bundle.putString("roomType", b.getString("roomType"));
                bundle.putString("userId", b.getString("userId"));
                Intent intent = new Intent(RoomActivity.this, BookActivity.class);
                intent.putExtras(bundle);
                onPause();
                startActivity(intent);
            }
        });
    }
}