package com.example.advancesalarycalculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;

public class NewActivity extends AppCompatActivity {

    private static DecimalFormat df2 = new DecimalFormat("#.##");
    public static String SHARED_PREFS = "mySharedPrefs";
    SharedPreferences sharedPreferences;

    EditText ctc1;
    TextView basicPayPer1, basicPayAmt1, hraPer1, hraAmt1, saPer1, saAmt1, caPer1, caAmt1, sdaPer1, sdaAmt1, epfPer1, epfAmt1, taxPer1;
    TextView taxAmt1, total, ded, net;

    double ctcSalary;
    double bpPer = 30;
    double hraP = 10;
    double saP = 20;
    double cap = 5;
    double sdaP = 5;
    double epfp = 12;
    double taxp;
    double epfa;
    double taxa;

    double neta;
    double bpAmt, hraA, saa, caa, sdaa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        ctc1 = findViewById(R.id.ctc);
        basicPayPer1 = findViewById(R.id.basicPayPer);
        basicPayAmt1 = findViewById(R.id.basicPayAmt);

        hraPer1 = findViewById(R.id.hraPer);
        hraAmt1 = findViewById(R.id.hraAmt);

        saPer1 = findViewById(R.id.saPer);
        saAmt1 = findViewById(R.id.saAmt);

        caPer1 = findViewById(R.id.caPer);
        caAmt1 = findViewById(R.id.caAmt);

        sdaPer1 = findViewById(R.id.sdaPer);
        sdaAmt1 = findViewById(R.id.sdaAmt);

        epfPer1 = findViewById(R.id.epfPer);
        epfAmt1 = findViewById(R.id.epfAmt);

        taxPer1 = findViewById(R.id.taxPer);
        taxAmt1 = findViewById(R.id.taxAmt);
        total = findViewById(R.id.permonth);
        ded = findViewById(R.id.permonthDeduction);
        net = findViewById(R.id.NetFinalDisplay);

        loadDefaultPercentage();

        ctc1.setOnKeyListener(new EditText.OnKeyListener() {
            public boolean onKey(View v, int KeyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (KeyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            if (ctc1.getText().toString().isEmpty()) {
                                ctc1.setError("CTC is Required");

                            } else {
                                ctcSalary = Double.parseDouble(ctc1.getText().toString());
                                setTaxPercentage(ctcSalary);
                                calculateAmount(ctcSalary);
                                setAllValues();
                            }

                            return true;
                        default:
                            break;

                    }
                }
                return false;
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        saveDataInSharedPreference();

    }

    private void saveDataInSharedPreference() {
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bpPer", String.valueOf(bpPer));
        editor.putString("hrap", String.valueOf(hraP));
        editor.putString("sap", String.valueOf(saP));
        editor.putString("cap", String.valueOf(cap));
        editor.putString("sdap", String.valueOf(sdaP));
        editor.putString("epfp", String.valueOf(epfp));
        editor.apply();
    }

    private void loadDefaultPercentage() {
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        bpPer = Double.parseDouble(sharedPreferences.getString("bpPer", "30"));
        hraP = Double.parseDouble(sharedPreferences.getString("hrap", "10"));
        saP = Double.parseDouble(sharedPreferences.getString("sap", "20"));
        cap = Double.parseDouble(sharedPreferences.getString("cap", "5"));
        sdaP = Double.parseDouble(sharedPreferences.getString("sdap", "5"));
        epfp = Double.parseDouble(sharedPreferences.getString("epfp", "12"));
        basicPayPer1.setText(String.valueOf(bpPer));
        hraPer1.setText(String.valueOf(hraP));
        saPer1.setText(String.valueOf(saP));
        caPer1.setText(String.valueOf(cap));
        sdaPer1.setText(String.valueOf(sdaP));
        epfPer1.setText(String.valueOf(epfp));
    }

    public void setTaxPercentage(Double ctcSalary) {
        if (ctcSalary >= 0 && ctcSalary <= 250000) {
            taxp = 0;

        } else if (ctcSalary > 250000 && ctcSalary <= 500000) {
            taxp = 5;

        } else if (ctcSalary > 500000 && ctcSalary <= 1000000) {
            taxp = 20;

        } else {
            taxp = 30;
        }

    }

    public void calculateAmount(Double ctcSalary) {

        if (taxp == 0) {
            taxa = (ctcSalary * taxp / 100) / 12;

        } else if (taxp == 5) {
            double above2AndHalf = ctcSalary - 250000;
            taxa = (above2AndHalf * taxp / 100) / 12;

        } else if (taxp == 20) {
            double above5 = ctcSalary - 500000;
            taxa = (12500 + (above5 * taxp / 100)) / 12;

        } else if (taxp == 30) {
            double above10 = ctcSalary - 1000000;
            taxa = (112500 + (above10 * taxp / 100)) / 12;
        }
        bpAmt = (ctcSalary / 12) * bpPer / 100;
        hraA = (ctcSalary / 12) * hraP / 100;
        saa = (ctcSalary / 12) * saP / 100;
        caa = (ctcSalary / 12) * cap / 100;
        sdaa = (ctcSalary / 12) * sdaP / 100;
        epfa = (ctcSalary / 12) * epfp / 100;
        neta = (ctcSalary / 12) - (epfa + taxa);


    }

    public void setAllValues() {
        basicPayAmt1.setText(df2.format(bpAmt));
        hraAmt1.setText(df2.format(hraA));
        saAmt1.setText(df2.format(saa));
        caAmt1.setText(df2.format(caa));
        sdaAmt1.setText(df2.format(sdaa));
        epfAmt1.setText(df2.format(epfa));
        taxPer1.setText(String.valueOf(taxp));
        taxAmt1.setText(df2.format(taxa));
        total.setText(df2.format((ctcSalary / 12)));
        ded.setText(df2.format((taxa + epfa)));
        net.setText(df2.format(neta));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_res, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting_on_action_bar) {

            Intent intent = new Intent(this, ThirdActivity.class);
            this.startActivityForResult(intent, 1);
        }
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                    if (ctc1.getText().toString().isEmpty()) {
                        loadDefaultPercentage();
                        basicPayAmt1.setText(R.string.setZeroValue);
                        hraAmt1.setText(R.string.setZeroValue);
                        saAmt1.setText(R.string.setZeroValue);
                        caAmt1.setText(R.string.setZeroValue);
                        sdaAmt1.setText(R.string.setZeroValue);
                       taxPer1.setText(R.string.setZeroValue);
                        epfAmt1.setText(R.string.setZeroValue);
                        taxAmt1.setText(R.string.setZeroValue);
                        total.setText(R.string.setZeroValue);
                        ded.setText(R.string.setZeroValue);
                        net.setText(R.string.setZeroValue);
                    } else {
                        loadDefaultPercentage();
                        setTaxPercentage(ctcSalary);
                        calculateAmount(ctcSalary);
                        setAllValues();
                    }
            }
        }
    }
}