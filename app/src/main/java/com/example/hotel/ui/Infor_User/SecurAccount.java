package com.example.hotel.ui.Infor_User;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecurAccount extends AppCompatActivity {
    TextView txtSecur;
    Button btnSavePass;
    EditText txtNewPass, txtPass, txtRetypePass;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ProgressDialog loadingBar;
    Boolean auth=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secur_account);
        loadingBar = new ProgressDialog(this);

        txtSecur = (TextView) findViewById(R.id.txtBackSecur);
        btnSavePass = (Button) findViewById(R.id.SavePass);
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtRetypePass = (EditText) findViewById(R.id.txtRetypePass);
        txtNewPass = (EditText) findViewById(R.id.txtNewPass);


        btnSavePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtPass.getText().toString().isEmpty()||txtNewPass.getText().toString().isEmpty()||txtRetypePass.getText().toString().isEmpty())
                {
                    loadingBar.dismiss();
                    Toast.makeText(SecurAccount.this, "Lỗi nhập mật khẩu", Toast.LENGTH_SHORT).show();
                }
                else

                {
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail().toString(), txtPass.getText().toString());
                    loadingBar.setTitle("Đang xử lý");
                    loadingBar.setMessage("Vui lòng đợi!");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    auth=true;
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    loadingBar.dismiss();
                                    auth=false;
                                }
                            });

                    if (txtNewPass.getText().toString().equals(txtRetypePass.getText().toString()) &&auth)
                    {
                        user.updatePassword(txtNewPass.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            loadingBar.dismiss();
                                            Toast.makeText(SecurAccount.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                    else {
                        loadingBar.dismiss();
                        Toast.makeText(SecurAccount.this, "Thông tin chưa chính xác", Toast.LENGTH_SHORT).show();
                    }
                }




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
