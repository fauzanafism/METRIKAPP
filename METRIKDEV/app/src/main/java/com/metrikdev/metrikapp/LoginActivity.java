package com.metrikdev.metrikapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import com.google.android.material.snackbar.Snackbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.metrikdev.metrikapp.R;
import com.metrikdev.metrikapp.setup.AppController;
import com.metrikdev.metrikapp.setup.Server;
import com.metrikdev.metrikapp.soal.Guidance;
import com.metrikdev.metrikapp.soal.soal;


public class LoginActivity extends AppCompatActivity {
    private RelativeLayout relativeLayout;
    ProgressDialog pDialog;
    private Button login;
    private EditText user, pass;
    int success;
    ConnectivityManager conMgr;
    private String url = Server.URL + "login.php";
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID = "id";
    String tag_json_obj = "json_obj_req";
    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, username;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        //Inisialiasi Koneksi
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Variabel yang bakal di input
        login   = (Button) findViewById(R.id.login_btn);
        user    = (EditText) findViewById(R.id.userID);
        pass    = (EditText) findViewById(R.id.userPassword);


        //Session untuk mengecek apakah sudah masuk atau belum
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        username = sharedpreferences.getString(TAG_USERNAME, null);

        if (session) {
            Intent intent = new Intent(LoginActivity.this, soal.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(intent);
        }
        //Proses Ambil Data dari EditText Username dan Password
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                loginChecker();
                loginProcess();
            }
        });


    }

    private void loginChecker(){
        if (TextUtils.isEmpty(user.getText().toString().trim()) || TextUtils.isEmpty(pass.getText().toString().trim())){
            user.setError("Kolom tidak boleh kosong");
            pass.setError("Kolom tidak boleh kosong");
        }
    }

    private void loginProcess(){
        // TODO Auto-generated method stub
        String username = user.getText().toString();
        String password = pass.getText().toString();

        // mengecek kolom yang kosong
        if (username.trim().length() > 0 && password.trim().length() > 0) {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
                checkLogin(username, password);
            } else {
                Snackbar snackbar = Snackbar.make(relativeLayout, "Tidak ada koneksi", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

    }
    //lanjutan dari proses ambil data dan mengecek kolom kosong
    private void checkLogin(final String username, final String password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        String username = jObj.getString(TAG_USERNAME);
                        String id = jObj.getString(TAG_ID);

                        Log.e("Successfully Login!", jObj.toString());
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_ID, id);
                        editor.putString(TAG_USERNAME, username);
                        editor.commit();

                        // Memanggil halaman Soal
                        Intent intent = new Intent(LoginActivity.this, soal.class);
                        intent.putExtra(TAG_ID, id);
                        intent.putExtra(TAG_USERNAME, username);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



    //memunculkan alert ketika back pressed
    public void onBackPressed(){
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);

        //judul
        alertdialogbuilder.setTitle("Peringatan !");

        //pesan
        alertdialogbuilder
                .setMessage("Yakin ingin Keluar?")
                .setIcon(R.drawable.baseline_warning_white_24)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Jika dipencet, maka akan keluar
                        Intent intent = new Intent(LoginActivity.this, Guidance.class);
                        LoginActivity.this.startActivity(intent);
                        LoginActivity.this.finish();
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