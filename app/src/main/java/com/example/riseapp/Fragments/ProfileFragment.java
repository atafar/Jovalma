package com.example.riseapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.riseapp.Helper.AppPreferences;
import com.example.riseapp.Helper.Constants;
import com.example.riseapp.Helper.LocaleHelper;
import com.example.riseapp.R;
import com.example.riseapp.User;
import com.google.firebase.firestore.DocumentReference;

public class ProfileFragment extends Fragment {


   private  DocumentReference userRef;
    private TextView txtNombreUsuario, txtEmail, txtFechaNacimiento, txtGenero, txtCiudad;
    private User user;
    private ImageView imgProfile;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View activity = inflater.inflate(R.layout.profile_fragment, container, false);
        LocaleHelper.setLocale(activity.getContext(), AppPreferences.getSettings().getString("lang","es"));
        txtNombreUsuario = activity.findViewById(R.id.tv_Nombre);
        txtEmail=activity.findViewById(R.id.tv_email);
        txtCiudad=activity.findViewById(R.id.tv_ciudad);
        txtFechaNacimiento= activity.findViewById(R.id.tv_FechaNacimiento);
        txtGenero=activity.findViewById(R.id.tv_Genero);
        imgProfile=activity.findViewById(R.id.avatar);
        userRef = Constants.getFirebaseFirestore().collection("users").document(Constants.getFirebaseAuth().getCurrentUser().getUid());
        Glide.with(activity.getContext()).load(Constants.getCurrentUserProfileImage())
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).circleCrop().into(imgProfile);

        //Botón EditarPerfilActivity
        com.getbase.floatingactionbutton.FloatingActionButton fabEditarPerfil = activity.findViewById(R.id.fabEditarPerfil);
        fabEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToEdit = new Intent(getContext(), EditarPerfilActivity.class);
                startActivity(goToEdit);
                getActivity().finish();
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
            txtEmail.setText(Constants.getCurrentUser().getEmail());
            txtCiudad.setText(Constants.getCurrentUser().getCity());
            txtFechaNacimiento.setText(Constants.getCurrentUser().getDate());

            if(Constants.getCurrentUser().getGender().equals("Home")||
                    Constants.getCurrentUser().getGender().equals("Hombre")||
                    Constants.getCurrentUser().getGender().equals("Male")){

                txtGenero.setText(getString(R.string.gender_home));
            }else{
                txtGenero.setText(getString(R.string.gender_dona));
            }

        }

        //Información de usuario








        return activity;
    }
}
