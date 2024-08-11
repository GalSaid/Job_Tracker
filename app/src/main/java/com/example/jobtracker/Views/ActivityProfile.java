package com.example.jobtracker.Views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.DrawerBaseActivity;
import com.example.jobtracker.Utilities.MyDbManager;
import com.example.jobtracker.Utilities.StorageManager;
import com.example.jobtracker.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.FirebaseDatabase;

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
    private LinearLayout profile_LAYOT_pdf;
    private LinearLayout profile_LAYOT_word;
    private MaterialTextView profile_LBL_wordName;
    private MaterialTextView profile_LBL_pdfName;
    private Uri uri_pdf=null;
    private Uri uri_word=null;
    private String previousPdfUrl =null;
    private String previousWordUrl =null;
    private ProgressBar profile_progress_bar;
    private ShapeableImageView profile_IMG_pdf;
    private ShapeableImageView profile_IMG_word;
    private boolean editMode=false;

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
        profile_LAYOT_word=findViewById(R.id.profile_LAYOT_word);
        profile_LAYOT_pdf=findViewById(R.id.profile_LAYOT_pdf);
        profile_LBL_wordName=findViewById(R.id.profile_LBL_wordName);
        profile_LBL_pdfName=findViewById(R.id.profile_LBL_pdfName);
        profile_progress_bar=findViewById(R.id.profile_progress_bar);
        profile_IMG_pdf=findViewById(R.id.profile_IMG_pdf);
        profile_IMG_word=findViewById(R.id.profile_IMG_word);
    }

    private void initViews() {
        MyDbManager.getInstance().getUser(user->{
            profile_EDT_name.setText(user.getName());
            profile_EDT_phone.setText(user.getPhoneNumber());
            profile_EDT_description.setText(user.getDescription());
            previousPdfUrl =user.getPdfCV();
            previousWordUrl =user.getWordCV();
            if(previousPdfUrl !=null && !previousPdfUrl.isEmpty()) //Show the pdf  cv file name if it exists
                profile_LBL_pdfName.setText(StorageManager.getInstance().getFileName(Uri.parse(previousPdfUrl)));
            else
                profile_LAYOT_pdf.setVisibility(View.GONE);

            if(previousWordUrl !=null && !previousWordUrl.isEmpty()) //Show the word cv file name if it exists
                profile_LBL_wordName.setText(StorageManager.getInstance().getFileName(Uri.parse(previousWordUrl)));
            else
                profile_LAYOT_word.setVisibility(View.GONE);
        });
        profile_LAYOT_pdf.setOnClickListener(v -> {
            if(editMode) //If in edit mode, can upload a new pdf cv
                StorageManager.getInstance().uploadPdfCV(launcherPdf);
            else
                viewPdf(); //If not in edit mode, can view the pdf cv
        });
        profile_LAYOT_word.setOnClickListener(v -> {
            if (editMode) //If in edit mode, can upload a new word cv
                StorageManager.getInstance().uploadWordCV(launcherWord);
            else
                viewWord(); //If not in edit mode, can view the word cv
        });
        noEditMode(); //Initially not in edit mode
        profile_IMAGEVIEW_edit.setOnClickListener(v -> {
          moveToEditMode();
        });
        profile_BTN_update.setOnClickListener(v -> {
            updateProfile(); //Save the updated profile
        });
    }

    private void moveToEditMode(){
        editMode=true;
        profile_BTN_update.setVisibility(View.VISIBLE);
        profile_EDT_name.setEnabled(true);
        profile_EDT_phone.setEnabled(true);
        profile_EDT_description.setEnabled(true);
    }

    private void updateProfile() {
        noEditMode();

        profile_layout_EDT_name.setError(null);
        profile_layout_EDT_phone.setError(null);
        profile_layout_EDT_description.setError(null);


        boolean valid=true;
        String name=profile_EDT_name.getText().toString();
        String phone=profile_EDT_phone.getText().toString().trim();
        String description=profile_EDT_description.getText().toString().trim();
        if(name.isEmpty()){
            profile_layout_EDT_name.setError("Please enter full name");
            profile_layout_EDT_name.requestFocus();
            valid=false;
        }
        if(phone.isEmpty()){
            profile_layout_EDT_phone.setError("Please enter phone");
            profile_layout_EDT_phone.requestFocus();
            valid=false;
        }
        if(description.isEmpty()){
            profile_layout_EDT_description.setError("Please enter description about yourself");
            profile_layout_EDT_description.requestFocus();
            valid=false;
        }
        if(valid){
            profile_progress_bar.setVisibility(View.VISIBLE);
            MyDbManager.getInstance().getUser(user->{
                if(user==null)
                   Toast.makeText(ActivityProfile.this, "User not found",Toast.LENGTH_SHORT).show();
                user.setName(name);
                user.setPhoneNumber(phone);
                user.setDescription(description);
                if(uri_pdf!=null){ //update the new pdf cv
                    StorageManager.getInstance().uploadPdfCVToFB(uri_pdf);
                }
                if(uri_word!=null){ //update the new word cv
                    StorageManager.getInstance().uploadWordCVToFB(uri_word);
                }
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ActivityProfile.this, "Successfully updated",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(ActivityProfile.this, "The user has not been updated",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                profile_progress_bar.setVisibility(View.GONE);
            });
        }
    }
    private ActivityResultLauncher<Intent> launcherPdf=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode()==RESULT_OK && result.getData()!=null){
            Intent data=result.getData();
            if(data!=null && data.getData()!=null){
                uri_pdf=data.getData();
                profile_LBL_pdfName.setText(StorageManager.getInstance().getFileName(uri_pdf)); //Show the new pdf cv file name
            }
        }
    });

    private ActivityResultLauncher<Intent> launcherWord= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode()==RESULT_OK && result.getData()!=null){
            Intent data=result.getData();
            if(data!=null && data.getData()!=null){
                uri_word=data.getData();
                profile_LBL_wordName.setText(StorageManager.getInstance().getFileName(uri_word)); //Show the new word cv file name
            }
        }
    });

    private void viewPdf(){ //view the pdf cv
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("application/pdf");
        intent.setData(Uri.parse(previousPdfUrl));
        startActivity(intent);
    }

    private void viewWord(){ //view the word cv`
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        intent.setData(Uri.parse(previousWordUrl));
        startActivity(intent);
    }

    private void noEditMode(){
        editMode=false;
        profile_BTN_update.setVisibility(View.INVISIBLE);
        profile_EDT_name.setEnabled(false);
        profile_EDT_phone.setEnabled(false);
        profile_EDT_description.setEnabled(false);

    }

}
