package com.example.jobtracker.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jobtracker.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityForgotPassword extends AppCompatActivity {


    private TextInputEditText forgot_password_EDT_email;
    private TextInputLayout layout_forgot_password_EDT_email;
    private MaterialButton forgot_password_BTN_reset;
    ;
    private ProgressBar forgot_password_progress_bar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        findViews();
        initViews();
        mAuth = FirebaseAuth.getInstance();

    }

    private void findViews() {
        forgot_password_EDT_email = findViewById(R.id.forgot_password_EDT_email);
        layout_forgot_password_EDT_email = findViewById(R.id.layout_forgot_password_EDT_email);
        forgot_password_BTN_reset = findViewById(R.id.forgot_password_BTN_reset);
        forgot_password_progress_bar = findViewById(R.id.forgot_password_progress_bar);
    }

    private void initViews() {
        forgot_password_BTN_reset.setOnClickListener((v) -> resetPassword());
    }

    private void resetPassword() {
        layout_forgot_password_EDT_email.setError(null);
        String email = forgot_password_EDT_email.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            layout_forgot_password_EDT_email.setError("Please enter valid email");
            layout_forgot_password_EDT_email.requestFocus();
            return;
        }
        forgot_password_progress_bar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ActivityForgotPassword.this, "Please check your email to reset password", Toast.LENGTH_SHORT).show();
                moveToLogin();
                forgot_password_progress_bar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityForgotPassword.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                forgot_password_progress_bar.setVisibility(View.GONE);
            }
        });

    }


    private void moveToLogin() {
        Intent i = new Intent(getApplicationContext(), ActivityLogin.class);
        Bundle bundle = new Bundle();
        i.putExtras(bundle);
        startActivity(i);
        finish();
    }

}
