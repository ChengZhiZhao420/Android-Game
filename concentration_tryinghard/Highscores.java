package com.example.concentration_tryinghard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.concentration_tryinghard.R.id.backBtn;

public class Highscores extends AppCompatActivity {
    private Button backBtn;
    private TextView score1;
    private TextView score2;
    private TextView score3;
    private TextView score4;
    private TextView score5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);
        score3 = findViewById(R.id.score3);
        score4 = findViewById(R.id.score4);
        score5 = findViewById(R.id.score5);

        TextView[] scoreArray = {score1, score2, score3, score4, score5};

        FileSystem fileSystem = new FileSystem();
        Bundle extras = getIntent().getExtras();
        int numberOfTiles = extras.getInt("NumberOfTiles");
        ArrayList<Score> scores = null;
        try {
            scores = fileSystem.readFile(numberOfTiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(scores.size() != 0){
            for(int i = 0; i < scores.size(); i++){

                scoreArray[i].setText("" + scores.get(i).getUsername() + "_ " + scores.get(i).getScore());
            }
        }



        backBtn = (Button)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (Highscores.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}
