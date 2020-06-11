package com.example.street_dancer_beta10.Segments.Profile.UserFollowersFollowingsComponents;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.street_dancer_beta10.R;
import com.example.street_dancer_beta10.Segments.Profile.ProfileFollowersFollowingsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfileFollowersFollowingInfoFragment extends Fragment {

    private List<ProfileFollowersFollowingsModel> profileFollowersFollowingsModels;
    private boolean isFollowersFocused;
    private RecyclerView recyclerView;
    private ProfileRecyclerViewFollowersFollowingAdapter adapter;

    public ProfileFollowersFollowingInfoFragment() {
        // Required empty public constructor
    }

    public ProfileFollowersFollowingInfoFragment(boolean isFollowersFocused) {
        // Required empty public constructor
        this.isFollowersFocused = isFollowersFocused;
    }

    public ProfileFollowersFollowingInfoFragment(List<ProfileFollowersFollowingsModel> profileFollowersFollowingsModels, boolean isFollowersFocused) {
        this.profileFollowersFollowingsModels = profileFollowersFollowingsModels;
        this.isFollowersFocused = isFollowersFocused;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_followers_following_info, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view_followers_followings);
        setAdapter();
    }

    private void setAdapter() {
        adapter = new ProfileRecyclerViewFollowersFollowingAdapter(getContext(), profileFollowersFollowingsModels, this.isFollowersFocused);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
