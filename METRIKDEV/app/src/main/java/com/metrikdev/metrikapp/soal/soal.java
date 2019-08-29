package com.metrikdev.metrikapp.soal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.metrikdev.metrikapp.R;

public class soal extends AppCompatActivity {
    ConnectivityManager conMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        conMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    }
}
