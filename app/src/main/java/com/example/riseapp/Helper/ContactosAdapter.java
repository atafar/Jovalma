package com.example.riseapp.Helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.riseapp.Contacte;
import com.example.riseapp.Fragments.ContactaFragment;
import com.example.riseapp.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.LinkedList;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ViewHolder>{
    private final ArrayList<Contacte> mContactos;
    private LayoutInflater mInflater;
    private View.OnClickListener onItemClickListener;


    public ContactosAdapter(Context context, ArrayList<Contacte> contactos){
        mInflater = LayoutInflater.from(context);
        mContactos = contactos;
    }

    public void setItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View mItemView = mInflater.inflate(R.layout.basic_recycler_itemlist, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(mItemView,this);
//        mItemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LatLng posicion = null;
//                ContactaFragment.changeMap(posicion);
//            }
//        });
        return mViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contacte mCurrent = mContactos.get(position);
        holder.contactosItemView.setText(mCurrent.getNOM());
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
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
            contactosItemView = itemView.findViewById(R.id.txtNombreContacto);
            mAdapter = adapter;
        }
    }
}
