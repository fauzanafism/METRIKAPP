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
import com.metrikdev.metrikapp.setup.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
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
    private static String url_insert = Server.URL + "viewJawaban.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    private Button buttonKelar;
    private String JSON_STRING;
    private ListView listView;

    String identity, username, url;
    Integer success;

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_NO_SOAL = "no_soal";
    public static final String TAG_JAWABAN = "jawaban";

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
        getJSON();

        buttonKelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kelarButton();
            }
        });
    }




    private void showJawaban(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String no_soal = jo.getString(TAG_NO_SOAL);
                String jawaban = jo.getString(TAG_JAWABAN);

                HashMap<String,String> tabel = new HashMap<>();
                tabel.put(TAG_NO_SOAL,no_soal);
                tabel.put(TAG_JAWABAN,jawaban);
                list.add(tabel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                hasil.this, list, R.layout.list_item,
                new String[]{TAG_NO_SOAL,TAG_JAWABAN},
                new int[]{R.id.nosoaljawaban, R.id.jawabanpeserta});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(hasil.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showJawaban();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(url_insert);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
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
