package com.example.riseapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    FirebaseUser user;
    private FirebaseAuth mAuth;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View activity = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView nombreUsuario = activity.findViewById(R.id.nombreUsuario);
        mAuth = FirebaseAuth.getInstance();
        //Botón Dietas
        com.getbase.floatingactionbutton.FloatingActionButton fabDietas = activity.findViewById(R.id.fabDietas);
        fabDietas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Botón EditarPerfil
        com.getbase.floatingactionbutton.FloatingActionButton fabEditarPerfil = activity.findViewById(R.id.fabEditarPerfil);
        fabEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToEdit = new Intent(getContext(), EditarPerfil.class);
                startActivity(goToEdit);
            }
        });

        //Botón Cerrar Sesión
        com.getbase.floatingactionbutton.FloatingActionButton fabCerrarSesion = activity.findViewById(R.id.fabCerrarSesion);
        fabCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                startActivity(new Intent(activity.getContext(),MainActivity.class ));
                getActivity().finish();

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
