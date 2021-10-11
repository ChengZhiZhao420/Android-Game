package com.example.concentration_tryinghard;

import org.json.JSONException;
import org.json.JSONObject;

public class Score {
        private static final String JSON_SCORE = "Score";
        private static final String JSON_NUMBER_OF_TILES = "Number Of Tiles";
        private static final String JSON_USERNAME = "username";

    private int score;
        private String username;
        private int numberOfTiles;

        public Score(int score, int numberOfTiles, String username){
            this.score = score;
            this.numberOfTiles = numberOfTiles;
            this.username = username;
        }

        public Score(JSONObject json) throws JSONException {
            this.score = json.getInt(JSON_SCORE);
            this.numberOfTiles = json.getInt(JSON_NUMBER_OF_TILES);
            this.username = json.getString(JSON_USERNAME);
        }

        public void setScore(int score){
            this.score = score;
        }

        public int getScore(){
            return score;
        }

        public void setNumberOfTiles(int number){
            numberOfTiles = number;
        }

        public int getNumberOfTiles(){
            return numberOfTiles;
        }

        public void setUsername(String username){
        username = username;
    }

        public String getUsername(){
        return username;
    }

        public JSONObject toJSON() throws JSONException {
            JSONObject json = new JSONObject();
            json.put(JSON_SCORE, score);
            json.put(JSON_NUMBER_OF_TILES, numberOfTiles);
            json.put(JSON_USERNAME, username);

            return json;
        }

}
