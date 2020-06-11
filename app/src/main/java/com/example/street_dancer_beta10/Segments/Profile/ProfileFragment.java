package com.example.street_dancer_beta10.Segments.Profile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.street_dancer_beta10.Segments.Profile.MenuFragments.AboutFragment;
import com.example.street_dancer_beta10.Segments.Profile.MenuFragments.HelpFragment;
import com.example.street_dancer_beta10.Segments.Profile.MenuFragments.PrivacyFragment;
import com.example.street_dancer_beta10.Segments.Profile.MenuFragments.SavedFragment;
import com.example.street_dancer_beta10.Segments.Profile.MenuFragments.SecurityFragment;
import com.example.street_dancer_beta10.HomeActivity;
import com.example.street_dancer_beta10.R;
import com.example.street_dancer_beta10.Segments.Profile.UserFollowersFollowingsComponents.ViewPagerSetupFragment;
import com.example.street_dancer_beta10.Segments.Profile.UserUploadComponents.ProfileUserUploadFragment;
import com.example.street_dancer_beta10.SharedComponents.Models.MediaObject;
import com.example.street_dancer_beta10.SignInActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    private RecyclerView recyclerView;
    public static final int REQUEST_IMAGE = 100;
    private ProfileRecyclerViewAdapter adapter;
    private TextView followers_linearLayout;
    private TextView following_linearlayout;
    private Fragment fragment;
    FirebaseAuth firebaseAuth;
    private TextView fullname;
    ImageView image_profile;
    private TextView username;
    private ImageView profileImage;

    private TextView followersFollowingUsername;
    private TextView followersFollowingName;
    private TextView followersFollowingProfile;

    FirebaseUser firebaseUser;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;

    String cameraPermissions[];
    String storagePermissions[];
    ProgressDialog pd;

    String profileid;

    Uri image_uri;



    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static final String USERS = "Users";
    private String email;


    private Uri mImageUri;
    private StorageTask uploadTask;
    StorageReference storageRef;



    @BindView(R.id.profile_pic_circular_image_view)
    ImageView imageView;

    Bitmap bitmap;

    private ArrayList<MediaObject> mediaObjects = new ArrayList<>();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saved:
                fragment = new SavedFragment();
                break;
            case R.id.security:
                fragment = new SecurityFragment();
                break;
            case R.id.help:
                fragment = new HelpFragment();
                break;
            case R.id.about:
                fragment = new AboutFragment();
                break;
            case R.id.privacy:
                fragment = new PrivacyFragment();
                break;
            case R.id.logout:
                firebaseAuth.signOut();
                Intent intent = new Intent(getContext(), SignInActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Logout!", Toast.LENGTH_LONG).show();
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

        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d(TAG, "onViewCreated: inside on-created");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");

        ButterKnife.bind(this, view);
        Log.d(TAG, "onViewCreated: inside on-create-view");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: inside on-view-created");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_profile_id);
        followers_linearLayout = (TextView) view.findViewById(R.id.followers_id);
        following_linearlayout = (TextView) view.findViewById(R.id.following_id);

        followersFollowingName = (TextView) view.findViewById(R.id.followers_following_name);
        followersFollowingUsername = (TextView) view.findViewById(R.id.followers_following_username);
        followersFollowingProfile = (TextView) view.findViewById(R.id.followers_following_profile);

        fullname = view.findViewById(R.id.fullname);
        username = view.findViewById(R.id.username);
        profileImage = view.findViewById(R.id.profile_pic_circular_image_view);

        pd = new ProgressDialog(getActivity());

        cameraPermissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE };
        cameraPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE };

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.inflateMenu(R.menu.my_menu);

        loadProfileDefault();



        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
        ImagePickerActivity.clearCache(getContext());

        Log.d(TAG, "onViewCreated: before call");
        setMedia();
        setAdapter();
        Log.d(TAG, "onViewCreated: after call");

        //followers-following
        followers_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ViewPagerSetupFragment("followers");


                HomeActivity.fragmentManager
                        .beginTransaction()
                        .replace(R.id.container_fragment, fragment)
                        .commit();
            }
        });

        //followers-following
        following_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ViewPagerSetupFragment("followings");

                HomeActivity.fragmentManager
                        .beginTransaction()
                        .replace(R.id.container_fragment, fragment)
                        .commit();
            }
        });


        // SET ON CLICK LISTENER
        adapter.setListener(new ProfileRecyclerViewAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Log.d(TAG, "onClick: ProfileFragment " + position);

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

                Log.d(TAG, "onClick: ProfileFragment " + position);
            }
        });
        userInfo();
    }

    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (getContext() == null){
                    return;
                }
                ProfileModel user = dataSnapshot.getValue(ProfileModel.class);

                fullname.setText(user.getName());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

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


    //-------------------------------------------------------------------------------------------



    //-------------------------------------------------------------------------------------------




    private void setAdapter() {

        // CREATE RECYCLER-VIEW ADAPTER OBJECT
        adapter = new ProfileRecyclerViewAdapter(getContext(), mediaObjects, initGlide());
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

    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);

        Glide.with(this).load(url)
                .into(imageView);
        imageView.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.transparent));
    }
    private void loadProfileDefault() {

        Glide.with(this).load(R.drawable.user)
                .into(imageView);
        imageView.setColorFilter(ContextCompat.getColor(getContext(), R.color.profile_default_tint));
    }



        @OnClick({ R.id.profile_pic_circular_image_view})
    void onProfileImageClick() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }




    ///////////////////////////////////////////////////////////////////////////////////////////



    ///////////////////////////////////////////////////////////////////////////////////////////

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(getContext(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }
    private void launchCameraIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }
    private void launchGalleryIntent() {
        Log.d(TAG, "launchGalleryIntent: gallery");
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "onActivityResult: before");
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Log.d(TAG, "onActivityResult: after");
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }
    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getApplicationContext().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }





}
