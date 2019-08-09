package com.metrikdev.metrikapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    private int waktu_loading=2000;
    //2000=2sekon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                Intent start=new Intent(SplashActivity.this, LandingActivity.class);
                startActivity(start);
                finish();
            }
        },waktu_loading);
    }
}
