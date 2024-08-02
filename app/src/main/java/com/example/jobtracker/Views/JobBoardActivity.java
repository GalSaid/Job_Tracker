package com.example.jobtracker.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtracker.Adapters.JobAdapter;
import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.DataManager;
import com.example.jobtracker.Utilities.DrawerBaseActivity;
import com.example.jobtracker.databinding.ActivityJobBoardBinding;

public class JobBoardActivity extends DrawerBaseActivity {
    private ActivityJobBoardBinding activityJobBoardBinding;
    private RecyclerView list_LST_jobs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityJobBoardBinding=ActivityJobBoardBinding.inflate(getLayoutInflater());
        setContentView(activityJobBoardBinding.getRoot());
        allocateActivityTitle("All Jobs");


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
        JobAdapter jobAdapter = new JobAdapter(DataManager.getJobs());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        list_LST_jobs.setLayoutManager(linearLayoutManager);
        list_LST_jobs.setAdapter(jobAdapter);
        jobAdapter.setJobCallback((job, position) -> {
                //Move to job screen
                Intent i = new Intent(getApplicationContext(), JobActivity.class);
                Bundle bundle = new Bundle();
                i.putExtras(bundle);
                startActivity(i);
                //finish();
        });
    }

    private void findViews() {
        list_LST_jobs = findViewById(R.id.list_LST_jobs);
    }

}