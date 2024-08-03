package com.example.jobtracker.Views;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.DrawerBaseActivity;
import com.example.jobtracker.databinding.ActivityProfileBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityProfile extends DrawerBaseActivity {


    private ActivityProfileBinding activityProfileBinding;

    private TextInputEditText profile_EDT_name;
    private TextInputEditText profile_EDT_phone;
    private TextInputEditText profile_EDT_description;
    private TextInputLayout profile_layout_EDT_name;
    private TextInputLayout profile_layout_EDT_phone;
    private TextInputLayout profile_layout_EDT_description;
    private MaterialButton profile_BTN_update;
    private ShapeableImageView profile_IMAGEVIEW_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());
        allocateActivityTitle("My Profile");
        findViews();
        initViews();

    }

    private void findViews() {
        profile_EDT_name = findViewById(R.id.profile_EDT_name);
        profile_EDT_phone = findViewById(R.id.profile_EDT_phone);
        profile_EDT_description = findViewById(R.id.profile_EDT_description);
        profile_layout_EDT_name = findViewById(R.id.profile_layout_EDT_name);
        profile_layout_EDT_phone = findViewById(R.id.profile_layout_EDT_phone);
        profile_layout_EDT_description = findViewById(R.id.profile_layout_EDT_description);
        profile_BTN_update = findViewById(R.id.profile_BTN_update);
        profile_IMAGEVIEW_edit=findViewById(R.id.profile_IMAGEVIEW_edit);
    }

    private void initViews() {
        profile_BTN_update.setVisibility(View.INVISIBLE);
        profile_EDT_name.setEnabled(false);
        profile_EDT_phone.setEnabled(false);
        profile_EDT_description.setEnabled(false);
        profile_IMAGEVIEW_edit.setOnClickListener(v -> {
          moveToEditMode();
        });

    }

    private void moveToEditMode(){
        profile_BTN_update.setVisibility(View.VISIBLE);
        profile_EDT_name.setEnabled(true);
        profile_EDT_phone.setEnabled(true);
        profile_EDT_description.setEnabled(true);
        profile_BTN_update.setOnClickListener(v -> {
            updateProfile();
        });
    }

    private void updateProfile() {}

}
