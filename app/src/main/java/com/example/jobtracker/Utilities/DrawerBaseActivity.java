package com.example.jobtracker.Utilities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.jobtracker.R;
import com.example.jobtracker.Views.JobBoardActivity;
import com.example.jobtracker.Views.MyApplicationsActivity;
import com.example.jobtracker.databinding.ActivityLoginBinding;
import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
        drawerLayout=(DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container=drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);
        Toolbar toolbar=drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView =drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId()==R.id.nav_allJobs){
            startActivity(new Intent(this, JobBoardActivity.class));
            overridePendingTransition(0,0);
        }
        else if(item.getItemId()== R.id.nav_myApplications){
            startActivity(new Intent(this, MyApplicationsActivity.class));
            overridePendingTransition(0,0);
        }
        return false;
    }

    protected void allocateActivityTitle(String titleSting){
        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle(titleSting);
    }
}
