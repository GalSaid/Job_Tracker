package com.example.jobtracker.Utilities;

import android.content.Intent;
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
import com.example.jobtracker.Views.ActivityProfile;
import com.example.jobtracker.Views.ActivityWelcome;
import com.example.jobtracker.Views.ActivityJobBoard;
import com.example.jobtracker.Views.ActivityMyApplications;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
            startActivity(new Intent(this, ActivityJobBoard.class));
            overridePendingTransition(0,0);
        }
        else if(item.getItemId()== R.id.nav_myApplications){
            startActivity(new Intent(this, ActivityMyApplications.class));
            overridePendingTransition(0,0);
        } else if (item.getItemId() == R.id.nav_profile) {
            startActivity(new Intent(this, ActivityProfile.class));
            overridePendingTransition(0, 0);
        } else if(item.getItemId()==R.id.nav_logout){
            logOut();
        }
        return false;
    }

    protected void allocateActivityTitle(String titleSting){
        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle(titleSting);
    }

    private void logOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(DrawerBaseActivity.this, ActivityWelcome.class));
                        finish();

                    }
                });
    }
}
