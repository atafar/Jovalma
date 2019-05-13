package com.example.riseapp;

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
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private ImageView appIcon;

    private long duracion = 2000;

//    private UsuariRepositori mUserRepository;

    private EditText etUsuari, etPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.d(MainActivity.class.getSimpleName(), Boolean.toString(FirebaseApp.initializeApp(this).isDefaultApp()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appIcon = findViewById(R.id.appIcon);

        //User database
//        mUserRepository = new UsuariRepositori(getApplication());

        etUsuari = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
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

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Intent goMenu = new Intent(getBaseContext(), Menu.class);
        startActivity(goMenu);
    }
}