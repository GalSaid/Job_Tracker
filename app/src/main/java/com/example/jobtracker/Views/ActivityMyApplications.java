package com.example.jobtracker.Views;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtracker.Adapters.ApplicationAdapter;
import com.example.jobtracker.Model.Application;
import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.DrawerBaseActivity;
import com.example.jobtracker.Utilities.MyDbManager;
import com.example.jobtracker.databinding.ActivityApplicationsBinding;

import java.util.ArrayList;

public class ActivityMyApplications extends DrawerBaseActivity {
    private RecyclerView list_LST_applications;
    private ActivityApplicationsBinding activityApplicationsBinding;


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
        ApplicationAdapter appAdapter = new ApplicationAdapter(applications);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        list_LST_applications.setLayoutManager(linearLayoutManager);
        list_LST_applications.setAdapter(appAdapter);
        appAdapter.setApplicationCallback((app, position) -> {
            //MOVE TO event SCREEN
//            Intent i = new Intent(getApplicationContext(), eventActivity.class);
//            Bundle bundle = new Bundle();
//            i.putExtras(bundle);
//            startActivity(i);
//            //finish();
        });
    }
    private void findViews() {
        list_LST_applications = findViewById(R.id.application_recyclerview);
    }

}