package com.example.riseapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    FirebaseUser user;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View activity = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView nombreUsuario = activity.findViewById(R.id.nombreUsuario);

        //Botón Dietas
        com.getbase.floatingactionbutton.FloatingActionButton floatingActionButton = activity.findViewById(R.id.fab1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Botón EditarPerfil
        com.getbase.floatingactionbutton.FloatingActionButton floatingActionButton2 = activity.findViewById(R.id.fab2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToEdit = new Intent(getContext(), EditarPerfil.class);
                startActivity(goToEdit);
            }
        });


        //Información de usuario
        try {
            user = FirebaseAuth.getInstance().getCurrentUser();

            String email = user.getEmail(); //HAY QUE CAMBIARLO POR user.getDisplayName() CUANDO HAYA NOMBRE
            nombreUsuario.setText(email);

        }catch (Exception e){
            nombreUsuario.setText(e.getMessage());
        }

        return activity;
    }
}
