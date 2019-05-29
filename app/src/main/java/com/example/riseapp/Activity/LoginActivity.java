package com.example.riseapp.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.riseapp.Helper.AppPreferences;
import com.example.riseapp.Helper.Constants;
import com.example.riseapp.Helper.LocaleHelper;
import com.example.riseapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.support.constraint.Constraints.TAG;


public class LoginActivity extends AppCompatActivity {

    private ImageView appIcon;
    private ImageButton btnEnglish, btnSpanish, btnCatalan;
    private Dialog myDialog;
    private long duracion = 2000;

//    private UsuariRepositori mUserRepository;

    private EditText etUsuari, etPassword;
    private Button btnLogin, btnRegister;
    private TextView tv_alternative,tv_tac,tv_passForgot;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.d(LoginActivity.class.getSimpleName(), Boolean.toString(FirebaseApp.initializeApp(this).isDefaultApp()));
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
        myDialog = new Dialog(this);
        tv_passForgot= findViewById(R.id.tv_contraOlvida);

        tv_passForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopUp_email();
            }
        });
        //IDIOMA AL INICIO O POR DEFECTO



        String lang = AppPreferences.getSettings().getString("lang", Locale.getDefault().getLanguage());

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
            Login(userText, passText);
        } catch (IllegalArgumentException ex) {
            Toast.makeText(this, getResources().getText(R.string.emptyStringsError), Toast.LENGTH_SHORT).show();
        }
    }

    private void Login(String userText, String passText) {
        AppPreferences.getEditor().putString("pass",passText);
        AppPreferences.getEditor().commit();
        mAuth.signInWithEmailAndPassword(userText, passText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("MISSATGE", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent goMenu = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(goMenu);


                            finish();
                        } else {
                            Log.w("ErrorLogin", "signInWithEmail:failure", task.getException());

                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginIncorrecto), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void btnRegistro(View v) {
        Intent intent = new Intent(this, RegistroActivity.class);
        AppPreferences.getEditor().putString("pass",etPassword.getText().toString());
        AppPreferences.getEditor().commit();
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Intent goMenu = new Intent(getBaseContext(), MenuActivity.class);
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
        langButton.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.rotate_lang_button));
    }
    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);


        etUsuari.setHint( context.getResources().getString(R.string.email));
        etPassword.setHint( context.getResources().getString(R.string.password));
        btnLogin.setText( context.getResources().getString(R.string.accedeix));
        btnRegister.setText( context.getResources().getString(R.string.registrat));
        tv_alternative.setText( context.getResources().getString(R.string.no_tens_un_compte_registra_t));
        tv_tac.setText(context.getResources().getString(R.string.termes_i_condicions));
        tv_passForgot.setText(context.getResources().getString(R.string.has_olvidado_tu_contrase_a));

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
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null){
            Intent goMenu = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(goMenu);
            finish();
        }

    }
    private void showPopUp_email() {
        final TextView txtClose, tv_wrong_email, tv_empty_email;
        Button save;
        final AutoCompleteTextView newEmail;

        myDialog.setContentView(R.layout.popup_email);
        save = myDialog.findViewById(R.id.btn_save_email);

        tv_empty_email = myDialog.findViewById(R.id.tv_empty);
        tv_wrong_email= myDialog.findViewById(R.id.tv_wrong_email);
        newEmail = myDialog.findViewById(R.id.newEmail);
        txtClose = myDialog.findViewById(R.id.txtClose_email);
        newEmail.setHint(getString(R.string.email));
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email_isValidated( tv_empty_email,tv_wrong_email, newEmail)) {
                    final String nEmail = newEmail.getText().toString();

                    Constants.getFirebaseAuth().sendPasswordResetEmail(nEmail);
                    displayToast(getString(R.string.restablecer_pass));
                    myDialog.dismiss();
                   }
            }
        });

        Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private boolean email_isValidated(TextView tv_empty_email, TextView tv_wrong_email, AutoCompleteTextView newEmail) {
        if(isEmpty(newEmail)){
            tv_empty_email.setVisibility(View.VISIBLE);
            tv_wrong_email.setVisibility(View.GONE);
            return false;
        }else{
            if(!isEmailValid(newEmail.getText().toString())){
                tv_empty_email.setVisibility(View.GONE);
                tv_wrong_email.setVisibility(View.VISIBLE);
                return false;
            }
            else{
                tv_empty_email.setVisibility(View.GONE);
                tv_wrong_email.setVisibility(View.GONE);

                return true;
            }
        }
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean isEmpty(EditText editText) {
        return (editText.getText().toString().trim().length() == 0);
    }
    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}