package com.example.street_dancer_beta10.Segments.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.street_dancer_beta10.HomeActivity;
import com.example.street_dancer_beta10.R;
import com.example.street_dancer_beta10.Segments.Profile.UserFollowersFollowingsComponents.ViewPagerSetupFragment;
import com.example.street_dancer_beta10.SharedComponents.Models.MediaObject;
import com.example.street_dancer_beta10.SharedComponents.RecyclerView.VerticalSpacingItemDecorator;
import com.example.street_dancer_beta10.SharedComponents.RecyclerView.VideoPlayerRecyclerAdapter;
import com.example.street_dancer_beta10.SharedComponents.RecyclerView.VideoPlayerRecyclerView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private VideoPlayerRecyclerView recyclerView;
    private VideoPlayerRecyclerAdapter adapter;
    private ArrayList<MediaObject> mediaObjects = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = (VideoPlayerRecyclerView) view.findViewById(R.id.home_video_recycler_view);
        
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(0);
        recyclerView.addItemDecoration(itemDecorator);

        recyclerView.setMediaObjects(mediaObjects);

        adapter = new VideoPlayerRecyclerAdapter(getContext(), mediaObjects, initGlide());


        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private RequestManager initGlide() {
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
