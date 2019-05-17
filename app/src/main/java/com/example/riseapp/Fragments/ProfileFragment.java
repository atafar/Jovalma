package com.example.riseapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.riseapp.Constants;
import com.example.riseapp.Activity.EditarPerfilActivity;
import com.example.riseapp.Activity.LoginActivity;
import com.example.riseapp.R;
import com.example.riseapp.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class ProfileFragment extends Fragment {


   private  DocumentReference userRef;
    private TextView txtNombreUsuario, txtEmail, txtFechaNacimiento, txtGenero, txtCiudad;
    private User user;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View activity = inflater.inflate(R.layout.profile_fragment, container, false);
        txtNombreUsuario = activity.findViewById(R.id.txtNombreUsuario);
        txtEmail=activity.findViewById(R.id.txtEmail);
        txtCiudad=activity.findViewById(R.id.txtCiudad);
        txtFechaNacimiento= activity.findViewById(R.id.txtFechaNacimiento);
        txtGenero=activity.findViewById(R.id.txtGenero);
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


        //Información de usuario
        try {
            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    Constants.setCurrentUser(user);

                }
            });
        }catch (Exception e){
            txtNombreUsuario.setText(e.getMessage());
        }
       if(Constants.getCurrentUser()!= null){
           txtNombreUsuario.setText(Constants.getCurrentUser().getName());
           txtEmail.setText(getResources().getString(R.string.Correu)+"                    "+Constants.getCurrentUser().getEmail());
           txtCiudad.setText(getResources().getString(R.string.ciudad)+"                   "+Constants.getCurrentUser().getCity());
           txtFechaNacimiento.setText(getResources().getString(R.string.fecha_de_nacimiento)+"                  "+Constants.getCurrentUser().getDate());
           txtGenero.setText(getResources().getString(R.string.g_nero)+"                 "+Constants.getCurrentUser().getGender());
       }



        return activity;
    }
}
