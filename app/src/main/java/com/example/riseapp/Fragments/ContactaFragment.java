package com.example.riseapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.riseapp.Helper.ContactosAdapter;
import com.example.riseapp.AppPreferences;
import com.example.riseapp.Helper.LocaleHelper;
import com.example.riseapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;

public class ContactaFragment extends Fragment implements OnMapReadyCallback {

    private RecyclerView mRecyclerView;
    private ContactosAdapter mAdapter;

    private final LinkedList<Integer> mContactos = new LinkedList<>();

        GoogleMap map;

        public ContactaFragment(){

        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View activity = inflater.inflate(R.layout.mapa_fragment, container, false);
            LocaleHelper.setLocale(activity.getContext(), AppPreferences.getSettings().getString("lang","es"));

            int contactos = 10;

            for(int i = 1; i<=contactos; i++){
                mContactos.add(i);
            }
            mRecyclerView = (RecyclerView)activity.findViewById(R.id.recyclerView); //hace falta parsear a (mRecyclerView)????
            mAdapter = new ContactosAdapter(getContext(), mContactos);
            mRecyclerView.setAdapter(mAdapter);
            LinearLayoutManager l = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            mRecyclerView.setLayoutManager(l);

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

            LatLng pp = new LatLng(41.3818, 2.1685);

            MarkerOptions option = new MarkerOptions();
            option.position(pp);
            map.setMinZoomPreference(11.0f);
            map.moveCamera(CameraUpdateFactory.newLatLng(pp));


        }

    }
