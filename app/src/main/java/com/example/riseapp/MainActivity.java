package com.example.riseapp;

import android.arch.lifecycle.LiveData;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView appIcon;

    private ObjectAnimator iconAnimator;

    private long duracion = 2000;

    private AnimatorSet animatorSet;

    //private UsuariRepositori mUserRepository;

    private EditText etUsuari, etPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d(MainActivity.class.getSimpleName(),Boolean.toString(FirebaseApp.initializeApp(this).isDefaultApp()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appIcon = findViewById(R.id.appIcon);

        iconAnimator = ObjectAnimator.ofFloat(appIcon, "rotation", 0f, 360f);
        iconAnimator.setDuration(duracion);
        animatorSet = new AnimatorSet();
        animatorSet.play(iconAnimator);
        animatorSet.start();

        //User database
        //mUserRepository = new UsuariRepositori(getApplication());

        etUsuari = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
    }

    public void goToCalendar(View view) {
        try {
            String userText = etUsuari.getText().toString();
            String passText = etPassword.getText().toString();
            mAuth.signInWithEmailAndPassword(userText, passText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent goCalendar = new Intent(getBaseContext(), Menu.class);
                                startActivity(goCalendar);
                            } else {
                                Toast.makeText(getBaseContext(), "El usuario o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        } catch (IllegalArgumentException ex){
            Toast.makeText(this, getResources().getText(R.string.emptyStringsError), Toast.LENGTH_SHORT).show();
        }
    }

    public void btnRegistro(View v){
        Intent intent = new Intent(this, RegistroUsuario.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Intent goCalendar = new Intent(getBaseContext(), Menu.class);
                startActivity(goCalendar);
            }
        }
    }
}