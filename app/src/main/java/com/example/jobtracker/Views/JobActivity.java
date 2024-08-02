package com.example.jobtracker.Views;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtracker.Adapters.JobAdapter;
import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.DataManager;

public class JobActivity extends AppCompatActivity {
    private RecyclerView list_LST_jobs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);


//        ArrayList<Movie> movies = DataManager.getMovies();
//
//        Gson gson = new Gson();

//        String moviesAsJson = gson.toJson(movies);
//        Log.d("Movies", "all movies: " + moviesAsJson);
//        ArrayList<Movie> fromJson = gson.fromJson(moviesAsJson,ArrayList.class);
//        Log.d("AL", "onCreate: "+ fromJson.get(0));

    //    findViews();
       // initViews();
    }
    private void initViews() {
        JobAdapter jobAdapter = new JobAdapter(DataManager.getJobs());
        Log.d("JobBoardActivity", "Number of jobs: " + DataManager.getJobs().size());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        list_LST_jobs.setLayoutManager(linearLayoutManager);
        list_LST_jobs.setAdapter(jobAdapter);
        jobAdapter.setJobCallback((job, position) -> {
                //MOVE TO JOB SCREEN
        });
    }

    private void findViews() {
        list_LST_jobs = findViewById(R.id.list_LST_jobs);
    }

}