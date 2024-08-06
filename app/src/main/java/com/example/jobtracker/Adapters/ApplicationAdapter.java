package com.example.jobtracker.Adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtracker.Interfaces.ApplicationCallback;
import com.example.jobtracker.Model.AppEvent;
import com.example.jobtracker.Model.Application;
import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.MyDbManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.AppViewHolder> {

    private ArrayList<Application> apps;
    // An object of RecyclerView.RecycledViewPool
    // is created to share the Views
    // between the child and
    // the parent RecyclerViews
    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();
    private ApplicationCallback appCallback;

    public ApplicationAdapter(ArrayList<Application> apps) {
        this.apps = apps;
    }

    public void setApplicationCallback(ApplicationCallback appCallback) {
        this.appCallback = appCallback;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_item, parent, false);

        return new AppViewHolder(view);
    }

    private void openEditEvent(Context context, AppEvent event, Application app) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_edit_event, null);
        MaterialTextView event_LBL_title= dialogView.findViewById(R.id.event_LBL_title);
        TextInputLayout event_layout_EDT_title_description= dialogView.findViewById(R.id.event_layout_EDT_title_description);
        TextInputEditText event_EDT_title_description= dialogView.findViewById(R.id.event_EDT_title_description);
        TextInputLayout event_layout_EDT_description= dialogView.findViewById(R.id.event_layout_EDT_description);
        TextInputEditText event_EDT_description= dialogView.findViewById(R.id.event_EDT_description);
        TextInputLayout event_layout_EDT_date= dialogView.findViewById(R.id.event_layout_EDT_date);
        TextInputEditText event_EDT_date= dialogView.findViewById(R.id.event_EDT_date);
        MaterialButton event_BTN_save= dialogView.findViewById(R.id.event_BTN_save);

        event_LBL_title.setText(R.string.edit_event);
        event_BTN_save.setText(R.string.edit);
        event_EDT_title_description.setText(event.getTitle());
        event_EDT_description.setText(event.getDescription());
        event_EDT_date.setText(event.getDate());

        AlertDialog dialog = new AlertDialog.Builder(context)
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
                //edit the event
                event.setDate(date);
                event.setDescription(description);
                event.setTitle(title);
                MyDbManager.getInstance().addOrUpdateEvent(app, event);
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        Application app = getItem(position);
        MyDbManager.getInstance().getSpecificJob(app.getJobId(), job -> {
            holder.application_LBL_title.setText(job.getName());
            holder.application_LBL_location.setText(job.getLocation());
        });
        holder.application_LBL_date.setText(app.getDate());
        if (app.isReturned())
            holder.application_CHECKBOX_returned.setChecked(true);
        else
            holder.application_CHECKBOX_returned.setChecked(false);
        String status = app.getStatus();
        holder.application_SPINNER_status.setSelection(((ArrayAdapter<CharSequence>) holder.application_SPINNER_status.getAdapter()).getPosition(status));

        // Create a layout manager
        // to assign a layout
        // to the RecyclerView.

        // Here we have assigned the layout
        // as LinearLayout with vertical orientation
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                holder
                        .ChildRecyclerView
                        .getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
        layoutManager
                .setInitialPrefetchItemCount(
                        app
                                .getAllEvents()
                                .size());

        // Create an instance of the child
        // item view adapter and set its
        // adapter, layout manager and RecyclerViewPool
        AppEventAdapter childItemAdapter
                = new AppEventAdapter(
                MyDbManager.getInstance().convertEventsHashMapToArrayList(app.getAllEvents()));
        childItemAdapter.setAppEventCallback((event, pos) -> {
            openEditEvent(holder.itemView.getContext(), event, app);
                }
        );
        holder
                .ChildRecyclerView
                .setLayoutManager(layoutManager);
        holder
                .ChildRecyclerView
                .setAdapter(childItemAdapter);
        holder
                .ChildRecyclerView
                .setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return apps == null ? 0 : apps.size();
    }

    public Application getItem(int position) {
        return apps.get(position);
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView application_LBL_title;
        private final MaterialTextView application_LBL_location;
        private final MaterialTextView application_LBL_date;
        private final ShapeableImageView application_IMAGEVIEW_plus;
        private final CheckBox application_CHECKBOX_returned;
        private RecyclerView ChildRecyclerView;
        private final Spinner application_SPINNER_status;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            application_LBL_title = itemView.findViewById(R.id.application_LBL_title);
            application_IMAGEVIEW_plus = itemView.findViewById(R.id.application_IMAGEVIEW_plus);
            application_CHECKBOX_returned = itemView.findViewById(R.id.application_CHECKBOX_returned);
            application_LBL_location = itemView.findViewById(R.id.application_LBL_location);
            application_LBL_date = itemView.findViewById(R.id.application_LBL_date);
            application_SPINNER_status = itemView.findViewById(R.id.application_SPINNER_status);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    itemView.getContext(),
                    R.array.status_array,
                    android.R.layout.simple_spinner_item
            );
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner.
            application_SPINNER_status.setAdapter(adapter);
            application_SPINNER_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String selectedStatus = (String) parentView.getItemAtPosition(position);
                    // Update application status
                    int pos = getAdapterPosition();
                    Application app = getItem(pos);
                    app.setStatus(selectedStatus);
                    MyDbManager.getInstance().updateApplication(app);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            application_CHECKBOX_returned.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();
                    Application app = getItem(position);
                    app.setReturned(isChecked);
                    //notifyItemChanged(position);
                    MyDbManager.getInstance().updateApplication(app);
                }
            });
            ChildRecyclerView
                    = itemView
                    .findViewById(
                            R.id.list_LST_events);
            application_IMAGEVIEW_plus.setOnClickListener(v -> { //Show all the details of the job
                if (appCallback != null) {
                    appCallback.addEvent(getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
