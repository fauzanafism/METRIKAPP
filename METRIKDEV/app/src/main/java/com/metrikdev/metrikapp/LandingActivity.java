package com.metrikdev.metrikapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        btnStart = (Button)findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_start:
                Intent loginIntent = new Intent(LandingActivity.this, LoginActivity.class);
                startActivity(loginIntent);

            break;
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan kembali untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
