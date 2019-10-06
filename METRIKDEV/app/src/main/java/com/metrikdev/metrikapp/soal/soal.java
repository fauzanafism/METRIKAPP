package com.metrikdev.metrikapp.soal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.metrikdev.metrikapp.LoginActivity;
import com.metrikdev.metrikapp.setup.AppController;
import com.metrikdev.metrikapp.setup.Server;

public class soal extends AppCompatActivity {
    private static final String TAG = soal.class.getSimpleName();
    private static String url_insert = Server.URL + "insertJawaban.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";


    private TextView soalCounter, soalCounterPhp, pgangka, pgjawaban;
    private Button buttonA, buttonB, buttonC, buttonD, buttonPass, buttonSelesai;
    SharedPreferences sharedpreferences;
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    String identity, username, sumStr, sumStrPhp, txt_a, txt_b, txt_c, txt_d, txt_pass, url;
    ProgressDialog progressDialog;
    Integer sum, sumPhp, success, pengurang, pengurang_noSoal ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_soal);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        identity = getIntent().getStringExtra("id");
        username = getIntent().getStringExtra("user_name");
        buttonA = (Button) findViewById(R.id.button_a);
        buttonB = (Button) findViewById(R.id.button_b);
        buttonC = (Button) findViewById(R.id.button_c);
        buttonD = (Button) findViewById(R.id.button_d);
        buttonPass = (Button) findViewById(R.id.button_pass);
        txt_a   = "A";
        txt_b   = "B";
        txt_c   = "C";
        txt_d   = "D";
        txt_pass = "Lewati";
        pgangka = (TextView) findViewById(R.id.angka);
        pgjawaban = (TextView) findViewById(R.id.jawaban);
        soalCounter = (TextView) findViewById(R.id.no_soal);
        soalCounterPhp = (TextView) findViewById(R.id.no_soalphp);
        buttonSelesai = (Button) findViewById(R.id.button_finish);

        //Button Action A-D
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog_A();
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog_B();
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog_C();
            }
        });

        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog_D();
            }
        });

        buttonPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog_pass();
            }
        });

        buttonSelesai.setVisibility(View.GONE);
        buttonSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertFinish();
            }
        });

    }

    //private method
    private void alertDialog_A(){
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setTitle("Peringatan !");
        alertdialogbuilder
                .setMessage("Yakin jawabannya A?")
                .setIcon(R.drawable.baseline_warning_white_48)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        insert_inputA(username, txt_a);
                        setSoalCounter();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertdialogbuilder.create();
        alertDialog.show();
    }

    private void alertDialog_B(){
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setTitle("Peringatan !");
        alertdialogbuilder
                .setMessage("Yakin jawabannya B?")
                .setIcon(R.drawable.baseline_warning_white_48)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        insert_inputB(username, txt_b);
                        setSoalCounter();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertdialogbuilder.create();
        alertDialog.show();
    }

    private void alertDialog_C(){
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setTitle("Peringatan !");
        alertdialogbuilder
                .setMessage("Yakin jawabannya C?")
                .setIcon(R.drawable.baseline_warning_white_48)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        insert_inputC(username, txt_c);
                        setSoalCounter();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertdialogbuilder.create();
        alertDialog.show();
    }

    private void alertDialog_D(){
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setTitle("Peringatan !");
        alertdialogbuilder
                .setMessage("Yakin jawabannya D?")
                .setIcon(R.drawable.baseline_warning_white_48)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        insert_inputD(username, txt_d);
                        setSoalCounter();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertdialogbuilder.create();
        alertDialog.show();
    }



    private void alertDialog_pass(){
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setTitle("Peringatan !");
        alertdialogbuilder
                .setMessage("Yakin Dilewati saja?")
                .setIcon(R.drawable.baseline_warning_white_48)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        insert_inputpass(username, txt_pass);
                        setSoalCounter();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertdialogbuilder.create();
        alertDialog.show();
    }
    // this method is for submitting input through volley to DB
    private void insert_inputA(final String username, final String txt_a){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mengirim Jawaban ...");
        showDialog();

        url = url_insert;
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Add", jObj.toString());
                        Toast.makeText(soal.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        pgangka.setText(sumStrPhp);
                        pgjawaban.setText(txt_a);
                    } else {
                        Toast.makeText(soal.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(soal.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                jawabanError();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // memasukkan value ke dalam DB
                params.put("username", username);
                params.put("no_soal", sumStrPhp);
                params.put("jawaban", txt_a);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void insert_inputB(final String username, final String txt_b){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mengirim Jawaban ...");
        showDialog();

        url = url_insert;
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Add", jObj.toString());
                        Toast.makeText(soal.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        pgangka.setText(sumStrPhp);
                        pgjawaban.setText(txt_b);

                    } else {
                        Toast.makeText(soal.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(soal.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                jawabanError();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // memasukkan value ke dalam DB
                params.put("username", username);
                params.put("no_soal", sumStrPhp);
                params.put("jawaban", txt_b);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void insert_inputC(final String username, final String txt_c){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mengirim Jawaban ...");
        showDialog();

        url = url_insert;
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Add", jObj.toString());
                        Toast.makeText(soal.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        pgangka.setText(sumStrPhp);
                        pgjawaban.setText(txt_c);

                    } else {
                        Toast.makeText(soal.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(soal.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                jawabanError();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // memasukkan value ke dalam DB
                params.put("username", username);
                params.put("no_soal", sumStrPhp);
                params.put("jawaban", txt_c);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void insert_inputD(final String username, final String txt_d){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mengirim Jawaban ...");
        showDialog();

        url = url_insert;
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Add", jObj.toString());
                        Toast.makeText(soal.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        pgangka.setText(sumStrPhp);
                        pgjawaban.setText(txt_d);

                    } else {
                        Toast.makeText(soal.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(soal.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                jawabanError();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // memasukkan value ke dalam DB
                params.put("username", username);
                params.put("no_soal", sumStrPhp);
                params.put("jawaban", txt_d);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void insert_inputpass(final String username, final String txt_pass){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mengirim Jawaban ...");
        showDialog();

        url = url_insert;
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Add", jObj.toString());
                        Toast.makeText(soal.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        pgangka.setText(sumStrPhp);
                        pgjawaban.setText(txt_pass);

                    } else {
                        Toast.makeText(soal.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(soal.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                jawabanError();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // memasukkan value ke dalam DB
                params.put("username", username);
                params.put("no_soal", sumStrPhp);
                params.put("jawaban", txt_pass);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    //public method
    public void alertFinish(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Peringatan !");
        builder
                .setMessage("Dengan menekan tombol ini, Anda akan menyelesaikan tes")
                .setIcon(R.drawable.baseline_warning_white_48)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //logout process
//                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                        editor.putBoolean(loginPenyisihan.session_status, false);
//                        editor.putString(TAG_ID, null);
//                        editor.putString(TAG_USERNAME, null);
//                        editor.commit();
//                        Intent intent = new Intent(soalPG.this, loginPenyisihan.class);
//                        finish();
//                        startActivity(intent);
                        Intent intent = new Intent(soal.this, hasil.class);
                        intent.putExtra("id", identity);
                        intent.putExtra("username",username);
                        soal.this.startActivity(intent);
                        soal.this.finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onBackPressed(){
        Toast.makeText(soal.this, "Anda tidak dapat kembali", Toast.LENGTH_LONG).show();
    }

    public void setSoalCounter(){
        //for mySQL (Query)
        sumStrPhp = soalCounterPhp.getText().toString();
        sumPhp = Integer.valueOf(sumStrPhp);
        sumPhp++;
        sumStrPhp = String.valueOf(sumPhp);
        soalCounterPhp.setText(sumStrPhp);
        //for App
        sumStr = soalCounter.getText().toString();
        sum = Integer.valueOf(sumStr);
        sum++;
        sumStr = String.valueOf(sum);
        soalCounter.setText(sumStr);
        // convert string value from sumStr (which is counted number) to Integer and will create some conditional statement
        Integer strtoint = Integer.valueOf(sumStr);
        if (strtoint == 75) {
            buttonSelesai.setVisibility(View.VISIBLE);
            soalCounter.setVisibility(View.GONE);
            buttonA.setVisibility(View.GONE);
            buttonB.setVisibility(View.GONE);
            buttonC.setVisibility(View.GONE);
            buttonD.setVisibility(View.GONE);
            buttonPass.setVisibility(View.GONE);
        } else {
            buttonSelesai.setVisibility(View.GONE);
        }
    }
    private void jawabanError(){
        int a = 1;
        //for php
        pengurang = sumPhp - a;
        sumStrPhp = String.valueOf(pengurang);
        soalCounterPhp.setText(sumStrPhp);
        //for App
        pengurang_noSoal = sum - a;
        sumStr = String.valueOf(pengurang_noSoal);
        soalCounter.setText(sumStr);
    }}
