package com.example.android.bestreceipe;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.android.bestreceipe.Model.Steps;

import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity {

    private static final String BUNDLE_KEY = "bundle_key";
    private static final String BUNDLE_POSITION = "bundle_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_detail);
        ArrayList<Steps> stepsList = getIntent().getParcelableArrayListExtra("stepsData");
        int stepsPosition = getIntent().getIntExtra("stepsPosition", 0);
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(BUNDLE_KEY, stepsList);
            arguments.putInt(BUNDLE_POSITION, stepsPosition);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.detail_fragment_container, fragment).commit();
        }
    }
}