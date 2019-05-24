package com.example.riseapp.Helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.riseapp.Fragments.ContactaFragment;
import com.example.riseapp.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ViewHolder>{
    private final LinkedList<Integer> mContactos;
    private LayoutInflater mInflater;

    public ContactosAdapter(Context context, LinkedList<Integer> contactos){
        mInflater = LayoutInflater.from(context);
        mContactos = contactos;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.basic_recycler_itemlist, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(mItemView,this);
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng posicion = null;
                ContactaFragment.changeMap(posicion);
            }
        });
        return mViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int mCurrent = mContactos.get(position);
        holder.contactosItemView.setText("Contacto "+Integer.toString(mCurrent));
    }

    @Override
    public int getItemCount() {
        return mContactos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView contactosItemView;
        final ContactosAdapter mAdapter;
        public ViewHolder(View itemView, ContactosAdapter adapter) {
            super(itemView);
            contactosItemView = itemView.findViewById(R.id.txtNombreContacto);
            mAdapter = adapter;
        }
    }
}
