package com.example.jobtracker.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtracker.Adapters.JobAdapter;
import com.example.jobtracker.Model.Job;
import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.DrawerBaseActivity;
import com.example.jobtracker.Utilities.MyDbManager;
import com.example.jobtracker.databinding.ActivityJobBoardBinding;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ActivityJobBoard extends DrawerBaseActivity {
    private ActivityJobBoardBinding activityJobBoardBinding;
    private RecyclerView list_LST_jobs;
    private JobAdapter jobAdapter;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityJobBoardBinding=ActivityJobBoardBinding.inflate(getLayoutInflater());
        setContentView(activityJobBoardBinding.getRoot());
        allocateActivityTitle("All Jobs");
        MyDbManager.getInstance().createJobsAndLoadToDB(); //added jobs to jobBoard
        findViews();
        initViews();
    }

    private void initViews() {
        MyDbManager.getInstance().getAllJobs(jobs -> {
            initRecycleView(jobs);
        });
    }

    private void initRecycleView(ArrayList<Job> jobs){
        jobAdapter = new JobAdapter(jobs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        list_LST_jobs.setLayoutManager(linearLayoutManager);
        list_LST_jobs.setAdapter(jobAdapter);
        jobAdapter.setJobCallback((job, position) -> {
            //Move to job screen
            Intent i = new Intent(getApplicationContext(), ActivityJob.class);
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.job_id), job.getId());
            i.putExtras(bundle);
            startActivity(i);
            //finish();
        });
    }

    private void findViews() {
        list_LST_jobs = findViewById(R.id.list_LST_jobs);
    }

    public void refreshEventsList() {
        jobAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (jobAdapter != null)
            jobAdapter.notifyDataSetChanged();
    }

}