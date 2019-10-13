package com.metrikdev.metrikapp.soal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.metrikdev.metrikapp.R;
import com.metrikdev.metrikapp.LoginActivity;

import com.metrikdev.metrikapp.setup.AppController;
import com.metrikdev.metrikapp.setup.Server;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class hasil extends AppCompatActivity {
    private static final String TAG = hasil.class.getSimpleName();
    private static String url = Server.URL + "getJawaban.php";
    String tag_json_obj = "json_obj_req";
    private Button buttonKelar;
    private ListView listView;
    ArrayList<HashMap<String, String>> list_data;

    String identity, username;
    public static final String TAG_NO_SOAL = "nomor_soal";
    public static final String TAG_JAWABAN = "jawaban_soal";
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";

    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        listView = (ListView) findViewById(R.id.listjwb);
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        identity = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
        buttonKelar = (Button) findViewById(R.id.kelarbut);
        list_data = new ArrayList<HashMap<String, String>>();
        fetch_jawaban(username);

        buttonKelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kelarButton();
            }
        });
    }

    private void fetch_jawaban(final String username){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    //get JSONArray name from JSONObject
                    JSONArray jsonArray = jsonObject.getJSONArray("daftar_jawaban");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        //get the attribute name
                        map.put("nomor_soal", json.getString("no_soal"));
                        map.put("jawaban_soal", json.getString("jawaban"));
                        list_data.add(map);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ListAdapter adapter = new SimpleAdapter(
                        hasil.this, list_data, R.layout.list_item,
                        new String[]{TAG_NO_SOAL,TAG_JAWABAN},
                        new int[]{R.id.id_cv_txt_nosoal, R.id.id_cv_txt_jawaban});

                listView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(hasil.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Menambahkan parameters post
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    public void onBackPressed(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Peringatan !");
        alertDialogBuilder
                .setMessage("Yakin ingin keluar?")
                .setIcon(R.drawable.baseline_warning_white_48)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //delete shared preferences
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(LoginActivity.session_status, false);
                        editor.putString(TAG_USERNAME, null);
                        editor.apply();
                        Intent intent = new Intent(hasil.this, LoginActivity.class);
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void kelarButton() {
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        //judul
        alertdialogbuilder.setTitle("Peringatan !");
        //pesan
        alertdialogbuilder
                .setMessage("Yakin ingin Keluar?")
                .setIcon(R.drawable.baseline_warning_white_48)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Jika dipencet, maka akan keluar
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(LoginActivity.session_status, false);
                        editor.putString(TAG_ID, null);
                        editor.putString(TAG_USERNAME, null);
                        editor.commit();

                        Intent intent = new Intent(hasil.this, LoginActivity .class);
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Jika dipencet maka tidak akan terjadi apa-apa
                        dialog.cancel();
                    }
                });
        //Membuat alert dialog dari builder
        AlertDialog alertDialog = alertdialogbuilder.create();
        //menampilkan dialog
        alertDialog.show();
    }
}
