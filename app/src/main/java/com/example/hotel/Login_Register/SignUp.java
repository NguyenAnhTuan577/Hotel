package com.example.hotel.Login_Register;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    EditText txtEmailSignup, txtPasswordSignup, txtRetypePassSigup;
    Button btnSign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loadingBar = new ProgressDialog(this);
        Init();
        mAuth = FirebaseAuth.getInstance();
        Click();
    }

    private boolean validatepass() {
        String email = txtEmailSignup.getText().toString();
        String pass = txtPasswordSignup.getText().toString();
        String retypepass = txtRetypePassSigup.getText().toString();
        if(email.isEmpty())
        {
            Toast.makeText(this, "Chưa nhập email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(pass.isEmpty())
        {
            Toast.makeText(this, "Chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(retypepass.isEmpty())
        {
            Toast.makeText(this, "Nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pass.equals(retypepass)) return true;
        else {
            Toast.makeText(this, "Nhập khẩu nhập không khớp,nhập lại!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void Init() {
        txtEmailSignup = (EditText) findViewById(R.id.txtEmailSignup);
        txtPasswordSignup = (EditText) findViewById(R.id.txtPasswordSignup);
        txtRetypePassSigup = (EditText) findViewById(R.id.txtRetypePassSigup);
        btnSign_up = (Button) findViewById(R.id.btnSign_up);
    }

    private void Click() {
        btnSign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatepass()) {
                    loadingBar.setTitle("Đang đăng kí");
                    loadingBar.setMessage("Vui lòng đợi!");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    Signup();
                }
            }
        });
    }

    private void Signup() {
        String email = txtEmailSignup.getText().toString();
        String password = txtPasswordSignup.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loadingBar.dismiss();
                                Toast.makeText(SignUp.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }

}
