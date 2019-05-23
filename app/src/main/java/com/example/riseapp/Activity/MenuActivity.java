package com.example.riseapp.Activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.riseapp.Fragments.ContactaFragment;
import com.example.riseapp.Fragments.ForoFragment;
import com.example.riseapp.Fragments.InformatFragment.InformatFragment;
import com.example.riseapp.Fragments.JovalmaFragment;
import com.example.riseapp.Fragments.ProfileFragment;
import com.example.riseapp.Helper.AppPreferences;
import com.example.riseapp.Helper.LocaleHelper;
import com.example.riseapp.R;

public class MenuActivity extends AppCompatActivity {
private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.setLocale(getApplicationContext(), AppPreferences.getSettings().getString("lang","es"));
        setContentView(R.layout.menu);
        /**Locale locale = new Locale(AppPreferences.getSettings().getString("lang","es"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.menu);**/


        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
           fragment =  new InformatFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                    switch (item.getItemId()) {
                        case R.id.navigation_perfil:
                            fragment = new ProfileFragment();
                            break;

                        case R.id.navigation_mapa:
                            fragment = new ContactaFragment();
                            break;

                        case R.id.navigation_info:
                            fragment = new InformatFragment();
                            break;

                        case R.id.navigation_empresa:
                            fragment = new JovalmaFragment();
                            break;
                        case R.id.navigation_forum:
                            fragment = new ForoFragment();
                            break;
                    }

                    if (fragment != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    return true;
                }
            };

    @Override
    public void onBackPressed()
    {
        if(ForoFragment.browser != null) {
            if (ForoFragment.browser.canGoBack()) {
                ForoFragment.browser.goBack();
            }
        }
    }
}
