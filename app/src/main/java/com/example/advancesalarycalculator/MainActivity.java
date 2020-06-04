package com.example.advancesalarycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Animation top, bottom;
    ImageView logo;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = (ImageView) findViewById(R.id.imageOfLogo);
        textView=(TextView)findViewById(R.id.textOfFirst);
        top= AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom=AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        logo.setAnimation(top);
        textView.setAnimation(bottom);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2500);

    }
}