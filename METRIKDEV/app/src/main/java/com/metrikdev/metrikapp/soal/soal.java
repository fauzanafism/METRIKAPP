package com.metrikdev.metrikapp.soal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.metrikdev.metrikapp.setup.AppController;
import com.metrikdev.metrikapp.setup.Server;
import com.metrikdev.metrikapp.R;

public class soal extends AppCompatActivity {
    private static final String TAG = soal.class.getSimpleName();
    private static String url_insert = Server.URL + "insertJawaban.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";

    private TextView soalCounter, soalCounterPhp, txt_nosoal, bs_angka, bs_jawaban;
    private Button buttonA, buttonB, buttonC, buttonD, buttonE, buttonPass, buttonSelesai;
    SharedPreferences sharedpreferences;
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    String identity, username, sumStr, sumStrPhp, txt_a, txt_b, txt_c, txt_d, txt_e, txt_pass, url;
    ProgressDialog progressDialog;
    Integer sum, sumPhp, success, pengurang, pengurang_noSoal ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }
}
