package com.example.hotel.ui.Infor_User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hotel.R;

public class InforCard extends AppCompatActivity {
    TextView txtBackInforCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_card);

        txtBackInforCard=(TextView) findViewById(R.id.txtBackInforCard);

        txtBackInforCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
