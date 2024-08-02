package com.example.jobtracker.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtracker.Adapters.ApplicationAdapter;
import com.example.jobtracker.Adapters.JobAdapter;
import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.DataManager;
import com.example.jobtracker.Utilities.DrawerBaseActivity;
import com.example.jobtracker.databinding.ActivityApplicationsBinding;
import com.example.jobtracker.databinding.ActivityJobBoardBinding;

public class MyApplicationsActivity extends DrawerBaseActivity {
    private RecyclerView list_LST_applications;
    private ActivityApplicationsBinding activityApplicationsBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityApplicationsBinding= ActivityApplicationsBinding.inflate(getLayoutInflater());
        setContentView(activityApplicationsBinding.getRoot());
        allocateActivityTitle("My applications");



//        ArrayList<Movie> movies = DataManager.getMovies();
//
//        Gson gson = new Gson();

//        String moviesAsJson = gson.toJson(movies);
//        Log.d("Movies", "all movies: " + moviesAsJson);
//        ArrayList<Movie> fromJson = gson.fromJson(moviesAsJson,ArrayList.class);
//        Log.d("AL", "onCreate: "+ fromJson.get(0));

        findViews();
        initViews();
    }
    private void initViews() {
        ApplicationAdapter appAdapter = new ApplicationAdapter(DataManager.getMyApplications());
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