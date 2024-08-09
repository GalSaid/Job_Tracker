package com.example.jobtracker.Views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.List;

public class ActivityMyApplications extends DrawerBaseActivity {
    private RecyclerView list_LST_applications;
    private ActivityApplicationsBinding activityApplicationsBinding;
    private ApplicationAdapter appAdapter;
    private TextInputEditText event_EDT_date;
    private ArrayList<Application> applications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityApplicationsBinding = ActivityApplicationsBinding.inflate(getLayoutInflater());
        setContentView(activityApplicationsBinding.getRoot());
        allocateActivityTitle("My applications");

        applications = new ArrayList<>();
        findViews();
        initRecycleView();
        Log.d("Gali", "on create");
        loadApplications();
    }

    private void loadApplications() {
        MyDbManager.getInstance().getAllApplications(apps -> {
            Log.d("Gali", "get all the apps");
            applications.clear();
            applications.addAll(apps);
            appAdapter.notifyDataSetChanged();
        });
    }

    private void initRecycleView() {
        appAdapter = new ApplicationAdapter(applications,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        list_LST_applications.setLayoutManager(linearLayoutManager);
        list_LST_applications.setAdapter(appAdapter);
        if (appAdapter == null)
            Log.d("Gali", "appAdapter is null1");
        appAdapter.setApplicationCallback(new ApplicationCallback() {
            @Override
            public void addEvent(Application app, int position, AppEvent event) {
                openEvent(app, event);
            }

            @Override
            public void updateReturnStatus(boolean isChecked, Application app, int position) {
                app.setReturned(isChecked);
                MyDbManager.getInstance().updateApplication(app, () -> {
                    appAdapter.notifyItemChanged(position);
                });
            }
        });
    }

    private void findViews() {
        list_LST_applications = findViewById(R.id.application_recyclerview);
    }


    private void openEvent(Application app, AppEvent event) {
        Log.d("Gal", "entet to open event");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_event, null);
        MaterialTextView event_LBL_title = dialogView.findViewById(R.id.event_LBL_title);
        TextInputLayout event_layout_EDT_title_description = dialogView.findViewById(R.id.event_layout_EDT_title_description);
        TextInputEditText event_EDT_title_description = dialogView.findViewById(R.id.event_EDT_title_description);
        TextInputLayout event_layout_EDT_description = dialogView.findViewById(R.id.event_layout_EDT_description);
        TextInputEditText event_EDT_description = dialogView.findViewById(R.id.event_EDT_description);
        TextInputLayout event_layout_EDT_date = dialogView.findViewById(R.id.event_layout_EDT_date);
        event_EDT_date = dialogView.findViewById(R.id.event_EDT_date);
        MaterialButton event_BTN_save = dialogView.findViewById(R.id.event_BTN_save);

        event_EDT_date.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        if (event == null) { //add new event
            event_LBL_title.setText(R.string.add_new_event);
            event_BTN_save.setText(R.string.add);
        } else { //edit event
            Log.d("Gal", "event  here is not null");
            event_LBL_title.setText(R.string.edit_event);
            event_BTN_save.setText(R.string.edit);
            event_EDT_title_description.setText(event.getTitle());
            event_EDT_description.setText(event.getDescription());
            event_EDT_date.setText(event.getDate());
        }

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
                    //save the event
                    addNewEvent(app, title, description, date);
                else {
                    //edit the event
                    event.setDate(date);
                    event.setDescription(description);
                    event.setTitle(title);
                    MyDbManager.getInstance().addOrUpdateEvent(app, event);
                }
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addNewEvent(Application app, String title, String description, String date) {
        AppEvent newEvent = new AppEvent(date, description, title);
        MyDbManager.getInstance().addOrUpdateEvent(app, newEvent);
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