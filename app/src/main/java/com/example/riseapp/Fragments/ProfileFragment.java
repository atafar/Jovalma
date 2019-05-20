package com.example.riseapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.riseapp.Activity.EditarPerfilActivity;
import com.example.riseapp.Activity.LoginActivity;
import com.example.riseapp.Constants;
import com.example.riseapp.R;
import com.example.riseapp.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
import java.util.Objects;

public class ProfileFragment extends Fragment {


   private  DocumentReference userRef;
    private TextView txtNombreUsuario, txtEmail, txtFechaNacimiento, txtGenero, txtCiudad;
    private User user;
    private ImageView imgProfile;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View activity = inflater.inflate(R.layout.profile_fragment, container, false);
        txtNombreUsuario = activity.findViewById(R.id.txtNombreUsuario);
        txtEmail=activity.findViewById(R.id.txtEmail);
        txtCiudad=activity.findViewById(R.id.txtCiudad);
        txtFechaNacimiento= activity.findViewById(R.id.txtFechaNacimiento);
        txtGenero=activity.findViewById(R.id.txtGenero);
        imgProfile=activity.findViewById(R.id.avatar);
        userRef = Constants.getFirebaseFirestore().collection("users").document(Constants.getFirebaseAuth().getCurrentUser().getUid());

        //Botón Dietas
        com.getbase.floatingactionbutton.FloatingActionButton fabDietas = activity.findViewById(R.id.fabDietas);
        fabDietas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Botón EditarPerfilActivity
        com.getbase.floatingactionbutton.FloatingActionButton fabEditarPerfil = activity.findViewById(R.id.fabEditarPerfil);
        fabEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToEdit = new Intent(getContext(), EditarPerfilActivity.class);
                startActivity(goToEdit);
            }
        });

        //Botón Cerrar Sesión
        com.getbase.floatingactionbutton.FloatingActionButton fabCerrarSesion = activity.findViewById(R.id.fabCerrarSesion);
        fabCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Constants.getFirebaseAuth().signOut();

                startActivity(new Intent(activity.getContext(), LoginActivity.class ));
                getActivity().finish();

            }
        });
        if(Constants.getCurrentUser()!= null){
            txtNombreUsuario.setText(Constants.getCurrentUser().getName());
            txtEmail.setText(getResources().getString(R.string.Correu)+"                    "+Constants.getCurrentUser().getEmail());
            txtCiudad.setText(getResources().getString(R.string.ciudad)+"                   "+Constants.getCurrentUser().getCity());
            txtFechaNacimiento.setText(getResources().getString(R.string.fecha_de_nacimiento)+"                  "+Constants.getCurrentUser().getDate());
            txtGenero.setText(getResources().getString(R.string.g_nero)+"                 "+Constants.getCurrentUser().getGender());
        }

        //Información de usuario
        try {

            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    Constants.setCurrentUser(user);
                    txtNombreUsuario.setText(Constants.getCurrentUser().getName());
                    txtEmail.setText(getResources().getString(R.string.Correu)+"                    "+Constants.getCurrentUser().getEmail());
                    txtCiudad.setText(getResources().getString(R.string.ciudad)+"                   "+Constants.getCurrentUser().getCity());
                    txtFechaNacimiento.setText(getResources().getString(R.string.fecha_de_nacimiento)+"                  "+Constants.getCurrentUser().getDate());
                    txtGenero.setText(getResources().getString(R.string.g_nero)+"                 "+Constants.getCurrentUser().getGender());
                }
            });
        }catch (Exception e){
            txtNombreUsuario.setText(e.getMessage());
        }


            userRef.collection("data").document("constants").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String string = String.valueOf(Objects.requireNonNull(documentSnapshot.getData()).get("hasProfilePicture"));
                    Boolean hasProfilePicture = Boolean.valueOf(string);
                    if(hasProfilePicture){
                        Log.i("Profile Image", "The user has a profile image");
                        if (Constants.getCurrentUserProfileImage() != null) {
                            Glide.with(activity.getContext()).load(Constants.getCurrentUserProfileImage())
                                    .apply(RequestOptions.skipMemoryCacheOf(true))
                                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).circleCrop().into(imgProfile);
                        }else {
                            final File mFile = new File(activity.getContext().getFilesDir().getAbsolutePath(), "profile_icon.png");
                            Constants.getFirebaseStorage().getReference().child("users/" + Objects.requireNonNull(Constants.getFirebaseAuth().getCurrentUser()).getUid() + "/" + "profile_icon.png").getFile(mFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Log.i("updateProfileImages", " Loading...");

                                    Constants.setCurrentUserProfileImage(Uri.fromFile(mFile));
                                    Glide.with(activity.getContext()).load(Constants.getCurrentUserProfileImage())
                                            .apply(RequestOptions.skipMemoryCacheOf(true))
                                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).circleCrop().into(imgProfile);
                                }
                            });
                        }
                    }else{
                        Log.i("Profile Image", " The user doesn't have a profile image");

                        Glide.with(activity.getContext()).load(activity.getContext().getDrawable(R.drawable.account_default)).into(imgProfile);
                    }
                }
            });






        return activity;
    }
}
