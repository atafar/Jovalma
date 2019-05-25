package com.example.riseapp.Helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.riseapp.Contacte;
import com.example.riseapp.R;

import java.util.ArrayList;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ViewHolder>{
    private final ArrayList<Contacte> mContactos;
    private LayoutInflater mInflater;
    private View.OnClickListener onItemClickListener;
    public int currentPosition;
    public DetailsAdapterListener btnListener;

    public ContactosAdapter(Context context, ArrayList<Contacte> contactos, DetailsAdapterListener listener){
        mInflater = LayoutInflater.from(context);
        mContactos = contactos;
        btnListener = listener;
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
        currentPosition = position;
    }

    @Override
    public int getItemCount() {
        return mContactos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nombreContacto;
        public TextView servicio;
        final ContactosAdapter mAdapter;
        public ImageView btnTlf;
        public ImageView btnLocation;
        public ImageView btnWeb;
        public ImageView btnEmail;


        public ViewHolder(final View itemView, ContactosAdapter adapter) {
            super(itemView);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
            nombreContacto = itemView.findViewById(R.id.txtNombreContacto);
            servicio = itemView.findViewById(R.id.txtServei);
            mAdapter = adapter;
            btnTlf = itemView.findViewById(R.id.imgPhone);
            btnLocation = itemView.findViewById(R.id.imgLocation);
            btnWeb = itemView.findViewById(R.id.imgWeb);
            btnEmail = itemView.findViewById(R.id.imgEmail);


            btnTlf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnListener.tlfClick(v, getAdapterPosition());
                }
            });

            btnLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnListener.locationClick(v, getAdapterPosition());
                }
            });

            btnWeb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnListener.webClick(v, getAdapterPosition());
                }
            });

            btnEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnListener.emailClick(v, getAdapterPosition());
                }
            });

        }
    }

    public interface DetailsAdapterListener {

        void tlfClick(View v, int position);
        void locationClick(View v, int position);
        void webClick(View v, int position);
        void emailClick(View v, int position);
    }
}
