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

import com.example.street_dancer_beta10.HomeActivity;
import com.example.street_dancer_beta10.R;
import com.example.street_dancer_beta10.Segments.Profile.ProfileFollowersFollowingsModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerSetupFragment extends Fragment {
    private static final String TAG = "ViewPagerSetupFragment";

    private TabLayout tableLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private List<ProfileFollowersFollowingsModel> profileFollowersFollowingsModels;
    private Toolbar toolbar;

    public ViewPagerSetupFragment() {
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

        setObjects();
        setAdapter();
    }

    private void setObjects(){

        profileFollowersFollowingsModels = new ArrayList<>();

        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("1","first person", "first name", R.drawable.video_profile_image));
        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("2","second person", "second name", R.drawable.video_profile_image));
        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("3","third person", "third name", R.drawable.video_profile_image));
        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("4","fourth person", "fourth name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("fifth person", "fifth name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("first person", "first name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("second person", "second name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("third person", "third name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("fourth person", "fourth name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("fifth person", "fifth name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("first person", "first name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("second person", "second name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("third person", "third name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("fourth person", "fourth name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("fifth person", "fifth name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("first person", "first name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("second person", "second name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("third person", "third name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("fourth person", "fourth name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("fifth person", "fifth name", R.drawable.video_profile_image));
//        profileFollowersFollowingsModels.add(new ProfileFollowersFollowingsModel("first person", "first name", R.drawable.video_profile_image));

    }

    private void setAdapter(){

        adapter = new ViewPagerAdapter(HomeActivity.fragmentManager);

        adapter.addFragment(new ProfileFollowersFollowingInfoFragment(profileFollowersFollowingsModels, true), "Followers-" + profileFollowersFollowingsModels.size());
        adapter.addFragment(new ProfileFollowersFollowingInfoFragment(profileFollowersFollowingsModels, false), "Following-" + profileFollowersFollowingsModels.size());

        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);


    }
}
