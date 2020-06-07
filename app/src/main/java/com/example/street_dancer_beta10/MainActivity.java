package com.example.street_dancer_beta10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ProgressBar progressBar;
    private TextView textView;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textview1);

        firebaseAuth = FirebaseAuth.getInstance();

        // splash animation
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        FadingCircle fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);

        //animation
        Animation progressDelay = AnimationUtils.loadAnimation(this, R.anim.myprogress);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        //textView1.startAnimation(animation2);
        textView.startAnimation(animation1);
        progressBar.startAnimation(progressDelay);

        final Intent loginIntent  = new Intent(MainActivity.this, SignInActivity.class);

        Thread user =new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    FirebaseUser currentuser = firebaseAuth.getCurrentUser();
                    if(currentuser==null){
                        Intent loginIntent  = new Intent(MainActivity.this, SignInActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }

                    else {
                        Intent mainIntent  = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }
            }
        };
        user.start();

    }


}

