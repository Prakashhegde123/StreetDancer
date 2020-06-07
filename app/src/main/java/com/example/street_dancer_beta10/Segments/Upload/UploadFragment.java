package com.example.street_dancer_beta10.Segments.Upload;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.street_dancer_beta10.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import static android.app.Activity.RESULT_CANCELED;


public class UploadFragment extends Fragment {

    //WIDGETS
    private VideoView videoView;
    private Button buttonUpload;

    // VARS
    private Uri videoURI = null;

    // FIREBASE VARS
    private StorageReference mStorageRef;

    private static final String VIDEO_DIRECTORY = "StreetDances";
    private static final int GALLERY = 1;
    private static final int CAMERA = 2;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        videoView = view.findViewById(R.id.video_view_upload);
        buttonUpload = view.findViewById(R.id.button_upload);

        //
        showUploadOptions();

        //
        MediaController controller = new MediaController(getContext());
        controller.setAnchorView(this.videoView);
        controller.setMediaPlayer(this.videoView);
        this.videoView.setMediaController(controller);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        videoView.setMediaController(controller);
                        controller.setAnchorView(videoView);

                    }
                });
            }
        });

        //
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if(videoURI != null) {
                    Toast.makeText(getContext(), getPath(videoURI), Toast.LENGTH_SHORT).show();
                    uploadVideo();
                }else {
                    Toast.makeText(getContext(), "Select a video to upload", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //
    private void uploadVideo(){

        Uri file = Uri.fromFile(new File(getPath(videoURI)));
        mStorageRef = FirebaseStorage.getInstance().getReference().child("videos");
        Toast.makeText(getContext(), "mStorageRef: " + mStorageRef, Toast.LENGTH_SHORT).show();
        buttonUpload.setText("uploading");

        mStorageRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Toast.makeText(getContext(), videoURI.toString(), Toast.LENGTH_LONG).show();
                        buttonUpload.setText("upload");
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        buttonUpload.setText("upload");
                    }
                });

                /*

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Upload Failed: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override

                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Write a message to the database
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("videos");

                                //taskSnapshot.get
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                myRef.setValue(downloadUrl.toString())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getContext(), "Successfully uploaded", Toast.LENGTH_LONG).show();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Error happened during the upload process", Toast.LENGTH_LONG).show();

                                            }
                                        });
                                );

                */

    }

    //
    private void showUploadOptions(){
        String[] uploadOptions = {"Select video from gallery", "Record video from camera" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Action");

        builder.setItems(
                uploadOptions,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                chooseVideoFromGallery();
                                break;

                            case 1:
                                takeVideoFromCamera();
                                break;
                        }
                    }
                }
        );

        builder.show();
    }

    //
    public void chooseVideoFromGallery() {
        if(checkPermission()){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY);
        }else {
            requestPermissions();
        }
    }


    //
    private void takeVideoFromCamera() {
        if(checkPermission()){
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 120);
            startActivityForResult(intent, CAMERA);
        }else {
            requestPermissions();
        }
    }

    //
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    //
    private void requestPermissions(){
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA
                );

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    //
    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY) {

            if (data != null) {
                videoURI = data.getData();

                String selectedVideoPath = getPath(videoURI);

                saveVideoToInternalStorage(selectedVideoPath);
                videoView.setVideoURI(videoURI);
                videoView.requestFocus();
                videoView.start();
            }

        } else if (requestCode == CAMERA) {
            Uri contentURI = data.getData();

            String recordedVideoPath = getPath(contentURI);

            saveVideoToInternalStorage(recordedVideoPath);
            videoView.setVideoURI(contentURI);
            videoView.requestFocus();
            videoView.start();
        }
    }


    //
    private void saveVideoToInternalStorage (String filePath) {
        File newFile;

        try {

            File currentFile = new File(filePath);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + VIDEO_DIRECTORY);
            newFile = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".mp4");

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            if(currentFile.exists()){
                InputStream in = new FileInputStream(currentFile);
                OutputStream out = new FileOutputStream(newFile);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);

                }
                in.close();
                out.close();
                // Video file saved successfully

            }else{
                // Video saving failed. Source file missing.

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //
    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };

        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {

            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);

        } else{
            return null;
        }

    }


    //
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
