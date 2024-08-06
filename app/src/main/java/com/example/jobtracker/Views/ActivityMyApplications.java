package com.example.jobtracker.Views;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtracker.Adapters.ApplicationAdapter;
import com.example.jobtracker.Model.AppEvent;
import com.example.jobtracker.Model.Application;
import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.DrawerBaseActivity;
import com.example.jobtracker.Utilities.MyDbManager;
import com.example.jobtracker.databinding.ActivityApplicationsBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ActivityMyApplications extends DrawerBaseActivity {
    private RecyclerView list_LST_applications;
    private ActivityApplicationsBinding activityApplicationsBinding;
    private  ApplicationAdapter appAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityApplicationsBinding= ActivityApplicationsBinding.inflate(getLayoutInflater());
        setContentView(activityApplicationsBinding.getRoot());
        allocateActivityTitle("My applications");


        findViews();
        initViews();
    }
    private void initViews() {
        MyDbManager.getInstance().getAllApplications(apps -> {
            initRecyclerView(apps);
        });
    }

    private void initRecyclerView(ArrayList<Application> applications) {
        appAdapter = new ApplicationAdapter(applications);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        list_LST_applications.setLayoutManager(linearLayoutManager);
        list_LST_applications.setAdapter(appAdapter);
        appAdapter.setApplicationCallback((app, position) -> {
            //open new event
            openNewEvent(app);
        });
    }
    private void findViews() {
        list_LST_applications = findViewById(R.id.application_recyclerview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (appAdapter != null)
            appAdapter.notifyDataSetChanged();
    }

    private void openNewEvent(Application app) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_event, null);
        MaterialTextView event_LBL_title= dialogView.findViewById(R.id.event_LBL_title);
        TextInputLayout event_layout_EDT_title_description= dialogView.findViewById(R.id.event_layout_EDT_title_description);
        TextInputEditText event_EDT_title_description= dialogView.findViewById(R.id.event_EDT_title_description);
        TextInputLayout event_layout_EDT_description= dialogView.findViewById(R.id.event_layout_EDT_description);
        TextInputEditText event_EDT_description= dialogView.findViewById(R.id.event_EDT_description);
        TextInputLayout event_layout_EDT_date= dialogView.findViewById(R.id.event_layout_EDT_date);
        TextInputEditText event_EDT_date= dialogView.findViewById(R.id.event_EDT_date);
        MaterialButton event_BTN_save= dialogView.findViewById(R.id.event_BTN_save);

        event_LBL_title.setText(R.string.add_new_event);
        event_BTN_save.setText(R.string.add);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        event_BTN_save.setOnClickListener(v -> {
            event_layout_EDT_title_description.setError(null);
            event_layout_EDT_description.setError(null);
            event_layout_EDT_date.setError(null);
            String title = event_EDT_title_description.getText().toString().trim();
            String description = event_EDT_description.getText().toString().trim();
            String date = event_EDT_date.getText().toString().trim();

            boolean valid=true;
            if(title.isEmpty()){
                event_layout_EDT_title_description.setError("Please enter event title");
                event_layout_EDT_title_description.requestFocus();
                valid=false;
            }
            if(description.isEmpty()){
                event_layout_EDT_description.setError("Please enter description event");
                event_layout_EDT_description.requestFocus();
                valid=false;
            }
            if(date.isEmpty()){
                event_layout_EDT_date.setError("Please enter date event");
                event_layout_EDT_date.requestFocus();
                valid=false;
            }
            if(valid){
                //save the event
                addNewEvent(app, title,description,date);
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addNewEvent(Application app,String title, String description, String date){
        AppEvent newEvent = new AppEvent(date,description,title);
        MyDbManager.getInstance().addOrUpdateEvent(app, newEvent);
    }

}