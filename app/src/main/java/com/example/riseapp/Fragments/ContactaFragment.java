package com.example.riseapp.Fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.riseapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

    public class ContactaFragment extends Fragment implements OnMapReadyCallback {

        GoogleMap map;

        public ContactaFragment(){

        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View activity = inflater.inflate(R.layout.mapa_fragment, container, false);

            return activity;
        }

        public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
            super.onViewCreated(view, savedInstanceState);
            SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;

            LatLng pp = new LatLng(41.5666700, 2.0166700);

            MarkerOptions option = new MarkerOptions();
            option.position(pp);
            map.setMinZoomPreference(13.0f);
            map.moveCamera(CameraUpdateFactory.newLatLng(pp));


        }

    }
