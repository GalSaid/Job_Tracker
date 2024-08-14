package com.example.jobtracker.Adapters;


import android.animation.ObjectAnimator;
import android.util.Log;
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

public class AppEventAdapter extends RecyclerView.Adapter<AppEventAdapter.AppEventViewHolder> {

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
    public AppEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);

        return new AppEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppEventViewHolder holder, int position) {
        AppEvent event = getItem(position);
        holder.event_LBL_description.setText(event.getDescription());
        holder.event_LBL_title.setText(event.getTitle());
        holder.event_LBL_date.setText(event.getDate());
        holder.event_LBL_description.setOnClickListener(v -> {
            animation(event, holder);
        });
        holder.event_LBL_title.setOnClickListener(v -> {
            animation(event, holder);
        });
    }

    private void animation(AppEvent event, AppEventViewHolder holder) {
        ArrayList<ObjectAnimator> animators = new ArrayList<>();

        if (event.isCollapsed()) {
            animators
                    .add(ObjectAnimator
                            .ofInt(holder.event_LBL_title, "maxLines", holder.event_LBL_title.getLineCount())
                            .setDuration(Math.max(holder.event_LBL_title.getLineCount() - event.MIN_LINES_COLLAPSED, 0) * 50L));
            animators
                    .add(ObjectAnimator
                            .ofInt(holder.event_LBL_description, "maxLines", holder.event_LBL_description.getLineCount())
                            .setDuration(Math.max(holder.event_LBL_description.getLineCount() - event.MAX_LINES_COLLAPSED, 0) * 50L));
        } else {
            animators
                    .add(ObjectAnimator
                            .ofInt(holder.event_LBL_title, "maxLines", event.MIN_LINES_COLLAPSED)
                            .setDuration(Math.max(holder.event_LBL_title.getLineCount() - event.MIN_LINES_COLLAPSED, 0) * 50L));
            animators
                    .add(ObjectAnimator
                            .ofInt(holder.event_LBL_description, "maxLines", event.MAX_LINES_COLLAPSED)
                            .setDuration(Math.max(holder.event_LBL_description.getLineCount() - event.MAX_LINES_COLLAPSED, 0) * 50L));
        }
        animators.forEach(ObjectAnimator::start);
        event.setCollapsed(!event.isCollapsed());
    }

    @Override
    public int getItemCount() {
        return appEvents == null ? 0 : appEvents.size();
    }

    public AppEvent getItem(int position) {
        return appEvents.get(position);
    }

    public class AppEventViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView event_LBL_title;
        private final MaterialTextView event_LBL_description;
        private final MaterialTextView event_LBL_date;
        private final ShapeableImageView event_IMAGEVIEW_Edit;

        public AppEventViewHolder(@NonNull View itemView) {
            super(itemView);
            event_LBL_title = itemView.findViewById(R.id.event_LBL_title);
            event_LBL_description = itemView.findViewById(R.id.event_LBL_description);
            event_LBL_date = itemView.findViewById(R.id.event_LBL_date);
            event_IMAGEVIEW_Edit = itemView.findViewById(R.id.event_IMAGEVIEW_Edit);
            event_IMAGEVIEW_Edit.setOnClickListener(v -> { // when the edit button of the event is clicked
                if (eventCallback != null) {
                    eventCallback.editEvent(getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
