package com.example.street_dancer_beta10.Segments.Profile.UserFollowersFollowingsComponents;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.street_dancer_beta10.HomeActivity;
import com.example.street_dancer_beta10.R;
import com.example.street_dancer_beta10.Segments.Profile.ProfileFollowersFollowingsModel;
import com.example.street_dancer_beta10.Segments.Profile.ProfileModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerSetupFragment extends Fragment {
    private static final String TAG = "ViewPagerSetupFragment";

    private TabLayout tableLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private List<ProfileFollowersFollowingsModel> profileFollowersFollowingsModels, followersModels;
    Toolbar toolbar;



    //==================================================================
    String id = "KmLHzTaSpfOflDTUVWPGWe3H0x92";
    private String title;

    private List<String> idList;

    ProfileRecyclerViewFollowersFollowingAdapter userAdapter;
    //==================================================================

    public ViewPagerSetupFragment(String title) {
        this.title = title;
        Log.d(TAG, "ViewPagerSetupFragment: opened");
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_view_page_setup, container, false);
        toolbar = view.findViewById(R.id.followers_following_toolbar_id);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        // TO HANDEL BACK BUTTON PRESS IN THE TOOLBAR
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        viewPager = view.findViewById(R.id.view_pager);
        tableLayout = view.findViewById(R.id.tab_layout);

        profileFollowersFollowingsModels = new ArrayList<>();
        followersModels = new ArrayList<>();

        userAdapter = new ProfileRecyclerViewFollowersFollowingAdapter(getContext(), profileFollowersFollowingsModels, false);
        userAdapter = new ProfileRecyclerViewFollowersFollowingAdapter(getContext(), followersModels, false);
        setAdapter();
    }


    private void setAdapter(){

        adapter = new ViewPagerAdapter(getContext(),HomeActivity.fragmentManager);

        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);

        tableLayout.setupWithViewPager(viewPager);
        idList = new ArrayList<>();

//        adapter.addFragment(new ProfileFollowersFollowingInfoFragment(profileFollowersFollowingsModels, false), "Following-" + profileFollowersFollowingsModels.size());
//        adapter.addFragment(new ProfileFollowersFollowingInfoFragment(followersModels, true), "Followers-" + followersModels.size());
//        adapter.notifyDataSetChanged();

        switch (title) {

            case "followings":
                 getFollowing();

                break;
            case "followers":
                getFollowers();

                break;

        }

    }
    private void getFollowers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(id).child("Followers");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());
                }
                showUsers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getFollowing() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(id).child("Following");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());

                }
                showUsers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(title.equals("followings")) {
                    Toast.makeText(getContext(), "followings clear", Toast.LENGTH_SHORT).show();
                    profileFollowersFollowingsModels.clear();

                }
                else if(title.equals("followers")){
                    Toast.makeText(getContext(), "followers clear", Toast.LENGTH_SHORT).show();
                    followersModels.clear();
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ProfileFollowersFollowingsModel user = snapshot.getValue(ProfileFollowersFollowingsModel.class);
                    for (String ID : idList){
                        if (user.getId().equals(ID)){
                            if(title.equals("followings")) {
                                profileFollowersFollowingsModels.add(user);
                            }
                            else if(title.equals("followers")){
                                followersModels.add(user);
                            }

                        }
                    }
                }
                Toast.makeText(getContext(), "size "+profileFollowersFollowingsModels.size(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "size"+followersModels.size(), Toast.LENGTH_SHORT).show();
                userAdapter = new ProfileRecyclerViewFollowersFollowingAdapter(getContext(), profileFollowersFollowingsModels, false);
                adapter = new ViewPagerAdapter(getContext(),HomeActivity.fragmentManager);

                adapter.notifyDataSetChanged();
                viewPager.setAdapter(adapter);

                tableLayout.setupWithViewPager(viewPager);
                idList = new ArrayList<>();



                adapter.addFragment(new ProfileFollowersFollowingInfoFragment(profileFollowersFollowingsModels, false), "Following-" + profileFollowersFollowingsModels.size());
                //followersModels.add(profileFollowersFollowingsModels.get(0));
                adapter.addFragment(new ProfileFollowersFollowingInfoFragment(followersModels, true), "Followers-" + followersModels.size());

                adapter.notifyDataSetChanged();

                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
