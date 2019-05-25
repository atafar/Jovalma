package com.example.riseapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.riseapp.Contacte;
import com.example.riseapp.Helper.AppPreferences;
import com.example.riseapp.Helper.ContactosAdapter;
import com.example.riseapp.Helper.LocaleHelper;
import com.example.riseapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.LinkedList;

public class ContactaFragment extends Fragment implements OnMapReadyCallback {

    private RecyclerView mRecyclerView;
    private ContactosAdapter mAdapter;
    private final ArrayList<Contacte> mContactos = new ArrayList<>();
    Contacte contacto1, contacto2, contacto3, contacto4, contacto5;
    static GoogleMap map;


    public ContactaFragment(){

    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            // viewHolder.getItemId();
            // viewHolder.getItemViewType();
            // viewHolder.itemView;
            Contacte currentC = mContactos.get(position);

            LatLng coordenada = new LatLng(currentC.getLATITUT(), currentC.getLONGITUT());
            changeMap(coordenada);
        }
    };

    public void changeMap(LatLng posicion){
        map.animateCamera(CameraUpdateFactory.newLatLng(posicion));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View activity = inflater.inflate(R.layout.mapa_fragment, container, false);
        LocaleHelper.setLocale(activity.getContext(), AppPreferences.getSettings().getString("lang","es"));

        contacto1 = new Contacte(677420309, "Maria", "Psicóloga", "c/ Prueba 123", 42.100, 2.1695, "azucar.com", "1@1.1");
        contacto2 = new Contacte(654125458, "Mario", "Psicólogo", "c/ Prueba 999", 42.200, 2.1705, "pera.com", "2@1.1");
        contacto3 = new Contacte(455214532, "Pepe", "Sicario", "c/ Prueba 666", 43.000, 1.1685, "melon.com", "3@1.1");
        contacto4 = new Contacte(695412545, "Carmen", "Fontanera", "c/ Salud 123", 42.300, 2.1685, "piña.com", "4@1.1");
        contacto5 = new Contacte(145236574, "Pedro", "Medico", "c/ Palma 123", 42.000, 2.2685, "melocoton.com", "5@1.1");

        mContactos.add(contacto1);
        mContactos.add(contacto2);
        mContactos.add(contacto3);
        mContactos.add(contacto4);
        mContactos.add(contacto5);

        mRecyclerView = (RecyclerView)activity.findViewById(R.id.recyclerView);
        mAdapter = new ContactosAdapter(getContext(), mContactos);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager l = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(l);
        mAdapter.setItemClickListener(onItemClickListener);

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