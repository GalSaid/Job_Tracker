package com.example.jobtracker.Views;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jobtracker.Model.Application;
import com.example.jobtracker.Model.Job;
import com.example.jobtracker.Model.User;
import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.MyDbManager;
import com.example.jobtracker.Utilities.StorageManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;


public class ActivityJob extends AppCompatActivity {
    private String job_id;
    private MaterialTextView job_description_LBL_name;
    private MaterialTextView job_description_LBL_fullTime;
    private MaterialTextView job_description_LBL_company;
    private MaterialTextView job_description_LBL_location;
    private MaterialTextView job_description_LBL_about;
    private MaterialTextView job_description_LBL_requirements;
    private MaterialTextView job_description_LBL_content;
    private MaterialButton job_apply;
    private String uriCV = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        findViews();
        initViews();
    }

    private void initViews() {

        Intent prev = getIntent();
        job_id = prev.getExtras().getString(getString(R.string.job_id)); //Which job to show
        MyDbManager.getInstance().getSpecificJob(job_id, job -> {
            if(job == null){
                return;
            }
            initJobDetails(job);
        });
    }

    private void initJobDetails(Job job) {
        job_description_LBL_name.setText(job.getName());
        job_description_LBL_fullTime.setText(job.isFullTime() ? getString(R.string.full_time) : getString(R.string.part_time));
        job_description_LBL_company.setText(job.getCompanyName());
        job_description_LBL_location.setText(job.getLocation());
        job_description_LBL_about.setText(job.getAboutAS());
        job_description_LBL_content.setText(job.getDescription());
        job_description_LBL_requirements.setText(job.getRequirements());
        userAlreadyApplied(job); //check if the user already applied to this job
    }

    private void findViews() {
        job_description_LBL_name = findViewById(R.id.job_description_LBL_name);
        job_description_LBL_fullTime = findViewById(R.id.job_description_LBL_full_time);
        job_description_LBL_company = findViewById(R.id.job_description_LBL_company);
        job_description_LBL_location = findViewById(R.id.job_description_LBL_location);
        job_description_LBL_about = findViewById(R.id.job_description_LBL_about);
        job_description_LBL_requirements = findViewById(R.id.job_description_LBL_requirements);
        job_description_LBL_content = findViewById(R.id.job_description_LBL_content);
        job_apply = findViewById(R.id.job_apply);
    }


    private void userAlreadyApplied(Job job){
        MyDbManager.getInstance().getSpecificApplication(job.getId(),app -> {
            if (app != null) {
                job_apply.setEnabled(false); // Disable the apply button if the user already applied
            }
            else{
                job_apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //apply to job
                        showApplyDialog(job);

                    }
                });
            }
        });
    }

    private void showApplyDialog(Job job) {
        MyDbManager.getInstance().getUser(user -> {
            if (user == null) {
                Toast.makeText(this, "You need to login to apply", Toast.LENGTH_SHORT).show();
                return;
            }
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_apply, null);
            EditText apply_ET_reciver_email = dialogView.findViewById(R.id.apply_ET_reciver_email);
            MaterialTextView apply_tvAttachment = dialogView.findViewById(R.id.apply_tvAttachment);
            apply_ET_reciver_email.setEnabled(false); // Disable editing the email of the company
            apply_ET_reciver_email.setText(job.getEmail());
            EditText apply_ET_description = dialogView.findViewById(R.id.apply_ET_description);
            apply_ET_description.setText(user.getDescription());
            EditText apply_ET_subject = dialogView.findViewById(R.id.apply_ET_subject);
            apply_ET_subject.setText("Applying to job: " + job.getName());

            MaterialButton apply_BTN_send = dialogView.findViewById(R.id.apply_BTN_send);
            MaterialButton apply_BTN_attachment = dialogView.findViewById(R.id.apply_BTN_attachment);
            if (user.getPdfCV() == null || user.getWordCV() == null) { //if we have only one cv , we don't need to show the dialog of choosing cv
                uriCV = user.getPdfCV() == null ? user.getWordCV() : user.getPdfCV();
                apply_BTN_attachment.setVisibility(View.INVISIBLE);
            }
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(dialogView)
                    .create();

            apply_BTN_attachment.setOnClickListener(v -> openSelectCVDialog(user, apply_tvAttachment));

            apply_BTN_send.setOnClickListener(v -> {
                String email = apply_ET_reciver_email.getText().toString().trim();
                String description = apply_ET_description.getText().toString().trim();
                String subject = apply_ET_subject.getText().toString().trim();

                if (subject.isEmpty()) {
                    subject = "Applying to job: " + job.getName(); //put default subject
                }
                if (description.isEmpty()) {
                    description = user.getDescription(); //put default description
                }
                if (uriCV == null) {
                    Toast.makeText(this, "You have to attach CV", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendEmail(subject, description, email);
                // Dismiss the dialog
                dialog.dismiss();
            });

            dialog.show();

        });
    }

    private final ActivityResultLauncher<Intent> emailActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        addApplicationToUser();
    });

    private void sendEmail(String subject, String description, String email) {
        StorageManager.getInstance().downloadFile(uriCV, uri -> {
            try {
                Intent emailIntent = null;

                // Create an email intent for Gmail
                if (isAppInstalled("com.google.android.gm")) {
                    emailIntent = createEmailIntent(email, subject, description);
                    emailIntent.setPackage("com.google.android.gm");
                    if (uri != null) {
                        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }

                // Create an email intent for Outlook if the user has not installed Gmail
                if (emailIntent == null && isAppInstalled("com.microsoft.office.outlook")) {
                    emailIntent = createEmailIntent(email, subject, description);
                    emailIntent.setPackage("com.microsoft.office.outlook");
                    if (uri != null) {
                        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    else {
                        runOnUiThread(() -> Toast.makeText(this, "No uri of cv", Toast.LENGTH_LONG).show());
                    }
                }

                // Allow the user to choose an email client if neither Gmail nor Outlook is installed
                if (emailIntent == null) {
                    emailIntent = createEmailIntent(email, subject, description);
                    if (uri != null) {
                        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    else {
                        runOnUiThread(() -> Toast.makeText(this, "No uri of cv", Toast.LENGTH_LONG).show());
                    }
                    // Create an intent chooser to let the user select an email app
                    emailIntent = Intent.createChooser(emailIntent, "Choose an email client");
                }

                // Launch the intent
                emailActivityResultLauncher.launch(emailIntent);
            } catch (Throwable t) {
                runOnUiThread(() -> Toast.makeText(this, "Request failed, try again: " + t.toString(), Toast.LENGTH_LONG).show());
            }
        });
    }

    private boolean isAppInstalled(String packageName) { //check if the app is installed
        PackageManager packageManager = getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private Intent createEmailIntent(String email, String subject, String description) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, description);
        return emailIntent;
    }


    // Open a dialog to let the user choose between a PDF and Word CV
    private void openSelectCVDialog(User user, MaterialTextView apply_tvAttachment) {
        Uri uriPdf = Uri.parse(user.getPdfCV());
        Uri uriWord = Uri.parse(user.getWordCV());
        String pdfName = StorageManager.getInstance().getFileName(uriPdf);
        String wordName = StorageManager.getInstance().getFileName(uriWord);

        new MaterialAlertDialogBuilder(this, R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle("Choose CV")
                .setMessage("What resume would you like to send?")
                .setPositiveButton(pdfName, (dialog, which) -> {
                    uriCV = user.getPdfCV();
                    showFileAttached(pdfName, apply_tvAttachment);
                })
                .setNegativeButton(wordName, (dialog, which) -> {
                    uriCV = user.getWordCV();
                    showFileAttached(wordName, apply_tvAttachment);
                })
                .show();
    }

    private void addApplicationToUser() { //add new application to user
        if (job_id != null) {
            String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Application app = new Application(userUid, job_id, this.getResources().getStringArray(R.array.status_array)[0]);
            MyDbManager.getInstance().addApplication(userUid, app, () -> {
                moveToMyApplications();
            });
        }
        else
            Toast.makeText(this, "Job id is null", Toast.LENGTH_SHORT).show();
    }

    private void moveToMyApplications() { //after the user applied to job, move to my applications
        Intent i = new Intent(getApplicationContext(), ActivityMyApplications.class);
        Bundle bundle = new Bundle();
        i.putExtras(bundle);
        startActivity(i);
        finish();
    }

    private void showFileAttached(String fileName, MaterialTextView apply_tvAttachment) { //show the file name in the dialog
        apply_tvAttachment.setVisibility(View.VISIBLE);
        apply_tvAttachment.setText("Selected File: " + fileName);
    }
}


