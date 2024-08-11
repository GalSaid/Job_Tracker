package com.example.jobtracker.Views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtracker.Adapters.ApplicationAdapter;
import com.example.jobtracker.Interfaces.ApplicationCallback;
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
import java.util.Calendar;

public class ActivityMyApplications extends DrawerBaseActivity {
    private RecyclerView list_LST_applications;
    private ActivityApplicationsBinding activityApplicationsBinding;
    private ApplicationAdapter appAdapter;
    private TextInputEditText event_EDT_date;
    private ArrayList<Application> applications;
    private ProgressBar progress_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityApplicationsBinding = ActivityApplicationsBinding.inflate(getLayoutInflater());
        setContentView(activityApplicationsBinding.getRoot());
        allocateActivityTitle("My applications");

        applications = new ArrayList<>();
        findViews();
        initRecycleView();
        loadApplications();
    }

    private void loadApplications() { //load all applications from firebase
        progress_bar.setVisibility(ProgressBar.VISIBLE);
        MyDbManager.getInstance().getAllApplications(apps -> {
            if (apps.isEmpty())
                Toast.makeText(this, "No applications found", Toast.LENGTH_SHORT).show();
            applications.clear();
            applications.addAll(apps);
            appAdapter.notifyDataSetChanged();
            progress_bar.setVisibility(ProgressBar.GONE);
        });
    }

    private void initRecycleView() {
        appAdapter = new ApplicationAdapter(applications, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        list_LST_applications.setLayoutManager(linearLayoutManager);
        list_LST_applications.setAdapter(appAdapter);

        appAdapter.setApplicationCallback(new ApplicationCallback() {
            @Override
            public void addEvent(Application app, int position, AppEvent event) { //add new event to specific application
                openEvent(app, event, position);
            }

            @Override
            public void updateReturnStatus(boolean isChecked, Application app, int position) { //update the return status of the application
                app.setReturned(isChecked);
                MyDbManager.getInstance().updateApplication(app, app.getStatus());
            }
        });
    }

    private void findViews() {
        list_LST_applications = findViewById(R.id.application_recyclerview);
        progress_bar = findViewById(R.id.apps_progress_bar);
    }


    private void openEvent(Application app, AppEvent event, int appPosition) { //for new event or edit existing event
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_event, null);
        MaterialTextView event_LBL_title = dialogView.findViewById(R.id.event_LBL_title);
        TextInputLayout event_layout_EDT_title_description = dialogView.findViewById(R.id.event_layout_EDT_title_description);
        TextInputEditText event_EDT_title_description = dialogView.findViewById(R.id.event_EDT_title_description);
        TextInputLayout event_layout_EDT_description = dialogView.findViewById(R.id.event_layout_EDT_description);
        TextInputEditText event_EDT_description = dialogView.findViewById(R.id.event_EDT_description);
        TextInputLayout event_layout_EDT_date = dialogView.findViewById(R.id.event_layout_EDT_date);
        event_EDT_date = dialogView.findViewById(R.id.event_EDT_date);
        MaterialButton event_BTN_save = dialogView.findViewById(R.id.event_BTN_save);

        event_EDT_date.setOnClickListener(v -> { //show date picker dialog of choosing the date
            showDatePickerDialog();
        });

        if (event == null) { //add new event
            event_LBL_title.setText(R.string.add_new_event);
            event_BTN_save.setText(R.string.add);
        } else { //edit event
            event_LBL_title.setText(R.string.edit_event);
            event_BTN_save.setText(R.string.edit);
            event_EDT_title_description.setText(event.getTitle());
            event_EDT_description.setText(event.getDescription());
            event_EDT_date.setText(event.getDate());
        }

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        event_BTN_save.setOnClickListener(v -> { //save the event
            event_layout_EDT_title_description.setError(null);
            event_layout_EDT_description.setError(null);
            event_layout_EDT_date.setError(null);
            String title = event_EDT_title_description.getText().toString().trim();
            String description = event_EDT_description.getText().toString().trim();
            String date = event_EDT_date.getText().toString().trim();

            boolean valid = true;
            if (title.isEmpty()) {
                event_layout_EDT_title_description.setError("Please enter event title");
                event_layout_EDT_title_description.requestFocus();
                valid = false;
            }
            if (description.isEmpty()) {
                event_layout_EDT_description.setError("Please enter description event");
                event_layout_EDT_description.requestFocus();
                valid = false;
            }
            if (date.isEmpty()) {
                event_layout_EDT_date.setError("Please enter date event");
                event_layout_EDT_date.requestFocus();
                valid = false;
            }
            if (valid) {
                if (event == null)
                    //save the new event
                    addNewEvent(app, title, description, date,appPosition);
                else {
                    //edit the event
                    event.setDate(date);
                    event.setDescription(description);
                    event.setTitle(title);
                    app.getAllEvents().put(event.getId(), event);
                    MyDbManager.getInstance().updateApplication(app, app.getStatus());
                    appAdapter.notifyItemChanged(appPosition);
                }
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addNewEvent(Application app, String title, String description, String date, int appPosition) { //add new event to the application
        AppEvent newEvent = new AppEvent(date, description, title);
        app.getAllEvents().put(newEvent.getId(), newEvent);
        MyDbManager.getInstance().updateApplication(app, app.getStatus());
        appAdapter.notifyItemChanged(appPosition);
    }

    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, this::setDate, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setDate(DatePicker view, int year, int month, int dayOfMonth) {
        String selectedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
        event_EDT_date.setText(selectedDate);
    }


}