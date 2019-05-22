package com.example.riseapp.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.riseapp.AppPreferences;
import com.example.riseapp.Constants;
import com.example.riseapp.Fragments.ForoFragment;
import com.example.riseapp.Helper.LocaleHelper;
import com.example.riseapp.R;
import com.example.riseapp.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.support.constraint.Constraints.TAG;

public class EditarPerfilActivity extends AppCompatActivity {
    private EditText etEmail, etPass,etDateView;
    private  TextView etName, etCity,txtCambiarFoto;
    private ImageView imgProfile;
    private Spinner spinnerGender;
    public static final int GET_FROM_GALLERY = 3;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button btnTornaPerfil;
    //Spinner
    private String[] strings;
    private List<String> items;
    private ArrayAdapter<String> adapter;
    private DatePickerDialog datePickerDialog;
    private Dialog myDialog;


    public void onBackPressed()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.setLocale(getApplicationContext(), AppPreferences.getSettings().getString("lang","es"));
        setContentView(R.layout.activity_editar_perfil);
        etEmail = findViewById(R.id.et_registro_email);
        etPass = findViewById(R.id.et_password);
        txtCambiarFoto=findViewById(R.id.tv_cambiarFoto);
        etDateView = findViewById(R.id.et_dateOfBirth);
        etName=findViewById(R.id.tv_Nombre);
        etCity=findViewById(R.id.tv_ciudad);
        imgProfile=findViewById(R.id.avatar);
        myDialog = new Dialog(this);
        etEmail.setInputType(0);
        etName.setInputType(0);
//        etCity.setInputType(0);
        etPass.setInputType(0);
        btnTornaPerfil=findViewById(R.id.btnTornarPerfil);
        User user=Constants.getCurrentUser();
        etEmail.setText(user.getEmail().toString());
        etName.setText(user.getName().toString());
        etCity.setText(user.getCity().toString());
        etPass.setText("**********");
        etDateView.setText(user.getDate().toString());

        btnTornaPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                Constants.getCurrentUser().setGender( spinnerGender.getSelectedItem().toString());
                Constants.getFirebaseFirestore().collection("users").document(Objects.requireNonNull(Constants.getFirebaseAuth().getCurrentUser()).getUid()).set(Constants.getCurrentUser()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {

                        }
                    }
                });
                finish();
            }
        });

        if(Constants.getCurrentUserProfileImage()!=null){
            Glide.with(getApplicationContext()).load(Constants.getCurrentUserProfileImage())
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).circleCrop().into(imgProfile);
        }

        etEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txtCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp_img();
            }
        });
        etName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp_name();
            }
        });
        etCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp_city();
            }
        });
        etPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp_pass();
            }
        });
        etEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp_email();
            }
        });


        //EMPIEZA SPINNER
        spinnerGender = (Spinner) findViewById(R.id.sp_gender_spinner);

        strings = getResources().getStringArray(R.array.genders);
        items = new ArrayList<>(Arrays.asList(strings));
        adapter = new ArrayAdapter<>(this, R.layout.spinner, items);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);


        if(Constants.getCurrentUser().getGender().equals("Hombre")||
                Constants.getCurrentUser().getGender().equals("Home")|| Constants.getCurrentUser().getGender().equals("Man")){
            AppPreferences.getEditor().putInt("spinnerSelection",1);
            AppPreferences.getEditor().commit();
        }
        else if(Constants.getCurrentUser().getGender().equals("Dona")||
                Constants.getCurrentUser().getGender().equals("Mujer")|| Constants.getCurrentUser().getGender().equals("Women")){
            AppPreferences.getEditor().putInt("spinnerSelection",2);
            AppPreferences.getEditor().commit();
        }
        else{
            AppPreferences.getEditor().putInt("spinnerSelection",0);
            AppPreferences.getEditor().commit();
        }


        spinnerGender.setSelection(AppPreferences.getSettings().getInt("spinnerSelection",0));
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               AppPreferences.getEditor().putInt("spinnerSelection",position);
               AppPreferences.getEditor().commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //ACABA SPINNER

    //CALENDARIO DATE PICKER
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(EditarPerfilActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        etDateView.setText(day + "/" + (month+1) + "/" + year);
                        Constants.getCurrentUser().setDate(day + "/" + (month+1) + "/" + year);
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        Constants.getFirebaseFirestore().collection("users").document(Objects.requireNonNull(auth.getCurrentUser()).getUid()).set(Constants.getCurrentUser()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    displayToast(getString(R.string.data_actualitzada));
                                } else {
                                    displayToast(getString(R.string.error_actualitzardata));
                                }
                            }
                        });
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
        etDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog.show();
            }
        });

        // ACABA CALENDARIO DATE PICKER



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

                    // Get auth credentials from the user for re-authentication
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(Constants.getCurrentUser().getEmail(),AppPreferences.getSettings().getString("pass","null")); // Current Login Credentials \\
                    // Prompt the user to re-provide their sign-in credentials
                    Constants.getFirebaseAuth().getCurrentUser().reauthenticate(credential)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Constraints", "re-auth");
                                    Constants.getFirebaseAuth().getCurrentUser().updateEmail(nEmail)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User email address updated.");
                                                        Constants.getCurrentUser().setEmail(newEmail.getText().toString());
                                                    }
                                                }
                                            });
                                }
                            });
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    Constants.getFirebaseFirestore().collection("users").document(Objects.requireNonNull(auth.getCurrentUser()).getUid()).set(Constants.getCurrentUser()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                etEmail.setText(nEmail);
                                myDialog.dismiss();
                                displayToast(getString(R.string.correu_actualitzat));
                            } else {

                                displayToast(getString(R.string.no_actualitzatCorreu));
                            }
                        }
                    });







                }
            }
        });

        Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private boolean email_isValidated(TextView tv_empty_email, TextView tv_wrong_email, AutoCompleteTextView newEmail) {
        if(isEmpty(newEmail)){
            tv_empty_email.setVisibility(View.VISIBLE);
            return false;
        }else{
            if(!isEmailValid(newEmail.getText().toString())){
                tv_empty_email.setVisibility(View.INVISIBLE);
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

    public void showPopUp_name() {
        final TextView txtClose, tv_wrong_name, tv_empty_name;
        Button save;
        final AutoCompleteTextView newName;

        myDialog.setContentView(R.layout.popup_name);
        save = myDialog.findViewById(R.id.btn_save_name);

        tv_empty_name = myDialog.findViewById(R.id.tv_empty_name);
        newName = myDialog.findViewById(R.id.newName);
        txtClose = myDialog.findViewById(R.id.txtClose_name);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name_isValidated( tv_empty_name, newName)) {
                    final String oldName = Constants.getCurrentUser().getName();
                    Constants.getCurrentUser().setName(newName.getText().toString());
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    Constants.getFirebaseFirestore().collection("users").document(Objects.requireNonNull(auth.getCurrentUser()).getUid()).set(Constants.getCurrentUser()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {




                                etName.setText(newName.getText().toString());
                                myDialog.dismiss();
                                displayToast(getString(R.string.nom_actualitzat));
                            } else {
                                Constants.getCurrentUser().setCity(oldName);
                                displayToast(getString(R.string.noPassUpdate));
                            }
                        }
                    });
                }
            }
        });

        Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }
    public void showPopUp_img() {
        TextView txtClose;
        Button gallery, camera;

        myDialog.setContentView(R.layout.popup_imageprofile);
        txtClose = myDialog.findViewById(R.id.txtClose_img);
        gallery = myDialog.findViewById(R.id.image_gallery);
        camera = myDialog.findViewById(R.id.take_picture);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                myDialog.dismiss();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);
                myDialog.dismiss();
            }
        });

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
    public void showPopUp_pass() {
        final TextView txtClose, tv_empty, tv_wrong_password_match;
        Button save;
        final AutoCompleteTextView oldPassword, newPassword, repeatNewPassword;

        myDialog.setContentView(R.layout.popup_password);

        oldPassword = myDialog.findViewById(R.id.oldPassword);
        newPassword = myDialog.findViewById(R.id.newPassword);
        repeatNewPassword = myDialog.findViewById(R.id.repeatNewPassword);

        tv_empty = myDialog.findViewById(R.id.tv_empty);
        tv_wrong_password_match = myDialog.findViewById(R.id.tv_wrong_password_match);


        txtClose = myDialog.findViewById(R.id.txtClose_pass);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        save = myDialog.findViewById(R.id.btn_save_pass);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password_isValidated(tv_empty, tv_wrong_password_match, oldPassword, newPassword, repeatNewPassword)) {
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(Objects.requireNonNull(Constants.getFirebaseAuth().getCurrentUser().getEmail()), oldPassword.getText().toString());

                    Constants.getFirebaseAuth().getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Constants.getFirebaseAuth().getCurrentUser().updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            myDialog.dismiss();
                                            displayToast(getString(R.string.actualitzar_contra));
                                        } else {
                                            Log.i(TAG, "Error password not updated");
                                        }
                                    }
                                });
                            } else {
                                displayToast(getString(R.string.contra_incorrecta));
                            }
                        }
                    });
                }
            }
        });

        Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }
    public void showPopUp_city() {
        final TextView txtClose, tv_empty_city;
        Button save;
        final AutoCompleteTextView newCity;

        myDialog.setContentView(R.layout.popup_city);
        txtClose = myDialog.findViewById(R.id.txtClose_city);
        save = myDialog.findViewById(R.id.btn_save_city);
        tv_empty_city = myDialog.findViewById(R.id.tv_empty_city);
        newCity = myDialog.findViewById(R.id.newCity);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city_isValidated(tv_empty_city, newCity)) {
                    final String oldCity = Constants.getCurrentUser().getCity();
                    Constants.getCurrentUser().setCity(newCity.getText().toString());
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    Constants.getFirebaseFirestore().collection("users").document(Objects.requireNonNull(auth.getCurrentUser()).getUid()).set(Constants.getCurrentUser()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                etCity.setText(newCity.getText().toString());
                                myDialog.dismiss();
                                displayToast(getString(R.string.ciutat_update));
                            } else {
                                Constants.getCurrentUser().setCity(oldCity);
                                displayToast(getString(R.string.city_noupdate));
                            }
                        }
                    });
                }
            }
        });

        Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }
    public boolean name_isValidated( TextView tv_empty_name, EditText newName) {
        boolean anyChanges = false;
        if (!name_validateEmpty(tv_empty_name, newName)) {
            tv_empty_name.setVisibility(View.VISIBLE);
            return false;
        }
        return  true;
    }
    public boolean name_validateEmpty(TextView tv_empty_name, EditText newName) {
        if (isEmpty(newName)) {
            return false;
        }
        tv_empty_name.setVisibility(View.GONE);
        return true;
    }


    public boolean password_isValidated(TextView tv_empty, TextView tv_wrong_password_match, EditText oldPassword, EditText newPassword, EditText repeatNewPassword) {
        boolean anyChanges = false;

        if (!password_validateEmpty(tv_empty, oldPassword, newPassword, repeatNewPassword)) {
            tv_empty.setVisibility(View.VISIBLE);
            return false;
        } else {
            if (!newPassword.getText().toString().equals(repeatNewPassword.getText().toString())) {
                tv_wrong_password_match.setVisibility(View.VISIBLE);
                anyChanges = true;
            } else {
                tv_wrong_password_match.setVisibility(View.GONE);
            }
            return !anyChanges;
        }
    }
    public boolean password_validateEmpty(TextView tv_empty, EditText oldPassword, EditText newPassword, EditText repeatNewPassword) {
        if (isEmpty(oldPassword) |
                isEmpty(newPassword) |
                isEmpty(repeatNewPassword)) {
            return false;
        }
        tv_empty.setVisibility(View.GONE);
        return true;
    }
    public boolean city_isValidated(TextView tv_empty_city, EditText newCity) {
        if (!city_validateEmpty(tv_empty_city, newCity)) {
            tv_empty_city.setVisibility(View.VISIBLE);
            return false;
        } else {
            return true;
        }
    }

    public boolean city_validateEmpty(TextView tv_empty_city, EditText newCity) {
        if (isEmpty(newCity)) {
            return false;
        }
        tv_empty_city.setVisibility(View.GONE);
        return true;
    }
    private boolean isEmpty(EditText editText) {
        return (editText.getText().toString().trim().length() == 0);
    }
    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Constants.getFirebaseStorage().getReference().child("users/" + Constants.getFirebaseAuth().getCurrentUser().getUid() + "/" + "profile_icon" + ".png").putFile(Objects.requireNonNull(data.getData())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Constants.setCurrentUserProfileImage(taskSnapshot.getUploadSessionUri());
                    Map<String, Object> constants = new HashMap<>();
                    constants.put("hasProfilePicture", true);
                    Constants.getFirebaseFirestore().collection("users").document(Objects.requireNonNull(Constants.getFirebaseAuth().getCurrentUser()).getUid()).collection("data")
                            .document("constants").set(constants).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Profile picture", "Upload Success!");
                        }
                    });
                    try {
                        Uri imageUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        bitmapToFile(bitmap);
                        Glide.with(getApplicationContext()).load(Constants.getCurrentUserProfileImage())
                                .apply(RequestOptions.skipMemoryCacheOf(true))
                                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).circleCrop().into(imgProfile);
                        Log.d("Local image", "Saved!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Profile picture", "Upload Failed!");
                }
            });
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            final Bitmap imageBitmap = (Bitmap) Objects.requireNonNull(extras).get("data");
            bitmapToFile(imageBitmap);
            Constants.getFirebaseStorage().getReference().child("users/" + Constants.getFirebaseAuth().getCurrentUser().getUid() + "/" + "profile_icon" + ".png").putFile(Objects.requireNonNull(Constants.getCurrentUserProfileImage())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Constants.setCurrentUserProfileImage(taskSnapshot.getUploadSessionUri());
                    Map<String, Object> constants = new HashMap<>();
                    constants.put("hasProfilePicture", true);
                    Constants.getFirebaseFirestore().collection("users").document(Objects.requireNonNull(Constants.getFirebaseAuth().getCurrentUser()).getUid()).collection("data")
                            .document("constants").set(constants).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Profile picture", "Upload Success!");
                        }
                    });
                    bitmapToFile(imageBitmap);
                    Glide.with(getApplicationContext()).load(Constants.getCurrentUserProfileImage())
                            .apply(RequestOptions.skipMemoryCacheOf(true))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).circleCrop().into(imgProfile);
                    Log.d("Local image", "Saved!");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Profile picture", "Upload Failed!");
                }
            });
        }
    }
    public File bitmapToFile(Bitmap bmp) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArr = bos.toByteArray();
            bos.flush();
            bos.close();

            FileOutputStream fos = openFileOutput("profile_icon.png", Context.MODE_PRIVATE);
            fos.write(bArr);
            fos.flush();
            fos.close();

            File mFile = new File(getFilesDir().getAbsolutePath(), "profile_icon.png");
            Constants.setCurrentUserProfileImage(Uri.fromFile(mFile));
            return mFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
