package com.example.riseapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegistroUsuario extends AppCompatActivity {

    EditText mEmail, mPass, mPassRepe;
    Button btnOk, btnCancel;
    private int REQUEST_CODE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        mEmail = findViewById(R.id.et_registro_email);
        mPass = findViewById(R.id.et_registro_contra);
        mPassRepe = findViewById(R.id.et_registro_contra_repe);
    }

    public void btnAcepta(View v){
        Log.d(RegistroUsuario.class.getSimpleName(), "Acepta");
        try{
            String email = mEmail.getText().toString();
            String pass = mPass.getText().toString();
            String passRepe = mPassRepe.getText().toString();

            if(pass.equals(passRepe)){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            setResult(RESULT_OK);
                            finish();
                        } else{
                            Exception c = task.getException();
                            Log.d(RegistroUsuario.class.getSimpleName(), c.getClass().getSimpleName());
                            if(c instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(RegistroUsuario.this, R.string.user_exists_error, Toast.LENGTH_SHORT).show();
                            } else if(c instanceof FirebaseAuthWeakPasswordException){
                                Toast.makeText(RegistroUsuario.this, R.string.user_weakpass_error, Toast.LENGTH_SHORT).show();
                            } else if(c instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(RegistroUsuario.this, R.string.notValidEmail, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
            else{
                Toast.makeText(this, R.string.notEqualPassReg, Toast.LENGTH_SHORT).show();
            }
        }catch(IllegalArgumentException ex){
            Toast.makeText(this, R.string.emptyStringsError, Toast.LENGTH_SHORT).show();
        }
    }

    public void btnCancel(View v){
        setResult(RESULT_CANCELED);
        finish();
        Log.d(RegistroUsuario.class.getSimpleName(), "Cancel");
    }
}