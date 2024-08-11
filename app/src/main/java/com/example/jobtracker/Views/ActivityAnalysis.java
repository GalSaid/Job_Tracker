package com.example.jobtracker.Views;


import android.os.Bundle;
import android.widget.TextView;

import androidx.core.content.ContextCompat;


import com.example.jobtracker.R;
import com.example.jobtracker.Utilities.DrawerBaseActivity;
import com.example.jobtracker.Utilities.MyDbManager;
import com.example.jobtracker.databinding.ActivityAnalysisBinding;


import org.eazegraph.lib.models.PieModel;


public class ActivityAnalysis extends DrawerBaseActivity {
    private ActivityAnalysisBinding activityAnalysisBinding;
    private TextView analysis_LBL_pending;
    private TextView analysis_LBL_accepted;
    private TextView analysis_LBL_rejected;
    private TextView analysis_LBL_inProcess;
    private org.eazegraph.lib.charts.PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAnalysisBinding=ActivityAnalysisBinding.inflate(getLayoutInflater());
        setContentView(activityAnalysisBinding.getRoot());
        allocateActivityTitle("Analysis");
        findViews();
        initView();
    }

    private void findViews() {
        analysis_LBL_pending = findViewById(R.id.analysis_LBL_pending);
        analysis_LBL_accepted = findViewById(R.id.analysis_LBL_accepted);
        analysis_LBL_rejected = findViewById(R.id.analysis_LBL_rejected);
        analysis_LBL_inProcess = findViewById(R.id.analysis_LBL_inProcess);
        pieChart = findViewById(R.id.piechart);
    }

    private void initView() {
        MyDbManager.getInstance().getStatusCount((pending, accepted, rejected, inProcess) -> {
            analysis_LBL_pending.setText(Integer.toString(pending));
            analysis_LBL_accepted.setText(Integer.toString(accepted));
            analysis_LBL_rejected.setText(Integer.toString(rejected));
            analysis_LBL_inProcess.setText(Integer.toString(inProcess));

            // Set the data and color to the pie chart
            pieChart.addPieSlice(new PieModel(
                    getString(R.string.pending),
                    Integer.parseInt(analysis_LBL_pending.getText().toString()),
                    ContextCompat.getColor(this, R.color.Pending)));

            pieChart.addPieSlice(new PieModel(
                    getString(R.string.accepted),
                    Integer.parseInt(analysis_LBL_accepted.getText().toString()),
                    ContextCompat.getColor(this, R.color.Accepted)));

            pieChart.addPieSlice(new PieModel(
                    getString(R.string.rejected),
                    Integer.parseInt(analysis_LBL_rejected.getText().toString()),
                    ContextCompat.getColor(this, R.color.Rejected)));

            pieChart.addPieSlice(new PieModel(
                    getString(R.string.in_process),
                    Integer.parseInt(analysis_LBL_inProcess.getText().toString()),
                    ContextCompat.getColor(this, R.color.InProcess)));

            // To animate the pie chart
            pieChart.startAnimation();
        });

    }

}