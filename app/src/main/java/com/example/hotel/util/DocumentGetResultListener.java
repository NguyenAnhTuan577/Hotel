package com.example.hotel.util;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public abstract class DocumentGetResultListener implements OnSuccessListener<DocumentSnapshot>, OnFailureListener {
}
