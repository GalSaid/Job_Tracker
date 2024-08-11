package com.example.jobtracker.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.jobtracker.Interfaces.JobCallback;
import com.example.jobtracker.Model.Job;
import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.ImageLoader;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private ArrayList<Job> jobs;
    private JobCallback jobCallback;

    public JobAdapter(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    public void setJobCallback(JobCallback jobCallback) {
        this.jobCallback = jobCallback;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);

        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = getItem(position);
        ImageLoader.getInstance().load(job.getCompanyImg(), holder.job_IMG_company);
        holder.job_LBL_name.setText(job.getName());
        holder.job_LBL_date.setText(job.getDate());
        holder.job_LBL_location.setText(String.valueOf(job.getLocation()));

    }

    @Override
    public int getItemCount() {
        return jobs == null ? 0 : jobs.size();
    }

    public Job getItem(int position) {
        return jobs.get(position);
    }

    public class JobViewHolder extends RecyclerView.ViewHolder {

        private final ShapeableImageView job_IMG_company;
        private final MaterialTextView job_LBL_date;
        private final MaterialTextView job_LBL_location;
        private final MaterialTextView job_LBL_name;
        private final CardView job_CARD;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            job_IMG_company = itemView.findViewById(R.id.job_IMG_company);
            job_LBL_date = itemView.findViewById(R.id.job_LBL_date);
            job_LBL_location = itemView.findViewById(R.id.job_LBL_location);
            job_LBL_name = itemView.findViewById(R.id.job_LBL_name);
            job_CARD=itemView.findViewById(R.id.job_CARD);
            job_CARD.setOnClickListener(v -> { //Show all the details of the job
                if (jobCallback != null){
                    jobCallback.Show(getItem(getAdapterPosition()), getAdapterPosition());
                }
           });
        }
    }
}
