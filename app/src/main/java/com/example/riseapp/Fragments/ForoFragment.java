package com.example.riseapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.riseapp.Helper.AppPreferences;
import com.example.riseapp.Helper.LocaleHelper;
import com.example.riseapp.R;


public class ForoFragment extends Fragment {

    public static WebView browser;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View activity = inflater.inflate(R.layout.foro_fragment, container, false);
        LocaleHelper.setLocale(activity.getContext(), AppPreferences.getSettings().getString("lang","es"));


        // Definimos el webView
        browser=(WebView)activity.findViewById(R.id.webView);

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
        browser.loadUrl("http://fororiseapp.foroactivo.com");

        return activity;
    }


}