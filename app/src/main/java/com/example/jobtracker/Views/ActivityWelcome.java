package com.example.jobtracker.Views;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jobtracker.R;
import com.google.android.material.button.MaterialButton;

public class ActivityWelcome extends AppCompatActivity {

    private MaterialButton welcome_BTN_login;
    private MaterialButton welcome_BTN_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findViews();
        initViews();
    }

    private void findViews() {
        welcome_BTN_login = findViewById(R.id.welcome_BTN_login);
        welcome_BTN_register = findViewById(R.id.welcome_BTN_register);

    }

    private void initViews(){
        welcome_BTN_register.setOnClickListener((v) -> {
            moveToRegister();
        });
        welcome_BTN_login.setOnClickListener((v) -> {
            moveToLogin();
        });
    }


    private void moveToLogin(){
        Intent i = new Intent(getApplicationContext(), ActivityLogin.class);
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

}