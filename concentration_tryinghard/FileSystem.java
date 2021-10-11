package com.example.concentration_tryinghard;

import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FileSystem {
    private File path;
    private File file;

    public FileSystem(){
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);//find the private directory to store the json file
        file = new File(path, "/" + "saveScore.json");
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Write into the json file
     * @param scoreObject A score object that contain with number of tiles and score
     * @throws JSONException
     */
    public void writeFile(Score scoreObject) throws JSONException {
        JSONObject js = scoreObject.toJSON();
        try {
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(js.toString() + "\n");
            bw.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Find all the Score object in the json file that has same number of tiles
     * @param numberOfTiles given the number of tiles
     * @return Sorted ArrayList of score object that has same number of tiles
     * @throws IOException
     */
    public ArrayList<Score> readFile(int numberOfTiles) throws IOException {
        ArrayList<Score> arrayScore = new ArrayList<Score>();
        BufferedReader reader = null;
        JSONObject obj;
        try{
            InputStream in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null){
                obj = (JSONObject) new JSONTokener(line).nextValue();


                if(obj.getInt("Number Of Tiles") == numberOfTiles){
                    arrayScore.add(new Score(obj));

                }
            }


        }catch(IOException | JSONException e){

        }finally {
            if(reader != null){
                reader.close();
            }
        }
        return sort(arrayScore);

    }

    public void removeData() throws IOException {
        file.createNewFile();
    }

    /**
     * Sort the given ArrayList, return the first 5 element of the given ArrayList
     * @param list ArrayList
     * @throws IOException
     */
    private ArrayList<Score> sort(ArrayList<Score> list) throws IOException {
        ArrayList<Score> currentScore = list;
        ArrayList<Score> newList = new ArrayList<>();
        Collections.sort(currentScore, new ScoreComparator());

        if(currentScore.size() != 0) {
            for (int i = 0; i < currentScore.size(); i++) {
                if (i == 5) {//maybe we can display more.
                    break;
                }
                newList.add(currentScore.get(i));
            }
        }

        return newList;
    }

    private class ScoreComparator implements Comparator<Score> {
        @Override
        public int compare(Score o1, Score o2) {
            int score1 = o1.getScore();
            int score2 = o2.getScore();

            if(score1 == score2){
                return 0;
            }
            else if(score1 < score2){
                return 1;
            }

            return -1;
        }
    }
}
