package com.example.street_dancer_beta10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";
    private TextView skipButton, forgot_password;
    private TextView createAccount;
    private Button login;
    private EditText mail, pass;
    String nmail, npass;
    FirebaseAuth firebaseAuth;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        forgot_password = (TextView) findViewById(R.id.forgot_password);
        createAccount = (TextView) findViewById(R.id.create_account_text_view);
        skipButton = (TextView) findViewById(R.id.skip_btn_id);
        login = (Button) findViewById(R.id.log_in_button);
        mail = (EditText) findViewById(R.id.email_edit_text_sign_in);
        pass = (EditText) findViewById(R.id.password_edit_text_sign_in);
        firebaseAuth = FirebaseAuth.getInstance();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });



        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                forgot_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String forgot_mail = mail.getText().toString().trim();
                        if (!forgot_mail.equals("")) {
                            firebaseAuth.sendPasswordResetEmail(forgot_mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        checkEmailVerification();
                                        Toast.makeText(SignInActivity.this, "Check your mail to reset the password", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Error to reset the password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignInActivity.this, "Enter your mail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nmail = mail.getText().toString().trim();
                npass = pass.getText().toString().trim();
                if (TextUtils.isEmpty(nmail)) {
                    mail.setError("Enter your mail");
                }
                if (TextUtils.isEmpty(npass)) {
                    mail.setError("Enter your password");
                } else if (!(nmail.equals("")) && (!(npass.equals("")))) {
                    firebaseAuth.signInWithEmailAndPassword(nmail, npass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                checkEmailVerification();
                            } else {
                                Log.d(TAG, "onFailed: " + task.getException());
                                Toast.makeText(SignInActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }
    private void checkEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uname = dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        boolean flag = user.isEmailVerified();
        if (flag) {
            Toast.makeText(SignInActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(SignInActivity.this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}
