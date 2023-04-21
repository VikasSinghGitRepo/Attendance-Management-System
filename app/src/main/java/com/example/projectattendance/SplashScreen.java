package com.example.projectattendance;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    TextView appname;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        appname=findViewById(R.id.tv_name);
        lottie=findViewById(R.id.gifSplash);

        appname.animate().translationY(-800).setDuration(2700).setStartDelay(0);
        //lottie.animate().translationX(2000).setDuration(2000).setStartDelay(2900);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,SignInActivity.class));
                finish();
            }
        },3000);


    }
}