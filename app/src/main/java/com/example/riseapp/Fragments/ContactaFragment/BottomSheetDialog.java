package com.example.riseapp.Fragments.ContactaFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

            txtInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(titulo.equals(getString(R.string.telefono))){
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + info));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else if(titulo.equals("Web")){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(info));
                        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(browserIntent);
                    } else if (titulo.equals(getString(R.string.email)) ) {

                        startActivity(sendEmail(info));
                    }

                }
            });

        }

        return v;
    }

    public void redirigir() {

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
    public static Intent sendEmail(String email) {

        String[] TO = {email};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("message/rfc822");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return emailIntent;



    }
}