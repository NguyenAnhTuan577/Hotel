package com.example.hotel.Login_Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SigninPhonenumber extends AppCompatActivity {

    EditText txtPhoneNumber, txtCode;
    Button btnSendSMS, btnVerifyCode;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_phonenumber);
        mAuth = FirebaseAuth.getInstance();
        Init();
        loadingBar = new ProgressDialog(this);

        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = txtPhoneNumber.getText().toString();
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(SigninPhonenumber.this, "Chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.setTitle("Xác nhận số điện thoại");
                    loadingBar.setMessage("Vui lòng đợi!");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNumber,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            SigninPhonenumber.this,               // Activity (for callback binding)
                            mCallbacks);        // OnVerificationStateChangedCallbacks
                }
            }
        });

        btnVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = txtCode.getText().toString();


                if (code.isEmpty()) {
                    Toast.makeText(SigninPhonenumber.this, "Chưa nhập mã code", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingBar.setTitle("Đang kiểm tra mã");
                    loadingBar.setMessage("Vui lòng đợi!");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    Toast.makeText(SigninPhonenumber.this, code, Toast.LENGTH_SHORT).show();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                loadingBar.dismiss();
                Toast.makeText(SigninPhonenumber.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                mVerificationId = verificationId;
                mResendToken = token;

                Toast.makeText(SigninPhonenumber.this, "Đang gửi mã code", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

                txtCode.setVisibility(View.VISIBLE);
                btnSendSMS.setVisibility(View.INVISIBLE);
                btnVerifyCode.setVisibility(View.VISIBLE);
                txtPhoneNumber.setEnabled(false);

            }
        };

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            Intent intent = new Intent(SigninPhonenumber.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(SigninPhonenumber.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    private void Init() {
        txtPhoneNumber = (EditText) findViewById(R.id.txtPhoneNumber);
        txtCode = (EditText) findViewById(R.id.txtCode);
        txtCode.setVisibility(View.INVISIBLE);

        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        btnVerifyCode = (Button) findViewById(R.id.btnVerifyCode);
        btnVerifyCode.setVisibility(View.INVISIBLE);
    }

}



