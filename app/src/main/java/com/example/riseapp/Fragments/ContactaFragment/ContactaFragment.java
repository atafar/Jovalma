package com.example.riseapp.Fragments.ContactaFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.riseapp.Contacte;
import com.example.riseapp.Helper.AppPreferences;
import com.example.riseapp.Helper.ContactosAdapter;
import com.example.riseapp.Helper.GestionConexion;
import com.example.riseapp.Helper.LocaleHelper;
import com.example.riseapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ContactaFragment extends Fragment implements OnMapReadyCallback, BottomSheetDialog.BottomSheetListener {

    private RecyclerView mRecyclerView;
    private ContactosAdapter mAdapter;
    private ArrayList<Contacte> mContactos = new ArrayList<>();
    private ArrayList<Marker> markers = new ArrayList<>();
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

            markers.get(position).showInfoWindow();

            LatLng coordenada = new LatLng(currentC.getLONGITUT(), currentC.getLATITUT());

            changeMap(coordenada);
        }
    };



    public void changeMap(LatLng posicion){
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(posicion, 16.0f));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View activity = inflater.inflate(R.layout.mapa_fragment, container, false);
        LocaleHelper.setLocale(activity.getContext(), AppPreferences.getSettings().getString("lang","es"));

        contacto1 = new Contacte(677420309, "Maria Del Carmen De La Felicidad", "Psicóloga", "c/ Prueba 123", 41.556310,2.398183,  "azucar.com", "1@1.1");
        contacto2 = new Contacte(654125458, "Mario", "Psicólogo", "c/ Prueba 999", 41.397216, 2.148798, "pera.com", "2@1.1");
        contacto3 = new Contacte(455214532, "Pepe", "Sicario", "c/ Prueba 666", 41.386765,2.152145,  "melon.com", "3@1.1");
        contacto4 = new Contacte(695412545, "Carmen", "Fontanera", "c/ Salud 123", 41.545757,2.109956, "piña.com", "4@1.1");
        contacto5 = new Contacte(145236574, "Pedro", "Medico", "c/ Palma 123", 41.398048,2.151553,  "melocoton.com", "5@1.1");

        mContactos.add(contacto1);
        mContactos.add(contacto2);
        mContactos.add(contacto3);
        mContactos.add(contacto4);
        mContactos.add(contacto5);

//        mContactos = GestionConexion.getContactes();


        mRecyclerView = (RecyclerView)activity.findViewById(R.id.recyclerView);
        mAdapter = new ContactosAdapter(getContext(), mContactos, new ContactosAdapter.DetailsAdapterListener() {
            @Override
            public void tlfClick(View v, int position) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                Bundle args = new Bundle();
                args.putString("titulo", getString(R.string.telefono));
                args.putString("info", Integer.toString(mContactos.get(position).getTELEFON()));
                bottomSheet.setArguments(args);
                bottomSheet.show(getFragmentManager(), "BottomSheet");
            }

            @Override
            public void locationClick(View v, int position) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                Bundle args = new Bundle();
                args.putString("titulo", getString(R.string.direccion));
                args.putString("info", mContactos.get(position).getADRECA());
                bottomSheet.setArguments(args);
                bottomSheet.show(getFragmentManager(), "BottomSheet");
            }

            @Override
            public void webClick(View v, int position) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                Bundle args = new Bundle();
                args.putString("titulo", getString(R.string.web));
                args.putString("info", mContactos.get(position).getWEB());
                bottomSheet.setArguments(args);
                bottomSheet.show(getFragmentManager(), "BottomSheet");
            }

            @Override
            public void emailClick(View v, int position) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                Bundle args = new Bundle();
                args.putString("titulo", getString(R.string.email));
                args.putString("info", mContactos.get(position).getCORREU());
                bottomSheet.setArguments(args);
                bottomSheet.show(getFragmentManager(), "BottomSheet");
            }

        });

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

        for (int i = 0; i < mContactos.size(); i++) {
            markers.add(map.addMarker(new MarkerOptions()
                    .position(new LatLng(mContactos.get(i).getLONGITUT(), mContactos.get(i).getLATITUT()))
                    .title(mContactos.get(i).getNOM())));
        }

        MarkerOptions option = new MarkerOptions();
        option.position(pp);
        map.setMinZoomPreference(11.0f);
        map.moveCamera(CameraUpdateFactory.newLatLng(pp));

    }

    @Override
    public void onButtonClicked(String text) {

    }
}