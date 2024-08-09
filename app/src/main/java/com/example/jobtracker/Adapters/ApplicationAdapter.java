package com.example.jobtracker.Adapters;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
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
import com.example.jobtracker.Interfaces.EventCallback;
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
import java.util.Calendar;

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
    private Context context;
    private ArrayAdapter<CharSequence> statusAdapter;


    public ApplicationAdapter(ArrayList<Application> apps, Context context) {
        this.apps = apps;
        this.context=context;
        statusAdapter  = ArrayAdapter.createFromResource(
               context,
                R.array.status_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        Log.d("Gali", "onBindViewHolder: " + position);
        Application app = getItem(position);
        MyDbManager.getInstance().getSpecificJob(app.getJobId(), job -> {
            holder.application_LBL_title.setText(job.getName());
            holder.application_LBL_location.setText(job.getLocation());
        });
        holder.application_LBL_date.setText(app.getDate());
        holder.application_CHECKBOX_returned.setChecked(app.isReturned());

        // Apply the adapter to the spinner.
        holder.application_SPINNER_status.setAdapter(statusAdapter);
        String status = app.getStatus();
       // holder.application_SPINNER_status.setOnItemSelectedListener(null);
        holder.application_SPINNER_status.setSelection(statusAdapter.getPosition(status));

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
            appCallback.addEvent(app,pos,event);
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

            application_SPINNER_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String selectedStatus = (String) parentView.getItemAtPosition(position);
                    // Update application status
                    int pos = getAdapterPosition();
                    Application app = getItem(pos);
                    if (!app.getStatus().equals(selectedStatus)) {
                        app.setStatus(selectedStatus);
                        Log.d("Gali","here1");
                        MyDbManager.getInstance().updateApplication(app,()->{
                            notifyItemChanged(pos);
                            Log.d("Gali","here2");

                        });
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            application_CHECKBOX_returned.setOnCheckedChangeListener((buttonView, isChecked) -> {
               appCallback.updateReturnStatus(isChecked,getItem(getAdapterPosition()),getAdapterPosition());
            });
            ChildRecyclerView
                    = itemView
                    .findViewById(
                            R.id.list_LST_events);
            application_IMAGEVIEW_plus.setOnClickListener(v -> { //Show all the details of the job
                if (appCallback != null) {
                    appCallback.addEvent(getItem(getAdapterPosition()),getAdapterPosition(),null);
                }
            });
        }
    }
}
