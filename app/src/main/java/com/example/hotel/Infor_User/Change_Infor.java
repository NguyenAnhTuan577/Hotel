package com.example.hotel.Infor_User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;

public class Change_Infor extends AppCompatActivity {
    TextView txtBackInfor;
    Button btnSaveInfor,btnChangeImgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_infor);

        txtBackInfor=(TextView) findViewById(R.id.txtBackInfor);
        btnSaveInfor=(Button) findViewById(R.id.SaveInfor);
        btnChangeImgAvatar=(Button) findViewById(R.id.change_imgAvatar);

        txtBackInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSaveInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Change_Infor.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
