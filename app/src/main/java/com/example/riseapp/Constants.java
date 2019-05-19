package com.example.riseapp;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public  class Constants {

    @SuppressLint("StaticFieldLeak")
    private static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private  static FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private static  User currentUser;
    private static Uri currentUserProfileImage = null;

    public static Uri getCurrentUserProfileImage() {
        return currentUserProfileImage;
    }

    public static void setCurrentUserProfileImage(Uri currentUserProfileImage) {
        Constants.currentUserProfileImage = currentUserProfileImage;
    }

    public static FirebaseFirestore getFirebaseFirestore() {
        return firebaseFirestore;
    }

    public static FirebaseStorage getFirebaseStorage() {
        return firebaseStorage;
    }


    public static FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Constants.currentUser = currentUser;
    }

    public static void UploadFile(Uri uri, String path, String fileName, String extension) {
        Constants.getFirebaseStorage().getReference().child("users/" + getFirebaseAuth().getCurrentUser().getUid() + path + fileName + extension).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("Test", "Upload Success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Test", "Upload Failed!");
            }
        });
    }






}
