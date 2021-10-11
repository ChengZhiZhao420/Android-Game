package com.example.concentration_tryinghard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private Button playBtn;
    private Button hsbtn;
    private Button settingsbtn;
    MediaPlayer song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openGameActivity();
        openHighscoresActivity();
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        song = MediaPlayer.create(MainActivity.this,R.raw.music);
    }

    public void openGameActivity() {
        playBtn = (Button) findViewById(R.id.pbtn);
        playBtn.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openHighscoresActivity() {
        hsbtn = (Button) findViewById(R.id.hsBtn);
        hsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHighscoresDialog();

            }
        });
    }

    private void openHighscoresDialog(){
        new HighscoreDialog().show(getSupportFragmentManager(), "HighScore");
    }

    private void openDialog() {
        Dialog bf = new Dialog();
        bf.show(getSupportFragmentManager(), "example dialog");
    }
}