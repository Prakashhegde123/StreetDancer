package com.example.street_dancer_beta10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.street_dancer_beta10.Segments.Home.HomeFragment;
import com.example.street_dancer_beta10.Segments.Notification.NotificationFragment;
import com.example.street_dancer_beta10.Segments.Profile.ProfileFragment;
import com.example.street_dancer_beta10.Segments.Search.SearchFrgment;
import com.example.street_dancer_beta10.Segments.Upload.UploadFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {





    public static FragmentManager fragmentManager;

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment = null;

    // "stack" IS USED TO KEEP TRACK OF ALL THE FRAGMENTS THAT WERE VISITED BEFORE REACHING THE PRESENT FRAGMENT
    // IT CONTAINS "" OF MENU-ITEMS IN BOTTOM NAVIGATION
    public List<Integer> stack = new ArrayList<>();

    // "isBackPressed" IS USED TO CHECK WEATHER BACK-BUTTON WAS PRESSED OR NOT
    private boolean isBackPressed = false;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (stack.size() > 1) {

            // SET "isBackPressed" TO TRUE
            isBackPressed = true;

            //Toast.makeText(getApplicationContext(), "after: " + stack.size(), Toast.LENGTH_SHORT).show();
            // REMOVE THE LAST ITEM IN THE LIST SINCE LAST ELEMENT IS THE PRESENT ACTIVE FRAGMENT
            stack.remove(stack.size()-1);

            // CALL THE "onNavigationItemSelected" METHOD WITH THE ID OF THE PREVIOUS MENU-ITEM SELECTED
            bottomNavigationView.setSelectedItemId(stack.get(stack.size()-1));

            //Toast.makeText(getApplicationContext(), "after: " + stack.size(), Toast.LENGTH_SHORT).show();

            // SET "isBackPressed" TO FALSE
            isBackPressed = false;

        } else {
            finish();
            //super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_bar);

        //spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);

        fragmentManager = getSupportFragmentManager();

        // SETTING HOME PAGE AS THE DEFAULT PAGE
        fragment = new HomeFragment();
        // MAKING CHANGES AND COMMITTING
        HomeActivity.fragmentManager
                // BEGIN THE TRANSACTION
                .beginTransaction()
                // THE "fragment" WILL BE DISPLAYED IN THE "container_fragment"
                .replace(R.id.container_fragment, fragment)

                .addToBackStack(null)
                // COMMITTING THE CHANGES
                .commit();

        // SINCE HOME PAGE IS THE DEFAULT PAGE, ADD IT TO THE "stack"
        stack.add(R.id.bottom_nav_home);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.bottom_nav_home :
                        fragment = new HomeFragment();

                        // ADD THE MENU-ITEM TO THE STACK, IF AND ONLY IF "onNavigationItemSelected"
                        // METHOD IS CALLED CLICKING ON THE NAVIGATION BAR
                        if(isBackPressed) {
                            changeViewOnBackButtonClick(fragment);
                        }else{
                            stack.add(R.id.bottom_nav_home);
                            changeViewOnClick(fragment);
                        }
                        //Toast.makeText(getApplicationContext(), "home called ", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.bottom_nav_search :
                        fragment = new SearchFrgment();

                        // ADD THE MENU-ITEM TO THE STACK, IF AND ONLY IF "onNavigationItemSelected"
                        // METHOD IS CALLED CLICKING ON THE NAVIGATION BAR
                        if(isBackPressed) {
                            changeViewOnBackButtonClick(fragment);
                        }else{
                            stack.add(R.id.bottom_nav_search);
                            changeViewOnClick(fragment);
                        }
                        //Toast.makeText(getApplicationContext(), "search called ", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.bottom_nav_upload :
                        fragment = new UploadFragment();



                        // ADD THE MENU-ITEM TO THE STACK, IF AND ONLY IF "onNavigationItemSelected"
                        // METHOD IS CALLED CLICKING ON THE NAVIGATION BAR
                        if(isBackPressed) {
                            changeViewOnBackButtonClick(fragment);
                        }else{
                            stack.add(R.id.bottom_nav_upload);
                            changeViewOnClick(fragment);
                        }
                        //Toast.makeText(getApplicationContext(), "upload called ", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.bottom_nav_notification :
                        fragment = new NotificationFragment();

                        // ADD THE MENU-ITEM TO THE STACK, IF AND ONLY IF "onNavigationItemSelected"
                        // METHOD IS CALLED CLICKING ON THE NAVIGATION BAR
                        if(isBackPressed) {
                            changeViewOnBackButtonClick(fragment);
                        }else{
                            stack.add(R.id.bottom_nav_notification);
                            changeViewOnClick(fragment);
                        }
                        //Toast.makeText(getApplicationContext(), "notification called ", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.bottom_nav_profile :
                        fragment = new ProfileFragment();

                        // ADD THE MENU-ITEM TO THE STACK, IF AND ONLY IF "onNavigationItemSelected"
                        // METHOD IS CALLED CLICKING ON THE NAVIGATION BAR
                        if(isBackPressed) {
                            changeViewOnBackButtonClick(fragment);
                        }else{
                            stack.add(R.id.bottom_nav_profile);
                            changeViewOnClick(fragment);
                        }
                        //Toast.makeText(getApplicationContext(), "profile called ", Toast.LENGTH_SHORT).show();
                        break;
                }

                //===========================================

                /*// MAKING CHANGES AND COMMITTING
                HomeActivity.fragmentManager
                        // BEGIN THE TRANSACTION
                        .beginTransaction()
                        // THE "fragment" WILL BE DISPLAYED IN THE "container_fragment"
                        .replace(R.id.container_fragment, fragment)

                        .addToBackStack(null)
                        // COMMITTING THE CHANGES
                        .commit();*/

                //=============================================
                return true;
            }
        });

        // ============================================================

        /*spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.setElevation((float) 1.5);

        // ADDING ITEM'S TO BOTTOM NAVIGATION BAR
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.nav_home_button));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.nav_search_button));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.nav_favorite_button));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.nav_profile_button));

        // SETTING ON CLICK LISTENER TO THE ITEM'S IN BOTTOM NAVIGATION BAR
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {

            @Override
            public void onCentreButtonClick() {

                spaceNavigationView.setCentreButtonSelectable(true);

                // WHEN CENTER BUTTON IS CLICKED
                fragment = new ProfileFragment();
                // MAKING CHANGES AND COMMITTING
                HomeActivity.fragmentManager
                        // BEGIN THE TRANSACTION
                        .beginTransaction()
                        // THE "fragment" WILL BE DISPLAYED IN THE "container_fragment"
                        .replace(R.id.container_fragment, fragment)

                        .addToBackStack(null)
                        // COMMITTING THE CHANGES
                        .commit();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {

                // GENERATING THE FRAGMENT ACCORDING TO THE ITEM CLICKED FROM THE BOTTOM NAVIGATION BAR
                switch (itemIndex){
                    case 0: // GENERATING HOME FRAGMENT
                        fragment = new HomeFragment();
                        break;
                    case 1: // GENERATING SEARCH FRAGMENT
                        fragment = new SearchFrgment();
                        break;
                    case 2: // GENERATING NOTIFICATION FRAGMENT
                        fragment = new NotificationFragment();
                        break;
                    case 3: // GENERATING PROFILE FRAGMENT
                        fragment = new ProfileFragment();
                        break;
                }

                // MAKING CHANGES AND COMMITTING
                HomeActivity.fragmentManager
                        // BEGIN THE TRANSACTION
                        .beginTransaction()
                        // THE "fragment" WILL BE DISPLAYED IN THE "container_fragment"
                        .replace(R.id.container_fragment, fragment)

                        .addToBackStack(null)
                        // COMMITTING THE CHANGES
                        .commit();
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(HomeActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });*/

        // ============================================================


    }

    private void changeViewOnClick(Fragment fragment){

        // MAKING CHANGES AND COMMITTING
        HomeActivity.fragmentManager
                // BEGIN THE TRANSACTION
                .beginTransaction()
                // THE "fragment" WILL BE DISPLAYED IN THE "container_fragment"
                .replace(R.id.container_fragment, fragment)
                // ADD TO STACK
                .addToBackStack(null)
                // COMMITTING THE CHANGES
                .commit();
    }

    private void changeViewOnBackButtonClick(Fragment fragment){

        // MAKING CHANGES AND COMMITTING
        HomeActivity.fragmentManager
                // BEGIN THE TRANSACTION
                .beginTransaction()
                // THE "fragment" WILL BE DISPLAYED IN THE "container_fragment"
                .replace(R.id.container_fragment, fragment)
                // COMMITTING THE CHANGES
                .commit();
    }


}

