package com.example.concentration_tryinghard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {
    MediaPlayer splashSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        splashSong = MediaPlayer.create(Splash.this,R.raw.intro);
        splashSong.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, (long) (5*1000));
    }

    public void onPause() {
        super.onPause();
        splashSong.stop();
        splashSong.release();
    }
}