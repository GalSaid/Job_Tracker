package com.example.jobtracker.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtracker.Interfaces.EventCallback;
import com.example.jobtracker.Model.AppEvent;
import com.example.jobtracker.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class AppEventAdapter extends RecyclerView.Adapter<AppEventAdapter.PlayerViewHolder> {

    private ArrayList<AppEvent> appEvents;
    private EventCallback eventCallback;


    public AppEventAdapter(ArrayList<AppEvent> appEvents) {
        this.appEvents = appEvents;
    }

    public void setAppEventCallback(EventCallback eventCallback) {
        this.eventCallback = eventCallback;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);

        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        AppEvent event = getItem(position);
        holder.event_LBL_description.setText(event.getDescription());
        holder.event_LBL_title.setText(event.getTitle());
        holder.event_LBL_date.setText(event.getDate());
    }

    @Override
    public int getItemCount() {
        return appEvents == null ? 0 : appEvents.size();
    }

    public AppEvent getItem(int position) {
        return appEvents.get(position);
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView event_LBL_title;
        private final MaterialTextView event_LBL_description;
        private final MaterialTextView event_LBL_date;
        private final ShapeableImageView event_IMAGEVIEW_Edit;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            event_LBL_title = itemView.findViewById(R.id.event_LBL_title);
            event_LBL_description = itemView.findViewById(R.id.event_LBL_description);
            event_LBL_date = itemView.findViewById(R.id.event_LBL_date);
            event_IMAGEVIEW_Edit = itemView.findViewById(R.id.event_IMAGEVIEW_Edit);
            event_IMAGEVIEW_Edit.setOnClickListener(v -> { //Show all the details of the job
                if (eventCallback != null){
                    eventCallback.editEvent(getItem(getAdapterPosition()), getAdapterPosition());
                }
           });
        }
    }
}
