package com.example.street_dancer_beta10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    private TextView skipButton, forgot_password;
    private TextView createAccount;
    private Button login;
    private EditText mail, pass;

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


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

}
