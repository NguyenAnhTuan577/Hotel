package com.example.hotel.ui.Login_Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText txtUser, txtPassword;
    TextView txtForgotPassword;
    Button btnLogin, btnSignup, btnSigninPhonenumber;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        if(mAuth.getCurrentUser()!=null) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        Init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                    Login();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
        btnSigninPhonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SigninPhonenumber.class);
                startActivity(intent);
            }
        });
    }

    private void Init() {
        txtUser = (EditText) findViewById(R.id.txtUser);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSigninPhonenumber = (Button) findViewById(R.id.btnSigninPhonenumber);
    }

    private boolean validate() {
        String email = txtUser.getText().toString();
        String password = txtPassword.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, "Chưa nhập email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void Login() {
        String email = txtUser.getText().toString();
        String password = txtPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            loadingBar.setTitle("Đang đăng nhập");
                            loadingBar.setMessage("Vui lòng đợi!");
                            loadingBar.setCanceledOnTouchOutside(false);
                            loadingBar.show();

                            new Thread(new Runnable() {
                                public void run() {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    loadingBar.dismiss();
                                    finish();
                                }
                            }).start();

                        } else {
                            Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
