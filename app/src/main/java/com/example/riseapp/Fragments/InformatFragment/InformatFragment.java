package com.example.riseapp.Fragments.InformatFragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.riseapp.Helper.AppPreferences;
import com.example.riseapp.Helper.Constants;
import com.example.riseapp.Helper.GestionConexion;
import com.example.riseapp.Helper.LocaleHelper;
import com.example.riseapp.R;
import com.example.riseapp.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
import java.util.Objects;


public class InformatFragment extends Fragment {
    private DocumentReference userRef;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View activity = inflater.inflate(R.layout.informat_fragment, container, false);
        LocaleHelper.setLocale(activity.getContext(), AppPreferences.getSettings().getString("lang","es"));
       //DESCARGAR DATOS PRINCIPALES USUARIO

        userRef= Constants.getFirebaseFirestore().collection("users").document(Constants.getFirebaseAuth().getCurrentUser().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                Constants.setCurrentUser(user);

            }
        });

        userRef.collection("data").document("constants").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String string = String.valueOf(Objects.requireNonNull(documentSnapshot.getData()).get("hasProfilePicture"));
                Boolean hasProfilePicture = Boolean.valueOf(string);
                if(hasProfilePicture){
                    Log.i("Profile Image", "The user has a profile image");
                    if (Constants.getCurrentUserProfileImage() == null) {
                        /**Glide.with(activity.getContext()).load(Constants.getCurrentUserProfileImage())
                                .apply(RequestOptions.skipMemoryCacheOf(true))
                                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).circleCrop().into(imgProfile);**/
                        final File mFile = new File(activity.getContext().getFilesDir().getAbsolutePath(), "profile_icon.png");
                        Constants.getFirebaseStorage().getReference().child("users/" + Objects.requireNonNull(Constants.getFirebaseAuth().getCurrentUser()).getUid() + "/" + "profile_icon.png").getFile(mFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Log.i("updateProfileImages", " Loading...");

                                Constants.setCurrentUserProfileImage(Uri.fromFile(mFile));

                            }
                        });
                    }
                }else{
                    Log.i("Profile Image", " The user doesn't have a profile image");
                    Uri uri = Uri.parse("android.resource://com.example.riseapp/drawable/account_default");
                    Constants.setCurrentUserProfileImage(uri);

                }
            }
        });
        //DESCARGAR CONTACTOS
        GestionConexion con = new GestionConexion();
        con.setLang(AppPreferences.getSettings().getString("lang","es"));
        con.start();

        //con.getContactes();
// Create an instance of the tab layout from the view.
        TabLayout tabLayout = activity.findViewById(R.id.tab_layout);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText("1"));
        tabLayout.addTab(tabLayout.newTab().setText("2"));
        tabLayout.addTab(tabLayout.newTab().setText("3"));

        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Use PagerAdapter_Informat to manage page views in fragments.
        // Each page is represented by its own fragment.
        final ViewPager viewPager = activity.findViewById(R.id.pager);
        final PagerAdapter_Informat adapter = new PagerAdapter_Informat
                (getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });
        return activity;
    }
}