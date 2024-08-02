package com.example.jobtracker.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jobtracker.Model.User;
import com.example.jobtracker.R;
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

    }

    private void initViews(){
        register_BTN_register.setOnClickListener((v) -> register());
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
            layout_EDT_password.setError("Please enter password");
            layout_EDT_password.requestFocus();
            valid=false;
        }
        if(valid){
            progress_bar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user=new User(register_EDT_name.getText().toString(),email, phone, description);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(ActivityRegister.this, "User registered Successfully",Toast.LENGTH_SHORT).show();
                                            moveToAllJobs();
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
        Intent i = new Intent(getApplicationContext(), JobBoardActivity.class);
        Bundle bundle = new Bundle();
        i.putExtras(bundle);
        startActivity(i);
        finish();
    }
}
