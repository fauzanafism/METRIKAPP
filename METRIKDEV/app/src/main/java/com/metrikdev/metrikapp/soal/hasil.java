package com.metrikdev.metrikapp.soal;

import androidx.appcompat.app.AppCompatActivity;

import com.metrikdev.metrikapp.R;
import com.metrikdev.metrikapp.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

public class hasil extends AppCompatActivity {
    private static final String TAG = hasil.class.getSimpleName();
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    private String identity, username;
    private ListView listView;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        listView = (ListView) findViewById(R.id.listjwb);
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        identity = getIntent().getStringExtra("id");
        username = getIntent().getStringExtra("username");

    }
}
