package com.example.riseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        BottomNavigationView bottomNav = findViewById(R.id.navigation);
//        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ProfileFragment()).commit();
    }
}

//    public void canviarIdioma(View view){
//        Intent change = new Intent(this, Idioma.class);
//        startActivity(change);
//    }
//
//    public void sortir(View view){
//        Intent tancar = new Intent(this, MainActivity.class);
//        startActivity(tancar);
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_perfil:
                            selectedFragment = new ProfileFragment();
                            break;
//                        case R.id.navigation_mapa:
//                            selectedFragment = new CalendariFragment();
//                            break;
//                        case R.id.navigation_info:
//                            selectedFragment = new ProgresFragment();
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
