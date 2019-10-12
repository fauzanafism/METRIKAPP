package com.metrikdev.metrikapp.soal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.metrikdev.metrikapp.LandingActivity;
import com.metrikdev.metrikapp.LoginActivity;
import com.metrikdev.metrikapp.R;


public class Guidance extends AppCompatActivity {
    ConnectivityManager conMgr;
    private Button Lanjut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        conMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        Lanjut = (Button) findViewById(R.id.btnLanjut);
        Lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    Intent intent = new Intent(Guidance.this, LoginActivity.class);
                    Guidance.this.startActivity(intent);
                    Guidance.this.finish();
                } else {
                    Snackbar snackbar = Snackbar.make(v, "Tidak ada koneksi", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }
    public void onBackPressed(){
        Intent intent = new Intent(Guidance.this, LandingActivity.class);
        Guidance.this.startActivity(intent);
        Guidance.this.finish();
    }


}

