package com.example.jobtracker.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtracker.Interfaces.ApplicationCallback;
import com.example.jobtracker.Model.Application;
import com.example.jobtracker.Model.Job;
import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.ImageLoader;
import com.google.android.material.imageview.ShapeableImageView;
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

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        Application app = getItem(position);
        if(app.isReturned())
            holder.application_CHECKBOX_returned.setChecked(true);
        else
            holder.application_CHECKBOX_returned.setChecked(false);
        holder.application_LBL_title.setText(app.getTitle());

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
                app
                        .getAllEvents());
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
        private final ShapeableImageView application_IMAGEVIEW_plus;
        private final CheckBox application_CHECKBOX_returned;
        private RecyclerView ChildRecyclerView;
        private final Spinner application_SPINNER_status;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            application_LBL_title = itemView.findViewById(R.id.application_LBL_title);
            application_IMAGEVIEW_plus = itemView.findViewById(R.id.application_IMAGEVIEW_plus);
            application_CHECKBOX_returned = itemView.findViewById(R.id.application_CHECKBOX_returned);
            application_SPINNER_status=itemView.findViewById(R.id.application_SPINNER_status);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    itemView.getContext(),
                    R.array.status_array,
                    android.R.layout.simple_spinner_item
            );
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner.
            application_SPINNER_status.setAdapter(adapter);
            ChildRecyclerView
                    = itemView
                    .findViewById(
                            R.id.list_LST_events);
            application_IMAGEVIEW_plus.setOnClickListener(v -> { //Show all the details of the job
                if (appCallback != null){
                    appCallback.addEvent(getItem(getAdapterPosition()), getAdapterPosition());
                }
           });
        }
    }
}
