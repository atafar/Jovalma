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

package com.example.riseapp.Fragments.JovalmaFragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.riseapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment3_Jovalma extends Fragment {


    public TabFragment3_Jovalma() {
        // Required empty public constructor
    }

    private ImageView btnGmail, btnTwitter, btnInsta, btnTele;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View activity = inflater.inflate(R.layout.tab_fragment3_quisom, container, false);
        btnGmail = activity.findViewById(R.id.btnGmail);
        btnInsta = activity.findViewById(R.id.btnInsta);
        btnTele = activity.findViewById(R.id.btnTele);
        btnTwitter = activity.findViewById(R.id.btnTwitter);

        btnInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(newInstagramProfileIntent(activity.getContext().getPackageManager(), "https://www.instagram.com/jovalmacoop/"));
            }
        });
        btnGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(sendEmail());

            }
        });
        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://jovalmacoop.weebly.com/"));
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(browserIntent);
            }
        });
        btnTele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "93 735 89 22"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return activity;
    }

    public static Intent newInstagramProfileIntent(PackageManager pm, String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() - 1);
                }
                final String username = url.substring(url.lastIndexOf("/") + 1);
                // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
                intent.setData(Uri.parse("http://instagram.com/_u/" + username));
                intent.setPackage("com.instagram.android");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                return intent;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        intent.setData(Uri.parse(url));
        return intent;
    }

    public static Intent sendEmail() {

        String[] TO = {"jovalmacoop@gmail.com"};
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
