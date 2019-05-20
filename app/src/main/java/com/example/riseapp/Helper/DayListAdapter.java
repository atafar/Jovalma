package com.example.riseapp.Helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.riseapp.R;

import java.util.LinkedList;

public class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.ViewHolder>{
    private final LinkedList<Integer> mDayList;
    private LayoutInflater mInflater;

    public DayListAdapter(Context context, LinkedList<Integer> dayList){
        mInflater = LayoutInflater.from(context);
        mDayList = dayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.basic_recycler_itemlist, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(mItemView,this);
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ACCIÓN A REALIAR
            }
        });
        return mViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int mCurrent = mDayList.get(position);
        holder.dayListItemView.setText("Contacto "+Integer.toString(mCurrent));
    }

    @Override
    public int getItemCount() {
        return mDayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView dayListItemView;
        final DayListAdapter mAdapter;
        public ViewHolder(View itemView, DayListAdapter adapter) {
            super(itemView);
            dayListItemView = itemView.findViewById(R.id.txt_day);
            mAdapter = adapter;
        }
    }
}
