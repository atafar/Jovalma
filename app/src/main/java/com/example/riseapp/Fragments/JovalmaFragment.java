package com.example.riseapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.riseapp.AppPreferences;
import com.example.riseapp.Helper.LocaleHelper;
import com.example.riseapp.R;


public class JovalmaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View activity = inflater.inflate(R.layout.jovalma_fragment, container, false);
        LocaleHelper.setLocale(activity.getContext(), AppPreferences.getSettings().getString("lang","es"));
        return activity;
    }
}