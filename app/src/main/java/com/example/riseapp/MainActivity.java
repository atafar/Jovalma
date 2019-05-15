package com.example.riseapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.riseapp.Helper.LocaleHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private ImageView appIcon;
    private ImageButton btnEnglish, btnSpanish, btnCatalan;

    private long duracion = 2000;

//    private UsuariRepositori mUserRepository;

    private EditText etUsuari, etPassword;
    private Button btnLogin, btnRegister;
    private TextView tv_alternative,tv_tac;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.d(MainActivity.class.getSimpleName(), Boolean.toString(FirebaseApp.initializeApp(this).isDefaultApp()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //DECLARACIONES
        //App Preferences
        AppPreferences.setSettings(getSharedPreferences("Ajustes", MODE_PRIVATE));
        AppPreferences.setEditor(AppPreferences.getSettings().edit());

        appIcon = findViewById(R.id.appIcon);

        //User database
//        mUserRepository = new UsuariRepositori(getApplication());

        etUsuari = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnAccedeix);
        btnRegister=findViewById(R.id.btnRegistrar);
        btnEnglish = findViewById(R.id.btnEnglish);
        btnSpanish = findViewById(R.id.btnSpanish);
        btnCatalan = findViewById(R.id.btnCatalan);
        tv_alternative= findViewById(R.id.tv_alternative);
        tv_tac=findViewById(R.id.tv_tac);
        mAuth = FirebaseAuth.getInstance();

        //IDIOMA AL INICIO O POR DEFECTO

        AppPreferences.getEditor().putString("lang", Locale.getDefault().getLanguage());
        AppPreferences.getEditor().commit();


        String lang = AppPreferences.getSettings().getString("lang", "null");

        try {
            switch (Objects.requireNonNull(lang)) {
                case "en":
                    btnEnglish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background_selected));
                    btnSpanish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
                    btnCatalan.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
                    btnEnglish.setEnabled(false);
                    btnSpanish.setEnabled(true);
                    btnCatalan.setEnabled(true);
                    updateView(lang);
                    break;
                case "es":
                    btnEnglish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
                    btnSpanish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background_selected));
                    btnCatalan.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
                    btnEnglish.setEnabled(true);
                    btnSpanish.setEnabled(false);
                    btnCatalan.setEnabled(true);
                    updateView(lang);
                    break;
                case "ca":
                    btnEnglish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
                    btnSpanish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
                    btnCatalan.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background_selected));
                    btnEnglish.setEnabled(true);
                    btnSpanish.setEnabled(true);
                    btnCatalan.setEnabled(false);
                    updateView(lang);
                    break;
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        //ACABA SELECCION IDIOMA INICIAL


        //LISTENERS BOTONES
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectEnglish();
            }
        });

        btnSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSpanish();
            }
        });

        btnCatalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCatalan();
            }
        });
    }

    public void goToMenu(View view) {
        try {
            String userText = etUsuari.getText().toString();
            String passText = etPassword.getText().toString();
            mAuth.signInWithEmailAndPassword(userText, passText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("MISSATGE", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent goMenu = new Intent(getBaseContext(), Menu.class);
                                startActivity(goMenu);
                            } else {
                                Log.w("ErrorLogin", "signInWithEmail:failure", task.getException());

                                Toast.makeText(getBaseContext(), getResources().getString(R.string.loginIncorrecto), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        } catch (IllegalArgumentException ex) {
            Toast.makeText(this, getResources().getText(R.string.emptyStringsError), Toast.LENGTH_SHORT).show();
        }
    }

    public void btnRegistro(View v) {
        Intent intent = new Intent(this, RegistroUsuario.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Intent goMenu = new Intent(getBaseContext(), Menu.class);
                startActivity(goMenu);
            }
        }
    }
    public void selectEnglish() {
        btnEnglish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background_selected));
        btnSpanish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
        btnCatalan.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
        btnEnglish.setEnabled(false);
        btnSpanish.setEnabled(true);
        btnCatalan.setEnabled(true);

        AppPreferences.getEditor().putString("lang", "en");
        AppPreferences.getEditor().commit();
        updateView("en");
        spinLanguage(btnEnglish);
    }

    public void selectSpanish() {
        btnEnglish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
        btnSpanish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background_selected));
        btnCatalan.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
        btnEnglish.setEnabled(true);
        btnSpanish.setEnabled(false);
        btnCatalan.setEnabled(true);

        AppPreferences.getEditor().putString("lang", "es");
        AppPreferences.getEditor().commit();
        updateView("es");
        spinLanguage(btnSpanish);
    }

    public void selectCatalan() {
        btnEnglish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
        btnSpanish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
        btnCatalan.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background_selected));
        btnEnglish.setEnabled(true);
        btnSpanish.setEnabled(true);
        btnCatalan.setEnabled(false);

        AppPreferences.getEditor().putString("lang", "ca");
        AppPreferences.getEditor().commit();
        updateView("ca");
        spinLanguage(btnCatalan);
    }
    public void spinLanguage(final ImageButton langButton) {
        langButton.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_lang_button));
    }
    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);


        etUsuari.setHint( context.getResources().getString(R.string.email));
        etPassword.setHint( context.getResources().getString(R.string.password));
        btnLogin.setText( context.getResources().getString(R.string.accedeix));
        btnRegister.setText( context.getResources().getString(R.string.registrat));
        tv_alternative.setText( context.getResources().getString(R.string.no_tens_un_compte_registra_t));
        tv_tac.setText(context.getResources().getString(R.string.termes_i_condicions));

    }

    @Override
    public  void onResume() {
        super.onResume();
        //IDIOMA AL INICIO O POR DEFECTO

        AppPreferences.getEditor().putString("lang", Locale.getDefault().getLanguage());
        AppPreferences.getEditor().commit();


        String lang = AppPreferences.getSettings().getString("lang", "null");

        try {
            switch (Objects.requireNonNull(lang)) {
                case "en":
                    btnEnglish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background_selected));
                    btnSpanish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
                    btnCatalan.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
                    btnEnglish.setEnabled(false);
                    btnSpanish.setEnabled(true);
                    btnCatalan.setEnabled(true);
                    updateView(lang);
                    break;
                case "es":
                    btnEnglish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
                    btnSpanish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background_selected));
                    btnCatalan.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
                    btnEnglish.setEnabled(true);
                    btnSpanish.setEnabled(false);
                    btnCatalan.setEnabled(true);
                    updateView(lang);
                    break;
                case "ca":
                    btnEnglish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
                    btnSpanish.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background));
                    btnCatalan.setBackground(ContextCompat.getDrawable(this, R.drawable.lang_button_background_selected));
                    btnEnglish.setEnabled(true);
                    btnSpanish.setEnabled(true);
                    btnCatalan.setEnabled(false);
                    updateView(lang);
                    break;
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        //ACABA SELECCION IDIOMA INICIAL
    }
    /**@Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Intent goMenu = new Intent(getBaseContext(), Menu.class);
        startActivity(goMenu);
    }**/
}