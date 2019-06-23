package com.example.hotel.ui.Login_Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.model.UserInfo;
import com.example.hotel.ui.main.MainActivity;
import com.example.hotel.util.DocumentPushResultListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    EditText txtEmailSignup, txtPasswordSignup, txtRetypePassSigup;
    Button btnSign_up,btnSigninPhonenumber;

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
        btnSigninPhonenumber=(Button) findViewById(R.id.btnSigninPhonenumber);
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
        btnSigninPhonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SigninPhonenumber.class);
                startActivity(intent);
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

                            if (task.isSuccessful()&&task.getResult()!=null)
                                createUserInfo(task.getResult().getUser());
                                else if(task.getException()!=null) notifyFailure(task.getException().getMessage());
                                else notifyFailure("Xin lỗi, vui lòng thử lại");
                        }
                    });
    }
    private void notifyOnSuccess() {
        loadingBar.dismiss();
        Toast.makeText(SignUp.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    private void notifyFailure(String message) {
        loadingBar.dismiss();
        Toast.makeText(SignUp.this,message, Toast.LENGTH_SHORT).show();

    }

    private void createUserInfo(FirebaseUser user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setBalance(0);
        userInfo.setUid(user.getUid());
        Log.d("SignUp", "createUserInfo: AutoCreate user info");

        FirebaseFirestore.getInstance()
                .collection("user_infos")
                .document(user.getUid())
                .set(userInfo)
                .addOnSuccessListener(mPushUserInfoListener)
                .addOnFailureListener(mPushUserInfoListener);

    }

    private DocumentPushResultListener mPushUserInfoListener = new DocumentPushResultListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            notifyFailure(e.getMessage());
        }

        @Override
        public void onSuccess(Void aVoid) {
            notifyOnSuccess();
        }
    };

}
