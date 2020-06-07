package com.example.street_dancer_beta10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.street_dancer_beta10.Segments.Home.HomeFragment;
import com.example.street_dancer_beta10.Segments.Notification.NotificationFragment;
import com.example.street_dancer_beta10.Segments.Profile.ProfileFragment;
import com.example.street_dancer_beta10.Segments.Search.SearchFragment;
import com.example.street_dancer_beta10.Segments.Upload.UploadFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

private String email;

    FirebaseAuth firebaseAuth;



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

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        Bundle Bundleintent = getIntent().getExtras();
        if (Bundleintent != null){
            String publisher = Bundleintent.getString("publisherid");

            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,
                    new ProfileFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,
                    new HomeFragment()).commit();
        }



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





    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.bottom_nav_home:
                            fragment = new HomeFragment();
                            // ADD THE MENU-ITEM TO THE STACK, IF AND ONLY IF "onNavigationItemSelected"
                            // METHOD IS CALLED CLICKING ON THE NAVIGATION BAR
                            if(isBackPressed) {
                                changeViewOnBackButtonClick(fragment);
                            }else{
                                stack.add(R.id.bottom_nav_home);
                                changeViewOnClick(fragment);
                            }
                            break;
                        case R.id.bottom_nav_search:
                            fragment = new SearchFragment();

                            // ADD THE MENU-ITEM TO THE STACK, IF AND ONLY IF "onNavigationItemSelected"
                            // METHOD IS CALLED CLICKING ON THE NAVIGATION BAR
                            if(isBackPressed) {
                                changeViewOnBackButtonClick(fragment);
                            }else{
                                stack.add(R.id.bottom_nav_search);
                                changeViewOnClick(fragment);
                            }
                            break;
                        case R.id.bottom_nav_upload:
                            fragment = new UploadFragment();
                            // ADD THE MENU-ITEM TO THE STACK, IF AND ONLY IF "onNavigationItemSelected"
                            // METHOD IS CALLED CLICKING ON THE NAVIGATION BAR
                            if(isBackPressed) {
                                changeViewOnBackButtonClick(fragment);
                            }else{
                                stack.add(R.id.bottom_nav_upload);
                                changeViewOnClick(fragment);
                            }
                            break;
                        case R.id.bottom_nav_notification:
                            fragment = new NotificationFragment();
                            // ADD THE MENU-ITEM TO THE STACK, IF AND ONLY IF "onNavigationItemSelected"
                            // METHOD IS CALLED CLICKING ON THE NAVIGATION BAR
                            if(isBackPressed) {
                                changeViewOnBackButtonClick(fragment);
                            }else{
                                stack.add(R.id.bottom_nav_notification);
                                changeViewOnClick(fragment);
                            }
                            break;
                        case R.id.bottom_nav_profile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            fragment = new ProfileFragment();
                            // ADD THE MENU-ITEM TO THE STACK, IF AND ONLY IF "onNavigationItemSelected"
                            // METHOD IS CALLED CLICKING ON THE NAVIGATION BAR
                            if(isBackPressed) {
                                changeViewOnBackButtonClick(fragment);
                            }else{
                                stack.add(R.id.bottom_nav_profile);
                                changeViewOnClick(fragment);
                            }
                            break;
                    }
                    if (fragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,
                                fragment).commit();
                    }

                    return true;
                }
            };





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

    @Override
    protected void onPause() {
        super.onPause();
    }



}

