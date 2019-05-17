package com.example.riseapp.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.riseapp.AppPreferences;
import com.example.riseapp.Constants;
import com.example.riseapp.Helper.LocaleHelper;
import com.example.riseapp.R;
import com.example.riseapp.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RegistroActivity extends AppCompatActivity {

    EditText etEmail, etPass, etPassRepe,etDateView, etName,etCity;
    private TextView tv_alternative,tv_tac;
    private Spinner spinnerGender;
    private CheckBox checkBoxConditions;
    //Spinner
    private String[] strings;
    private List<String> items;
    private ArrayAdapter<String> adapter;
    private DatePickerDialog datePickerDialog;
    private ImageButton btnEnglish, btnSpanish, btnCatalan;
    Button btnOk, btnCancel;
    private DocumentReference userRef;
    private int REQUEST_CODE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        etEmail = findViewById(R.id.et_registro_email);
        etPass = findViewById(R.id.et_registro_contra);
        etDateView = findViewById(R.id.et_dateOfBirth);
        btnOk=findViewById(R.id.bt_aceptaRegistoUser);
        btnCancel=findViewById(R.id.bt_cancelaRegistroUser);
        etPassRepe = findViewById(R.id.et_registro_contra_repe);
        etName=findViewById(R.id.et_registro_nombre_usuario);
        etCity=findViewById(R.id.et_registro_ciudad);
        checkBoxConditions=findViewById(R.id.chk_conditions);
        btnEnglish = findViewById(R.id.btnEnglish);
        btnSpanish = findViewById(R.id.btnSpanish);
        btnCatalan = findViewById(R.id.btnCatalan);
        tv_alternative= findViewById(R.id.tv_alternative);
        tv_tac=findViewById(R.id.tv_tac);


        //EMPIEZA SPINNER
        spinnerGender = findViewById(R.id.sp_gender_spinner);

        strings = getResources().getStringArray(R.array.genders);
        items = new ArrayList<>(Arrays.asList(strings));
        adapter = new ArrayAdapter<>(this, R.layout.spinner, items);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        //ACABA SPINNER
        //SELECCION IDIOMA
        String lang = AppPreferences.getSettings().getString("lang", "");

        try {
            assert lang != null;
            switch (lang) {
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
        //ACABA SELECCION IDIOMA

        //CALENDARIO DATE PICKER
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(RegistroActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        etDateView.setText(day + "/" + month + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);

        // ACABA CALENDARIO DATE PICKER
        etDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (LoginActivity.isKeyboardShown(v)) {
                    LoginActivity.hideKeyboard(v);
                }
                if (RegisterActivity.this.getCurrentFocus() != null) {
                    RegisterActivity.this.getCurrentFocus().clearFocus();
                }*/
                datePickerDialog.show();
            }
        });
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

    public void btnAcepta(View v){
        Log.d(RegistroActivity.class.getSimpleName(), "Acepta");
        try{
            String email = etEmail.getText().toString();
            String pass = etPass.getText().toString();
            String passRepe = etPassRepe.getText().toString();

            if(pass.equals(passRepe) && camposLlenos()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //CREACION USUARIO+SUBIR DATOS BDDD
                             userRef = Constants.getFirebaseFirestore().collection("users").document(Constants.getFirebaseAuth().getCurrentUser().getUid());
                            Calendar c = Calendar.getInstance();
                            String registerDate = DateFormat.format("dd-MM-yyyy", c).toString();
                            User user = new User(etEmail.getText().toString(), etName.getText().toString(), spinnerGender.getSelectedItem().toString(), etCity.getText().toString(), etDateView.getText().toString(), registerDate);
                            Constants.setCurrentUser(user);
                            userRef.set(user);

                             setResult(RESULT_OK);
                            finish();
                        } else{
                            Exception c = task.getException();
                            Log.d(RegistroActivity.class.getSimpleName(), c.getClass().getSimpleName());
                            if(c instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(RegistroActivity.this, R.string.user_exists_error, Toast.LENGTH_SHORT).show();
                            } else if(c instanceof FirebaseAuthWeakPasswordException){
                                Toast.makeText(RegistroActivity.this, R.string.user_weakpass_error, Toast.LENGTH_SHORT).show();
                            } else if(c instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(RegistroActivity.this, R.string.notValidEmail, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
            else if(!pass.equals(passRepe)){
                Toast.makeText(this, R.string.notEqualPassReg, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, R.string.emptyStringsError, Toast.LENGTH_SHORT).show();
            }
        }catch(IllegalArgumentException ex){
            Toast.makeText(this, R.string.emptyStringsError, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean camposLlenos() {
        if(isEmpty(etEmail)||isEmpty(etPass)||isEmpty(etPassRepe)||isEmpty(etCity)||
                isEmpty(etDateView)||isEmpty(etName)|| (spinnerGender.getSelectedItemPosition() == 0)||!checkBoxConditions.isChecked()){
            return false;
        }
        else{
            return true;
        }
    }
    private boolean isEmpty(EditText editText) {
        return (editText.getText().toString().trim().length() == 0);
    }
    public void btnCancel(View v){
        setResult(RESULT_CANCELED);
        finish();
        Log.d(RegistroActivity.class.getSimpleName(), "Cancel");
    }

    @Deprecated
    private void Login(String userText, String passText) {
        Constants.getFirebaseAuth().signInWithEmailAndPassword(userText, passText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("MISSATGE", "signInWithEmail:success");
                            FirebaseUser user = Constants.getFirebaseAuth().getCurrentUser();
                            Intent goMenu = new Intent(getBaseContext(), MenuActivity.class);
                            startActivity(goMenu);
                            finish();
                        } else {
                            Log.w("ErrorLogin", "signInWithEmail:failure", task.getException());

                            Toast.makeText(getBaseContext(), getResources().getString(R.string.loginIncorrecto), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);


        etEmail.setHint( context.getResources().getString(R.string.email));
        etPass.setHint( context.getResources().getString(R.string.password));
        btnOk.setText( context.getResources().getString(R.string.registrat));
        btnCancel.setText( context.getResources().getString(R.string.accedeix));
        etName.setHint(context.getResources().getString(R.string.nom_usuario));
        etPassRepe.setHint(context.getResources().getString(R.string.repeatPassword));
        etDateView.setHint(context.getResources().getString(R.string.data_de_naixement));
        etCity.setHint(context.getResources().getString(R.string.ciutat));
        checkBoxConditions.setText(context.getResources().getString(R.string.accepto_els_termes_i_condicions));
        tv_alternative.setText( context.getResources().getString(R.string.ja_tens_un_compte_accedeix_al_teu_compte));
        tv_tac.setText(context.getResources().getString(R.string.termes_i_condicions));

        //Save Spinner
        AppPreferences.getEditor().putInt("spinnerSelection", spinnerGender.getSelectedItemPosition());
        AppPreferences.getEditor().commit();

        //New Spinner
        strings = context.getResources().getStringArray(R.array.genders);
        items = new ArrayList<>(Arrays.asList(strings));
        adapter = new ArrayAdapter<>(this, R.layout.spinner, items);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        spinnerGender.setSelection(AppPreferences.getSettings().getInt("spinnerSelection", 0));

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
        langButton.startAnimation(AnimationUtils.loadAnimation(RegistroActivity.this, R.anim.rotate_lang_button));
    }
}