package com.example.jobtracker.Views;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jobtracker.Model.User;
import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.StorageManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityRegister extends AppCompatActivity {
    private TextInputEditText register_EDT_name;
    private TextInputEditText register_EDT_email;
    private TextInputEditText register_EDT_phone;
    private TextInputEditText  register_EDT_description;
    private TextInputEditText register_EDT_password;
    private TextInputLayout layout_EDT_name;
    private TextInputLayout layout_EDT_email;
    private TextInputLayout layout_EDT_phone;
    private TextInputLayout  layout_EDT_description;
    private TextInputLayout layout_EDT_password;
    private MaterialButton register_BTN_register;
    private MaterialButton btn_pdf;
    private MaterialButton btn_word;
    private Uri uri_pdf=null;
    private Uri uri_word=null;
    private ProgressBar progress_bar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        initViews();
        mAuth = FirebaseAuth.getInstance();

    }

    private void findViews() {
        register_EDT_name = findViewById(R.id.register_EDT_name);
        layout_EDT_name=findViewById(R.id.layout_EDT_name);
        register_EDT_email = findViewById(R.id.register_EDT_email);
        layout_EDT_email=findViewById(R.id.layout_EDT_email);
        register_EDT_phone = findViewById(R.id.register_EDT_phone);
        layout_EDT_phone=findViewById(R.id.layout_EDT_phone);
        register_EDT_description = findViewById(R.id.register_EDT_description);
        layout_EDT_description=findViewById(R.id.layout_EDT_description);
        register_EDT_password = findViewById(R.id.register_EDT_password);
        layout_EDT_password =findViewById(R.id.layout_EDT_password );
        register_BTN_register=findViewById(R.id.register_BTN_register);
        progress_bar=findViewById(R.id.progress_bar);
        btn_pdf=findViewById(R.id.btn_pdf);
        btn_word=findViewById(R.id.btn_word);

    }

    private void initViews(){
        register_BTN_register.setOnClickListener((v) -> register());
        btn_pdf.setOnClickListener((v) -> uploadPdfCV());
        btn_word.setOnClickListener((v) -> uploadWordCV());
    }


    private ActivityResultLauncher<Intent> launcherPdf=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode()==RESULT_OK && result.getData()!=null){
            Intent data=result.getData();
            if(data!=null && data.getData()!=null){
                uri_pdf=data.getData();
                Toast.makeText(ActivityRegister.this,StorageManager.getInstance().getFileName(uri_pdf)+" has been uploaded",Toast.LENGTH_SHORT).show();
            }
        }
    });

    private ActivityResultLauncher<Intent> launcherWord= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode()==RESULT_OK && result.getData()!=null){
            Intent data=result.getData();
            if(data!=null && data.getData()!=null){
                uri_word=data.getData();
                Toast.makeText(ActivityRegister.this,StorageManager.getInstance().getFileName(uri_word)+" has been uploaded",Toast.LENGTH_SHORT).show();
            }
        }
    });

    private void uploadWordCV(){
        Intent intent = new Intent();
        intent.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcherWord.launch(Intent.createChooser(intent, "Select WORD"));
    }


    private void uploadPdfCV(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcherPdf.launch(Intent.createChooser(intent, "Select PDF"));
    }

    private void register(){
        layout_EDT_phone.setError(null);
        layout_EDT_email.setError(null);
        layout_EDT_password.setError(null);
        layout_EDT_name.setError(null);
        layout_EDT_description.setError(null);

        boolean valid=true;
        String name=register_EDT_name.getText().toString().trim();
        String email=register_EDT_email.getText().toString().trim();
        String phone=register_EDT_phone.getText().toString().trim();
        String description=register_EDT_description.getText().toString().trim();
        String password=register_EDT_password.getText().toString().trim();
        if(name.isEmpty()){
            Log.d("gal","here");
            layout_EDT_name.setError("Please enter full name");
            layout_EDT_name.requestFocus();
            valid=false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            layout_EDT_email.setError("Please enter valid email");
            layout_EDT_email.requestFocus();
            valid=false;
        }
        if(phone.isEmpty()){
            layout_EDT_phone.setError("Please enter phone");
            layout_EDT_phone.requestFocus();
            valid=false;
        }
        if(description.isEmpty()){
            layout_EDT_description.setError("Please enter description about yourself");
            layout_EDT_description.requestFocus();
            valid=false;
        }
        if(password.length() < 6){
            layout_EDT_password.setError("Please enter password with at least 6 characters");
            layout_EDT_password.requestFocus();
            valid=false;
        }
        if(uri_pdf==null && uri_word==null){
                Toast.makeText(ActivityRegister.this, "Please upload your CV",Toast.LENGTH_SHORT).show();
            valid=false;
        }
        if(valid){
            progress_bar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user=new User(name,email, phone, description);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(ActivityRegister.this, "User registered Successfully",Toast.LENGTH_SHORT).show();
                                            moveToAllJobs();
                                            StorageManager.getInstance().uploadPdfCVToFB(uri_pdf);
                                            StorageManager.getInstance().uploadWordCVToFB(uri_word);
                                        }
                                        else{
                                            Toast.makeText(ActivityRegister.this, "User failed to register",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else{
                        Toast.makeText(ActivityRegister.this, "User failed to register",Toast.LENGTH_SHORT).show();
                    }
                    progress_bar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void moveToAllJobs(){
        Intent i = new Intent(getApplicationContext(), ActivityJobBoard.class);
        Bundle bundle = new Bundle();
        i.putExtras(bundle);
        startActivity(i);
        finish();
    }
}
