package com.example.riseapp;

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


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View activity = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView nombreUsuario = activity.findViewById(R.id.nombreUsuario);

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
