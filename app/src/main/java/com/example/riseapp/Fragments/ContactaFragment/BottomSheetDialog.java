package com.example.riseapp.Fragments.ContactaFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.riseapp.R;


public class BottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    public TextView txtTitulo, txtInfo;
    String titulo, info;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_dialog, container, false);
        txtTitulo = v.findViewById(R.id.txtTitulo);
        txtInfo = v.findViewById(R.id.txtInformacion);

        if(getArguments() != null){
            titulo = getArguments().getString("titulo");
            txtTitulo.setText(titulo);
            info = getArguments().getString("info");
            txtInfo.setText(info);

        }

        return v;
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}