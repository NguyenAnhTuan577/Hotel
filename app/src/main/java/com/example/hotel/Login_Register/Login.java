package com.example.hotel.Login_Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.ui.main.MainActivity;

public class Login extends AppCompatActivity {

    EditText txtUser, txtPassword;
    TextView txtForgotPassword;
    Button btnLogin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                        R.style.AppTheme_TransparentTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                new Thread(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(Login.this,MainActivity.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                    }
                }).start();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "dfd", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Init() {
        txtUser = (EditText) findViewById(R.id.txtUser);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
    }


}
