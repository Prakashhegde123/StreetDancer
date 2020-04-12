package com.example.street_dancer_beta10.Segments.Profile.VisitedProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.street_dancer_beta10.HomeActivity;
import com.example.street_dancer_beta10.R;
import com.example.street_dancer_beta10.Segments.Profile.UserUploadComponents.ProfileUserUploadFragment;
import com.example.street_dancer_beta10.SharedComponents.Models.MediaObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VisitedProfileFragment extends Fragment {

    ImageButton imageButton;
    Toolbar toolbar;
    private ArrayList<MediaObject> mediaObjects = new ArrayList<>();
    int dispImg=0;
    private VisitedProfileRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    private Fragment fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visited_profile, container, false);

        toolbar = view.findViewById(R.id.toolbar_visited_profile);

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
        super.onViewCreated(view, savedInstanceState);

        imageButton = (ImageButton) view.findViewById(R.id.icon);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_profile_id);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageButton.isPressed()) {
                    if (dispImg == 0) {
                        imageButton.setImageResource(R.drawable.ic_check_black_24dp);
                        dispImg = 1;
                    } else if (dispImg == 1) {
                        imageButton.setImageResource(R.drawable.ic_person_add_black_24dp);
                        dispImg = 0;
                    }

                }
            }
        });

        setMedia();
        setAdapter();

        // SET ON CLICK LISTENER
        adapter.setListener(new VisitedProfileRecyclerViewAdapter.Listener() {
            @Override
            public void onClick(int position) {


                Toast.makeText(getContext(), " " + position, Toast.LENGTH_LONG).show();
                fragment = new ProfileUserUploadFragment(position);

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
        });







    }

    private void setMedia() {

        mediaObjects.add(new MediaObject("Sending Data to a New Activity with Intent Extras",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.png",
                "Description for media object #1"));

        mediaObjects.add(new MediaObject("REST API, Retrofit2, MVVM Course SUMMARY",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API+Retrofit+MVVM+Course+Summary.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API%2C+Retrofit2%2C+MVVM+Course+SUMMARY.png",
                "Description for media object #2"));

        mediaObjects.add(new MediaObject("MVVM and LiveData",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/MVVM+and+LiveData+for+youtube.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/mvvm+and+livedata.png",
                "Description for media object #3"));

        mediaObjects.add(new MediaObject("Swiping Views with a ViewPager",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/SwipingViewPager+Tutorial.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Swiping+Views+with+a+ViewPager.png",
                "Description for media object #4"));

        mediaObjects.add(new MediaObject("Database Cache, MVVM, Retrofit, REST API demo for upcoming course",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+api+teaser+video.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+API+Integration+with+MVVM.png",
                "Description for media object #5"));
    }




    private void setAdapter() {

        // CREATE RECYCLER-VIEW ADAPTER OBJECT
        adapter = new VisitedProfileRecyclerViewAdapter(getContext(), mediaObjects, initGlide());
        recyclerView.setHasFixedSize(true);

        // SETUP THE ADAPTER
        recyclerView.setAdapter(adapter);

        // SET THE LAYOUT TO "GRID-LAYOUT"
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    private RequestManager initGlide(){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.video_error)
                .error(R.drawable.video_error);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }
}