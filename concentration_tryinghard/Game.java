package com.example.concentration_tryinghard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Game extends AppCompatActivity {
    private Button button1, button2, button3, button4, button5, button6, button7,
            button8, button9, button10, button11, button12, button13, button14,
            button15, button16, button17, button18, button19, button20;
    private TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7,
            textView8, textView9, textView10, textView11, textView12, textView13, textView14,
            textView15, textView16, textView17, textView18, textView19, textView20;
    private TextView scoreDisplay;
    private DisplayComponent dc1, dc2, dc3, dc4, dc5, dc6, dc7, dc8, dc9, dc10, dc11, dc12, dc13, dc14, dc15, dc16, dc17, dc18, dc19, dc20;
    private DisplayComponent[] currentDisplay = null;
    private TilesStack checkStack = new TilesStack();
    private TilesStack wrongStack = new TilesStack();
    private TilesStack correctStack = new TilesStack();
    private int counter = 0;
    private int score = 0;
    private String[] answerKey = {"peen", "gawk", "rize", "yuzu", "aqua", "quiz", "add", "ace", "acid", "area"};//sample answer key
    private MediaPlayer song;
    private Switch musicSwitch;
    public boolean playing = false;
    private int numberOfTiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        song = MediaPlayer.create(Game.this,R.raw.music);
        music();

        initialButton();
        initialTextView();
        initialDisplayComponent();

        //Combination of the buttons
        DisplayComponent[] combinationForTwoWords = {dc1, dc2, dc5, dc6};
        DisplayComponent[] combinationForThreeWords = {dc1, dc2, dc3, dc5, dc6, dc7};
        DisplayComponent[] combinationForFourWords = {dc1, dc2, dc3, dc4, dc5, dc6, dc7, dc8};
        DisplayComponent[] combinationForFiveWords = {dc1, dc2, dc3, dc4, dc5, dc6, dc7, dc8, dc11, dc10};
        DisplayComponent[] combinationForSixWords = {dc1, dc2, dc3, dc4, dc5, dc6, dc7, dc8, dc9, dc10, dc11, dc12};
        DisplayComponent[] combinationForSevenWords = {dc1, dc2, dc3, dc4, dc5, dc6, dc7, dc8, dc9, dc10, dc11, dc12, dc14, dc15};
        DisplayComponent[] combinationForEightWords = {dc1, dc2, dc3, dc4, dc5, dc6, dc7, dc8, dc9, dc10, dc11, dc12, dc13, dc14, dc15, dc16};
        DisplayComponent[] combinationForNineWords = {dc1, dc2, dc3, dc4, dc5, dc6, dc7, dc8, dc9, dc10, dc11, dc12, dc13, dc14, dc15, dc16, dc18, dc19};
        DisplayComponent[] combinationForTenWords = {dc1, dc2, dc3, dc4, dc5, dc6, dc7, dc8, dc9, dc10, dc11, dc12, dc13, dc14, dc15, dc16, dc17, dc18, dc19, dc20};


        Bundle extras = getIntent().getExtras();
        numberOfTiles = extras.getInt("NumberOfTiles");//Receive number of tiles from the dialog

        //Decide how many tiles gonna display at the screen
        switch(numberOfTiles){
            case 2:
                currentDisplay = combinationForTwoWords;
                break;
            case 3:
                currentDisplay = combinationForThreeWords;
                break;
            case 4:
                currentDisplay = combinationForFourWords;
                break;
            case 5:
                currentDisplay = combinationForFiveWords;
                break;
            case 6:
                currentDisplay = combinationForSixWords;
                break;
            case 7:
                currentDisplay = combinationForSevenWords;
                break;
            case 8:
                currentDisplay = combinationForEightWords;
                break;
            case 9:
                currentDisplay = combinationForNineWords;
                break;
            case 10:
                currentDisplay = combinationForTenWords;
                break;
            default:

        }

        display(currentDisplay);
        if(savedInstanceState == null){
            randomKey(currentDisplay);
        }else{
            counter = savedInstanceState.getInt("STATE_COUNTER");
            score = savedInstanceState.getInt("STATE_SCORE");
            scoreDisplay.setText("Score: " + score);
        }

        /**
         * When the user clicks the Try Again Button
         * Resets the last two cards they picked if they are incorrect choices
         */
        Button retryButton = (Button)findViewById(R.id.tryAgain_button);
        retryButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DisplayComponent temp = null;
                while(!wrongStack.isEmpty()){
                    temp = (DisplayComponent) wrongStack.pop();
                    temp.setWrong(false);
                    temp.setButtonVisibility(true);
                    temp.setTextVisibility(false);
                    counter = 0;
                }
            }
        });

        /**
         * When the user clicks the New Game Button
         * Allows user to re-pick how many cards they want on the board
         */
        Button newGameBtn = (Button)findViewById(R.id.newGame_button);
        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                Dialog newGame = new Dialog();
                newGame.show(getSupportFragmentManager(), "open dialog");
            }
        });

        /**
         * When the user clicks the End Game button
         * Resets the board and returns to MainActivity
         */
        Button endGameBtn = (Button)findViewById(R.id.endGame_button);
        endGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                Intent i = new Intent ( Game.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * save the sate of the activity and DisplayComponent
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("STATE_SCORE", score); //save the score
        savedInstanceState.putInt("STATE_COUNTER", counter);//save the counter

        JSONArray data = new JSONArray();//first create a JsonArray that will store the JSONObject
        PrintWriter pw;//writer for input to the file
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);//find the private directory to store the json file
        File file = new File(path, "/" + "saveData.json");

        for(int i = 0; i < currentDisplay.length; i++){//for each DisplayComponent that is currentDisplay, convert to JSON format
            try {
                data.put(currentDisplay[i].toJSON());//convert each DisplayComponent that is currently in use to JSONObject

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Writer writer = null;
        try {
            OutputStream out = Game.this.getApplicationContext().openFileOutput("saveData.json", Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(data.toString());
        }  catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(data.toString());
    }

    /**
     * Push back the properties of DisplayComponent, and restore from previous state
     * @param savedInstanceState saved state from previous Game object
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        try {
            ArrayList<DisplayComponent> array = loadData();

            for(int i = 0; i < currentDisplay.length; i++){//push all properties to DisplayComponent
                DisplayComponent current = currentDisplay[i];
                current.setTextVisibility(array.get(i).getTextVisibility());
                current.setButtonVisibility(array.get(i).getButtonVisibility());
                current.setAnswer(array.get(i).getAnswer());
                current.setChecking(array.get(i).isChecking());
                current.setWrong(array.get(i).isWrong());
                current.setCorrect(array.get(i).isCorrect());

                if(current.isChecking()){//check if this component is in the checkStack
                    checkStack.push(current);
                }
                else if(current.isWrong()){//check if this component is in the wrongStack
                    wrongStack.push(current);
                }
                else if(current.isCorrect()){//check if this component is in the correctStack
                    correctStack.push(current);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     * load Data from saveData.json
     * @return ArrayList of DisplayComponent object
     * @throws IOException
     * @throws JSONException
     */
    private ArrayList<DisplayComponent> loadData() throws IOException, JSONException{
        ArrayList<DisplayComponent> dc = new ArrayList<DisplayComponent>();
        BufferedReader reader = null;
        try{
            InputStream in = Game.this.openFileInput("saveData.json");
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                jsonString.append(line);
            }

            JSONArray array = (JSONArray) new JSONTokener((jsonString.toString())).nextValue();

            for(int i = 0; i < array.length(); i++){
                dc.add(new DisplayComponent(array.getJSONObject(i)));
            }

        }catch(IOException e){

        }finally {
            if(reader != null){
                reader.close();
            }
        }
        return dc;
    }

    /**
     * Randomize the word beneath the button
     * @param dc An array of components
     */
    private void randomKey(DisplayComponent[] dc){
        List<DisplayComponent> list = new ArrayList<DisplayComponent>();
        List<String> list1 = new ArrayList<String>();
        int i = 0;
        for(int k = 0; k < dc.length; k++){
            list.add(dc[k]);
            if(k < 10){
                list1.add(answerKey[k]);
            }
        }

        Collections.shuffle(list);
        Collections.shuffle(list1);

        for(int j = 0; j < dc.length; j = j + 2){
            list.get(j).setAnswer(list1.get(i));
            list.get(j + 1).setAnswer(list1.get(i));
            i++;
        }
    }

    /**
     * Display components at the screen, and setOnClickListener for each button that displayed at the screen
     * @param dc An array of components
     */
    private void display(DisplayComponent[] dc){

        for(int i = 0; i < dc.length; i++){
            final int j = i;
            Button temp = dc[i].getButton();

            dc[i].setButtonVisibility(true);
            temp.setVisibility(View.VISIBLE);
            temp.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    counter++;
                    try {
                        checkAnswer(dc[j]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * When user click on a button, it will push into a stack. If counter go to two, then check whether the answer that the user clicked and the previous answer are match
     * If not match, then pop the previous one from the stack, and push in wrong stack with the one that user clicked
     * @param dc A pair of component that contain with button and textView
     */
    private void checkAnswer(DisplayComponent dc) throws IOException {
        DisplayComponent current = dc;
        DisplayComponent previous = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);

        if(counter <= 2){
            current.setButtonVisibility(false);
            current.setTextVisibility(true);
            current.setChecking(true);

        }
        else{
            builder.setTitle("Alert Massage").setMessage("TRY AGAIN");
            builder.show();
        }

        if(counter == 2){
            previous = (DisplayComponent) checkStack.peek();
            String text1 = previous.getTextView().getText().toString();
            String text2 = current.getTextView().getText().toString();

            if(text2.compareTo(text1) == 0){
                current.setCorrect(true);
                previous.setCorrect(true);

                current.setChecking(false);
                previous.setChecking(false);

                correctStack.push(current);
                correctStack.push(previous);

                checkStack.pop();

                score = score + 2;
                scoreDisplay.setText("Score: " + score);
                counter = 0;
            }
            else{
                previous.setChecking(false);
                current.setChecking(false);

                previous.setWrong(true);
                current.setWrong(true);

                wrongStack.push(previous);
                wrongStack.push(current);

                checkStack.pop();

                if(score > 0){
                    score--;
                    scoreDisplay.setText("Score: " + score);
                }
            }
        }
        else{
            checkStack.push(current);
        }

        if(correctStack.getSize() == currentDisplay.length){
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle("High Scores");

            FileSystem fileSystem = new FileSystem();
            ArrayList<Score> scores= fileSystem.readFile(numberOfTiles);
            boolean ns = false;

            if(scores.size() <= 5){
                ns = true;
            }
            else if(scores.size() > 5){
                for(int i = 0; i < scores.size(); i++){
                    if(score > scores.get(i).getScore()){
                        ns = true;
                        break;
                    }
                }
            }

            if(ns){
                // Set up the input
                final EditText input = new EditText(this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder2.setView(input).setMessage("Please input your username: ");

                // Set up the buttons
                builder2.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        Score currentScore = new Score(score, numberOfTiles, m_Text);
                        try {
                            fileSystem.writeFile(currentScore);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent i = new Intent ( Game.this, MainActivity.class);
                        startActivity(i);
                    }
                });
                builder2.show();
            }
            else{
                builder2.setMessage("Try again next time");
                builder2.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent ( Game.this, MainActivity.class);
                        startActivity(i);
                    }
                });
                builder2.show();
            }
        }
    }

    public void music() {
        Switch musicSwitch = (Switch)  findViewById(R.id.musicSwitch);
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                if (musicSwitch.isChecked()){
                    song.start();
                    playing = true;
                } else {
                    song.pause();
                    playing = false;
                }
            }
        });
    }

    public void onPause() {
        super.onPause();
        song.pause();
    }

    /**
     * Initial Button and assign ID for each one
     */
    private void initialButton(){
        button1 = (Button)findViewById(R.id.B_1);
        button2 = (Button)findViewById(R.id.B_2);
        button3 = (Button)findViewById(R.id.B_3);
        button4 = (Button)findViewById(R.id.B_4);
        button5 = (Button)findViewById(R.id.B_5);
        button6 = (Button)findViewById(R.id.B_6);
        button7 = (Button)findViewById(R.id.B_7);
        button8 = (Button)findViewById(R.id.B_8);
        button9 = (Button)findViewById(R.id.B_9);
        button10 = (Button)findViewById(R.id.B_10);
        button11 = (Button)findViewById(R.id.B_11);
        button12 = (Button)findViewById(R.id.B_12);
        button13 = (Button)findViewById(R.id.B_13);
        button14 = (Button)findViewById(R.id.B_14);
        button15 = (Button)findViewById(R.id.B_15);
        button16 = (Button)findViewById(R.id.B_16);
        button17 = (Button)findViewById(R.id.B_17);
        button18 = (Button)findViewById(R.id.B_18);
        button19 = (Button)findViewById(R.id.B_19);
        button20 = (Button)findViewById(R.id.B_20);
    }

    /**
     * Initial TextView and assign ID for each one
     */
    private void initialTextView(){
        textView1 = (TextView)findViewById(R.id.A_1);
        textView2 = (TextView)findViewById(R.id.A_2);
        textView3 = (TextView)findViewById(R.id.A_3);
        textView4 = (TextView)findViewById(R.id.A_4);
        textView5 = (TextView)findViewById(R.id.A_5);
        textView6 = (TextView)findViewById(R.id.A_6);
        textView7 = (TextView)findViewById(R.id.A_7);
        textView8 = (TextView)findViewById(R.id.A_8);
        textView9 = (TextView)findViewById(R.id.A_9);
        textView10 = (TextView)findViewById(R.id.A_10);
        textView11 = (TextView)findViewById(R.id.A_11);
        textView12 = (TextView)findViewById(R.id.A_12);
        textView13 = (TextView)findViewById(R.id.A_13);
        textView14 = (TextView)findViewById(R.id.A_14);
        textView15 = (TextView)findViewById(R.id.A_15);
        textView16 = (TextView)findViewById(R.id.A_16);
        textView17 = (TextView)findViewById(R.id.A_17);
        textView18 = (TextView)findViewById(R.id.A_18);
        textView19 = (TextView)findViewById(R.id.A_19);
        textView20 = (TextView)findViewById(R.id.A_20);

        scoreDisplay = (TextView)findViewById(R.id.score_display);
    }

    /**
     * Assign button and textView as a pair, use as a united component
     */
    private void initialDisplayComponent(){
        dc1 = new DisplayComponent(button1, textView1);
        dc2 = new DisplayComponent(button2, textView2);
        dc3 = new DisplayComponent(button3, textView3);
        dc4 = new DisplayComponent(button4, textView4);
        dc5 = new DisplayComponent(button5, textView5);
        dc6 = new DisplayComponent(button6, textView6);
        dc7 = new DisplayComponent(button7, textView7);
        dc8 = new DisplayComponent(button8, textView8);
        dc9 = new DisplayComponent(button9, textView9);
        dc10 = new DisplayComponent(button10, textView10);
        dc11 = new DisplayComponent(button11, textView11);
        dc12 = new DisplayComponent(button12, textView12);
        dc13 = new DisplayComponent(button13, textView13);
        dc14 = new DisplayComponent(button14, textView14);
        dc15 = new DisplayComponent(button15, textView15);
        dc16 = new DisplayComponent(button16, textView16);
        dc17 = new DisplayComponent(button17, textView17);
        dc18 = new DisplayComponent(button18, textView18);
        dc19 = new DisplayComponent(button19, textView19);
        dc20 = new DisplayComponent(button20, textView20);
    }
}