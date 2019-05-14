package com.example.riseapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RegistroUsuario extends AppCompatActivity {

    EditText mEmail, mPass, mPassRepe,mDateView;
    private Spinner spinnerGender;
    //Spinner
    private String[] strings;
    private List<String> items;
    private ArrayAdapter<String> adapter;
    private DatePickerDialog datePickerDialog;

    Button btnOk, btnCancel;
    private int REQUEST_CODE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        mEmail = findViewById(R.id.et_registro_email);
        mPass = findViewById(R.id.et_registro_contra);
        mDateView = findViewById(R.id.et_dateOfBirth);
        mPassRepe = findViewById(R.id.et_registro_contra_repe);
        //EMPIEZA SPINNER
        spinnerGender = findViewById(R.id.sp_gender_spinner);

        strings = getResources().getStringArray(R.array.genders);
        items = new ArrayList<>(Arrays.asList(strings));
        adapter = new ArrayAdapter<>(this, R.layout.spinner, items);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        //ACABA SPINNER

        //CALENDARIO DATE PICKER
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(RegistroUsuario.this,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        mDateView.setText(day + "/" + month + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);

        // ACABA CALENDARIO DATE PICKER
        mDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (MainActivity.isKeyboardShown(v)) {
                    MainActivity.hideKeyboard(v);
                }
                if (RegisterActivity.this.getCurrentFocus() != null) {
                    RegisterActivity.this.getCurrentFocus().clearFocus();
                }*/
                datePickerDialog.show();
            }
        });
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