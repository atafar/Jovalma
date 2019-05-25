package com.example.riseapp.Helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.riseapp.Contacte;
import com.example.riseapp.R;

import java.util.ArrayList;

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

        return mViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contacte mCurrent = mContactos.get(position);
        holder.nombreContacto.setText(mCurrent.getNOM());
        holder.servicio.setText(mCurrent.getSERVEI());
    }

    @Override
    public int getItemCount() {
        return mContactos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nombreContacto;
        public TextView servicio;
        final ContactosAdapter mAdapter;

        public ViewHolder(View itemView, ContactosAdapter adapter) {
            super(itemView);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
            nombreContacto = itemView.findViewById(R.id.txtNombreContacto);
            servicio = itemView.findViewById(R.id.txtServei);
            mAdapter = adapter;
        }
    }
}
