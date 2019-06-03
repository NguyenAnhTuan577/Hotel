package com.example.hotel.Infor_User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;

public class SecurAccount extends AppCompatActivity {
    TextView txtSecur;
    Button btnSavePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secur_account);

        txtSecur=(TextView) findViewById(R.id.txtBackSecur);
        btnSavePass=(Button) findViewById(R.id.SavePass);

        btnSavePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecurAccount.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
            }
        });

        txtSecur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
