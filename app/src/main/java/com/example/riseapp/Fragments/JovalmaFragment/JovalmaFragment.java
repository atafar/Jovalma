package com.example.riseapp.Fragments.JovalmaFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.riseapp.Fragments.InformatFragment.PagerAdapter_Informat;
import com.example.riseapp.Helper.AppPreferences;
import com.example.riseapp.Helper.LocaleHelper;
import com.example.riseapp.R;


public class JovalmaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View activity = inflater.inflate(R.layout.jovalma_fragment, container, false);
        LocaleHelper.setLocale(activity.getContext(), AppPreferences.getSettings().getString("lang","es"));
        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = activity.findViewById(R.id.tab_layout);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText("1"));
        tabLayout.addTab(tabLayout.newTab().setText("2"));
        tabLayout.addTab(tabLayout.newTab().setText("3"));

        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Use PagerAdapter_Informat to manage page views in fragments.
        // Each page is represented by its own fragment.
        final ViewPager viewPager = activity.findViewById(R.id.pager);
        final PagerAdapter_Jovalma adapter = new PagerAdapter_Jovalma
                (getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });
        return activity;
    }
}