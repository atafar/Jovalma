package com.example.riseapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.riseapp.Fragments.ContactaFragment;
import com.example.riseapp.Fragments.ForoFragment;
import com.example.riseapp.Fragments.InformatFragment;
import com.example.riseapp.Fragments.JovalmaFragment;
import com.example.riseapp.Fragments.ProfileFragment;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new InformatFragment()).commit();
    }
}

//    public void canviarIdioma(View view){
//        Intent change = new Intent(this, Idioma.class);
//        startActivity(change);
//    }
//
//    public void sortir(View view){
//        Intent tancar = new Intent(this, LoginActivity.class);
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

                        case R.id.navigation_mapa:
                            selectedFragment = new ContactaFragment();
                            break;

                        case R.id.navigation_info:
                            selectedFragment = new InformatFragment();

                        case R.id.navigation_empresa:
                            selectedFragment = new JovalmaFragment();

                        case R.id.navigation_forum:
                            selectedFragment = new ForoFragment();
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
