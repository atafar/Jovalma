package com.example.riseapp.Fragments.InformatFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.riseapp.Helper.AppPreferences;
import com.example.riseapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment3_Informat extends Fragment {

    public static WebView browser;

    public TabFragment3_Informat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View activity = inflater.inflate(R.layout.fragment_tab_fragment3__informat, container, false);

        browser = (WebView) activity.findViewById(R.id.webView);

        //Habilitamos JavaScript
        browser.getSettings().setJavaScriptEnabled(true);


        browser.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // Cargamos la web
        if (AppPreferences.getSettings().getString("lang", "es").equals("es")) {
            browser.loadUrl("file:///android_asset/web/RiscTCAES.html");
        } else if (AppPreferences.getSettings().getString("lang", "ca").equals("ca")) {
            browser.loadUrl("file:///android_asset/web/RiscTCACA.html");
        } else {
            browser.loadUrl("file:///android_asset/web/RiscTCAEN.html");
        }


        return activity;
    }

}
