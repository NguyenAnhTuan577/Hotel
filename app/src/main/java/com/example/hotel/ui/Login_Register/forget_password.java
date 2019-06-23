package com.example.hotel.ui.Login_Register;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget_password extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button btnSendEmail;
    TextView txtsendPassWordEmail;
    private ProgressDialog loadingBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        btnSendEmail = (Button) findViewById(R.id.btnSendEmail);
        txtsendPassWordEmail = (TextView) findViewById(R.id.txtsendPassWordEmail);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtsendPassWordEmail.getText() != null) {
                    loadingBar.setTitle("Đang gửi email tới " + txtsendPassWordEmail.getText().toString());
                    loadingBar.setMessage("Vui lòng đợi!");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    mAuth.sendPasswordResetEmail(txtsendPassWordEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        loadingBar.dismiss();
                                        Toast.makeText(forget_password.this, "Gửi thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(forget_password.this, "Gửi thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(forget_password.this, "Chưa nhập email", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
