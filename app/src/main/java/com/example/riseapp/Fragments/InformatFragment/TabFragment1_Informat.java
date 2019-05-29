/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
public class TabFragment1_Informat extends Fragment {
    public static WebView browser;
    public TabFragment1_Informat() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View activity=inflater.inflate(R.layout.tab_fragment1_informat, container, false);
        // Inflate the layout for this fragment.
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
        if (AppPreferences.getSettings().getString("lang", "es").equals("es")) {
            browser.loadUrl("file:///android_asset/web/TipusTCAES.html");
        } else if (AppPreferences.getSettings().getString("lang", "ca").equals("ca")) {
            browser.loadUrl("file:///android_asset/web/TipusTCACA.html");
        } else {
            browser.loadUrl("file:///android_asset/web/TipusTCAEN.html");
        }

        return activity;
    }

}
