package com.example.street_dancer_beta10.Segments.Profile.UserUploadComponents;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.street_dancer_beta10.R;
import com.example.street_dancer_beta10.Segments.Profile.ProfileRecyclerViewAdapter;
import com.example.street_dancer_beta10.SharedComponents.Models.MediaObject;
import com.example.street_dancer_beta10.SharedComponents.RecyclerView.VerticalSpacingItemDecorator;
import com.example.street_dancer_beta10.SharedComponents.RecyclerView.VideoPlayerRecyclerAdapter;
import com.example.street_dancer_beta10.SharedComponents.RecyclerView.VideoPlayerRecyclerView;

import java.util.ArrayList;

public class ProfileUserUploadFragment extends Fragment {

    private VideoPlayerRecyclerView recyclerView;
    private ProfileRecyclerViewAdapter adapter;
    private ArrayList<MediaObject> mediaObjects = new ArrayList<>();
    private Toolbar toolbar;
    private  int position;
    public ProfileUserUploadFragment() {
        // Required empty public constructor
    }

    public ProfileUserUploadFragment(int position) {
        this.position = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_user_upload, container, false);

        toolbar = view.findViewById(R.id.toolbar_user_uploads);

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
        recyclerView = (VideoPlayerRecyclerView) view.findViewById(R.id.profile_user_upload_recycler_view);

        getMedia();
        setAdapter();
    }

    private void getMedia() {

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

        adapter = new ProfileRecyclerViewAdapter(getContext(), mediaObjects, initGlide());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(0);
        recyclerView.addItemDecoration(itemDecorator);

        recyclerView.setMediaObjects(mediaObjects);

        VideoPlayerRecyclerAdapter adapter = new VideoPlayerRecyclerAdapter(getContext(), mediaObjects, initGlide());
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(position);
        adapter.notifyDataSetChanged();

    }

    private RequestManager initGlide(){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.video_error)
                .error(R.drawable.video_error);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }

    @Override
    public void onDestroy() {
        if (recyclerView != null)
            recyclerView.releasePlayer();
        super.onDestroy();
    }
    @Override
    public void onPause() {
        if (recyclerView != null)
            recyclerView.releasePlayer();
        super.onPause();
    }

}
