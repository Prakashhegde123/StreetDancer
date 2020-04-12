package com.example.street_dancer_beta10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class SignUpActivity extends AppCompatActivity {

    EditText name, mail, password, repassword, phone, dob;
    RadioButton male, female;
    private Button button;

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


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);

            }

        });
    }
}



