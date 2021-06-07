package com.appdid.otpverification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.imgs);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    Animation animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                    imageView.startAnimation(animZoomOut);
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        myThread.start();
    }
}