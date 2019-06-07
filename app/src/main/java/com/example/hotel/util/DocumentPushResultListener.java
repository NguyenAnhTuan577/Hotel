package com.example.hotel.util;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public abstract class DocumentPushResultListener implements OnSuccessListener<Void>, OnFailureListener {
}
