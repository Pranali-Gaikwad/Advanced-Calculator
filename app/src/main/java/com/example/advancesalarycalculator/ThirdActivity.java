package com.example.advancesalarycalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {
    public static String SHARED_PREFS="mySharedPrefs";
    SharedPreferences sharedPreferences;
    EditText bpN, hraN, saN, caN, sdaN, epfN;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        bpN = findViewById(R.id.basicPayPer);
        hraN = findViewById(R.id.hraPer);
        saN = findViewById(R.id.saPer);
        caN = findViewById(R.id.caPer);
        sdaN = findViewById(R.id.sdaPer);
        epfN = findViewById(R.id.epfPerinDetails);
        btnSubmit = findViewById(R.id.btnaddDetails);
        loadDefaultPercentage();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bpN.getText().toString().isEmpty() ||
                        hraN.getText().toString().isEmpty() || saN.getText().toString().isEmpty() || caN.getText().toString().isEmpty()
                        || sdaN.getText().toString().isEmpty() || epfN.getText().toString().isEmpty()) {
                    Toast.makeText(ThirdActivity.this, "Please Enter All the Details", Toast.LENGTH_SHORT).show();
                } else {
                    saveDataInSharedPreference();
                    double t = Double.parseDouble(sharedPreferences.getString("bpPer", "30"))
                            + Double.parseDouble(sharedPreferences.getString("hrap", "10"))
                            + Double.parseDouble(sharedPreferences.getString("sap", "20"))
                            + Double.parseDouble(sharedPreferences.getString("cap", "5"))
                            + Double.parseDouble(sharedPreferences.getString("sdap", "5"))
                            + Double.parseDouble(sharedPreferences.getString("epfp", "12"));

                    if (t > 100) {
                        AlertDialog.Builder al = new AlertDialog.Builder(ThirdActivity.this);
                        al.setMessage("Total Percentage is greater than 100");
                        al.setTitle("Error Message");
                        al.setPositiveButton("OK", null);
                        al.setCancelable(true);
                        al.create().show();
                    } else {
                        Intent i = new Intent();
                        setResult(RESULT_OK, i);
                        finish();
                    }
                }
            }
        });
    }
    private void loadDefaultPercentage() {
        sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        bpN.setText(sharedPreferences.getString("bpPer","30"));
        hraN.setText(sharedPreferences.getString("hrap","10"));
        saN.setText(sharedPreferences.getString("sap","20"));
        caN.setText(sharedPreferences.getString("cap","5"));
        sdaN.setText(sharedPreferences.getString("sdap","5"));
        epfN.setText(sharedPreferences.getString("epfp","12"));
    }
    private void saveDataInSharedPreference() {
        sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("bpPer", bpN.getText().toString());
        editor.putString("hrap", hraN.getText().toString());
        editor.putString("sap", saN.getText().toString());
        editor.putString("cap", caN.getText().toString());
        editor.putString("sdap", sdaN.getText().toString());
        editor.putString("epfp", epfN.getText().toString());
        editor.apply();
    }
}