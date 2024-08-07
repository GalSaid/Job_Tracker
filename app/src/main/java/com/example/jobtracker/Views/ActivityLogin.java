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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityLogin extends AppCompatActivity {


        private TextInputEditText login_EDT_email;
        private TextInputEditText login_EDT_password;
        private TextInputLayout layout_login_EDT_password;
        private TextInputLayout layout_login_EDT_email;
        private MaterialButton login_BTN_login;
        private MaterialTextView login_BTN_forgot;
        private MaterialTextView login_BTN_register;
        private ProgressBar login_progress_bar;
        private FirebaseAuth mAuth;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            findViews();
            initViews();
            mAuth = FirebaseAuth.getInstance();

        }

        private void findViews() {
            login_EDT_email=findViewById(R.id.login_EDT_email);
            login_EDT_password=findViewById(R.id.login_EDT_password);
            layout_login_EDT_password=findViewById(R.id.layout_login_EDT_password);
            layout_login_EDT_email=findViewById(R.id.layout_login_EDT_email);
            login_BTN_login=findViewById(R.id.login_BTN_login);
            login_BTN_forgot=findViewById(R.id.login_BTN_forgot);
            login_BTN_register=findViewById(R.id.login_BTN_register);
            login_progress_bar=findViewById(R.id.login_progress_bar);
        }

        private void initViews(){
            login_BTN_login.setOnClickListener((v) -> login());
            login_BTN_forgot.setOnClickListener((v) -> resetPassword());
            login_BTN_register.setOnClickListener((v) -> moveToRegister());
        }

        private void login(){
            layout_login_EDT_email.setError(null);
            layout_login_EDT_password.setError(null);
            boolean valid=true;
            String email=login_EDT_email.getText().toString().trim();
            String password=login_EDT_password.getText().toString().trim();

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                layout_login_EDT_email.setError("Please enter valid email");
                layout_login_EDT_email.requestFocus();
                valid=false;
            }
            if(password.isEmpty()){
                layout_login_EDT_password.setError("Please enter password");
                layout_login_EDT_password.requestFocus();
                valid=false;
            }
            if(valid){
                login_progress_bar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ActivityLogin.this, "User connected successfully",Toast.LENGTH_SHORT).show();
                            moveToAllJobs();
                        }
                        else{
                            Toast.makeText(ActivityLogin.this, "User failed to login",Toast.LENGTH_SHORT).show();
                        }
                        login_progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }

        private void resetPassword(){
            Intent i = new Intent(getApplicationContext(), ActivityForgotPassword.class);
            Bundle bundle = new Bundle();
            i.putExtras(bundle);
            startActivity(i);
            finish();
        }
    private void moveToRegister(){
        Intent i = new Intent(getApplicationContext(), ActivityRegister.class);
        Bundle bundle = new Bundle();
        i.putExtras(bundle);
        startActivity(i);
        finish();
    }

    private void moveToAllJobs(){
        Intent i = new Intent(getApplicationContext(), ActivityJobBoard.class);
        Bundle bundle = new Bundle();
        i.putExtras(bundle);
        startActivity(i);
        finish();
    }
}
