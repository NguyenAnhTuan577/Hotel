package com.example.hotel.ui.Infor_User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Change_Infor extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 101;
    TextView txtBackInfor, txtUseNameChange;
    Button btnSaveInfor, btnChangeImgAvatar;
    ImageView imgChangeAvatar;
    private ProgressDialog loadingBar;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_infor);
        loadingBar = new ProgressDialog(this);


        txtUseNameChange = (TextView) findViewById(R.id.txtUseNameChange);
        txtUseNameChange.setText(user.getDisplayName());
        txtBackInfor = (TextView) findViewById(R.id.txtBackInfor);
        btnSaveInfor = (Button) findViewById(R.id.SaveInfor);
        btnChangeImgAvatar = (Button) findViewById(R.id.change_imgAvatar);
        imgChangeAvatar = (ImageView) findViewById(R.id.imgChangeAvatar);

        if (user.getPhotoUrl() != null) {

        }

        txtBackInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent refresh = new Intent(Change_Infor.this, MainActivity.class);
                startActivity(refresh);
            }
        });

        btnSaveInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setTitle("Đang Lưu");
                loadingBar.setMessage("Vui lòng đợi!");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                if(user!=null)
                {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(txtUseNameChange.getText().toString())
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        loadingBar.dismiss();
                                        Toast.makeText(Change_Infor.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }


        });
        btnChangeImgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgChangeAvatar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void ChooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHOOSE_IMAGE);
    }

}
