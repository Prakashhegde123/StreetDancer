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
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    EditText name, mail, password, repassword, phone, dob;
    RadioButton male, female;
    String gender;
    private Button button;
    //new 1
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        button = (Button) findViewById(R.id.create_account_login);
        name = (EditText) findViewById(R.id.name_edit_text_create_account);
        mail = (EditText) findViewById(R.id.email_edit_text_create_account);
        password = (EditText) findViewById(R.id.password_edit_text_create_account);
        repassword = (EditText) findViewById(R.id.re_password_edit_text_create_account);
        phone = (EditText) findViewById(R.id.phone_edit_text_create_account);
        dob = (EditText) findViewById(R.id.dob_edit_text_create_account);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        firebaseAuth = FirebaseAuth.getInstance();
        mRef = firebaseDatabase.getInstance().getReference("Users");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mname = name.getText().toString();
                String mmail = mail.getText().toString();
                String mpass = password.getText().toString();
                String mrepass = repassword.getText().toString();
                String mphone = phone.getText().toString();
                String mdob = dob.getText().toString();
                if (TextUtils.isEmpty(mname)) {
                    name.setError("Enter your name");
                }
                if (TextUtils.isEmpty(mmail)) {
                    mail.setError("Enter your Email");
                }
                if (TextUtils.isEmpty(mpass)) {
                    password.setError("Enter your password");
                }
                if (TextUtils.isEmpty(mrepass)) {
                    repassword.setError("Enter your password");
                }
                if (TextUtils.isEmpty(mphone)) {
                    phone.setError("Enter your conf");
                }
                if (TextUtils.isEmpty(mdob)) {
                    dob.setError("Enter your name");
                }
                if (mpass.length() < 8) {
                    password.setError("Password Minimum length should be 8");
                }
                if (mrepass.length() < 8) {
                    password.setError("Password Minimum length should be 8");
                }
                if (!(mpass.equals(mrepass))) {
                    password.setError("The password or repassword entered is wrong");
                    return;
                }
                if (male.isChecked()) {
                    gender = "male";
                }
                if (female.isChecked()) {
                    gender = "female";
                }
                //validate
                else if (!(mname.equals("")) && (!(mmail.equals(""))) && (!(mpass.equals(""))) && (!(mrepass.equals(""))) && (!(mphone.equals("")))
                        && (!(mdob.equals("")))&&mpass.equals(mrepass)) {
                    firebaseAuth.createUserWithEmailAndPassword(mmail, mpass)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        Data data = new Data(mname, mmail, mpass, mrepass, mphone, mdob, gender);
                                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                sendEmailVerification();
                                            }
                                        });

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }

        });
    }
    //new_1.0
    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Account successfully created.Please check your mail to verify your account.", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Account Creation Failed.Please create a new Account.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(SignUpActivity.this, "Was not able to send a verfication mail!! Please try again!!", Toast.LENGTH_SHORT).show();
        }
    }
}



